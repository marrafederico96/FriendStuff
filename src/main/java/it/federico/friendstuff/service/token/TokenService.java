package it.federico.friendstuff.service.token;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.federico.friendstuff.config.CustomUserDetails;
import it.federico.friendstuff.dto.token.TokenDTO;
import it.federico.friendstuff.dto.token.TokenResponseDTO;
import it.federico.friendstuff.exception.TokenExpireException;
import it.federico.friendstuff.model.token.RefreshToken;
import it.federico.friendstuff.model.user.User;
import it.federico.friendstuff.repository.TokenRepository;
import it.federico.friendstuff.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class TokenService {
	private final JwtEncoder jwtEncoder;
	private final TokenRepository tokenRepository;
	private final UserRepository userRepository;

	public TokenService(JwtEncoder jwtEncoder, TokenRepository tokenRepository, UserRepository userRepository) {
		this.jwtEncoder = jwtEncoder;
		this.tokenRepository = tokenRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public TokenDTO generateToken(Authentication authentication) {
		Instant now = Instant.now();
		String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		User user = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(authentication.getName() + " user not found"));
		List<RefreshToken> refreshTokenList = user.getRefreshTokens();

		if (!refreshTokenList.isEmpty()) {
			refreshTokenList.forEach(token -> token.setRevoked(true));
		}

		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setRefreshTokenValue(UUID.randomUUID().toString());
		refreshToken.setCreatedDate(now);
		refreshToken.setExpireDate(now.plus(15, ChronoUnit.DAYS));
		refreshToken.setRevoked(false);
		refreshToken.setUser(user);

		tokenRepository.save(refreshToken);

		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now)
				.expiresAt(now.plus(10, ChronoUnit.MINUTES)).subject(authentication.getName()).claim("scope", scope)
				.build();

		String activeToken = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

		return new TokenDTO(activeToken, refreshToken.getRefreshTokenValue());
	}

	@Transactional
	public void revokeToken(String token) {
		RefreshToken refreshToken = tokenRepository.findByRefreshTokenValue(token);
		refreshToken.setRevoked(true);
		tokenRepository.save(refreshToken);
	}

	@Transactional
	public TokenResponseDTO refresh(String refreshToken, HttpServletResponse response) {

		if (refreshToken == null) {
			throw new TokenExpireException("Cookie not exists");
		}

		RefreshToken token = tokenRepository.findByRefreshTokenValue(refreshToken);

		if (token == null || token.isRevoked() || token.getExpireDate().isBefore(Instant.now())) {
			throw new TokenExpireException("Please login.");
		}

		CustomUserDetails customUserDetails = new CustomUserDetails(token.getUser().getUsername(),
				token.getUser().getPassword());
		Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails.getUsername(), null,
				customUserDetails.getAuthorities());

		TokenDTO tokenDTO = generateToken(authentication);
		ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenDTO.refreshToken()).httpOnly(true).secure(true)
				.path("/auth/refresh").sameSite("Strict").maxAge(Duration.ofDays(15)).build();

		response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		return new TokenResponseDTO(tokenDTO.accessToken());
	}

	public RefreshToken findFalseRefreshToken(User user) {
		return tokenRepository.findByUserAndRevokedFalse(user);
	}
}
