package it.federico.friendstuff.service.user;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.federico.friendstuff.dto.token.TokenDTO;
import it.federico.friendstuff.dto.token.TokenResponseDTO;
import it.federico.friendstuff.dto.user.UserDataLoginDTO;
import it.federico.friendstuff.dto.user.UserDataRegisterDTO;
import it.federico.friendstuff.exception.GroupException;
import it.federico.friendstuff.exception.PasswordMismatchException;
import it.federico.friendstuff.exception.UserDataExistsException;
import it.federico.friendstuff.model.group.GroupMember;
import it.federico.friendstuff.model.group.GroupRole;
import it.federico.friendstuff.model.token.RefreshToken;
import it.federico.friendstuff.model.user.User;
import it.federico.friendstuff.repository.UserRepository;
import it.federico.friendstuff.service.token.TokenService;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, TokenService tokenService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	@Transactional
	public UserDataLoginDTO registerUser(UserDataRegisterDTO userDataRegisterDTO) {

		if (userRepository.findByUsername(userDataRegisterDTO.username()).isPresent()) {
			throw new UserDataExistsException(userDataRegisterDTO.username() + " already in use.");
		}

		if (userRepository.findByEmail(userDataRegisterDTO.email()).isPresent()) {
			throw new UserDataExistsException(userDataRegisterDTO.email() + " already in use.");
		}

		if (!userDataRegisterDTO.password().equals(userDataRegisterDTO.confirmPassword())) {
			throw new PasswordMismatchException("Password mismatch");
		}

		User user = new User();

		user.setEmail(userDataRegisterDTO.email().toLowerCase().trim());
		user.setFirstName(userDataRegisterDTO.firstName().trim());
		user.setLastName(userDataRegisterDTO.lastName().trim());
		user.setUsername(userDataRegisterDTO.username().toLowerCase().trim());
		user.setPassword(passwordEncoder.encode(userDataRegisterDTO.password()).trim());
		userRepository.save(user);

		return new UserDataLoginDTO(userDataRegisterDTO.username(), userDataRegisterDTO.password());

	}

	public TokenResponseDTO loginUser(UserDataLoginDTO userDataLoginDTO, HttpServletResponse response) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userDataLoginDTO.username(), userDataLoginDTO.password()));
		TokenDTO tokenDTO = tokenService.generateToken(authentication);
		String refreshToken = tokenDTO.refreshToken();

		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken).httpOnly(true).secure(true)
				.path("/auth/refresh").maxAge(Duration.ofDays(15)).sameSite("Strict").build();
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		return new TokenResponseDTO(tokenDTO.accessToken());
	}

	public void logoutUser(Authentication authentication, HttpServletResponse response) {
		User user = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(authentication.getName() + " not found"));
		RefreshToken refreshToken = tokenService.findFalseRefreshToken(user);
		tokenService.revokeToken(refreshToken.getRefreshTokenValue());

		ResponseCookie cookie = ResponseCookie.from("refreshToken", "").httpOnly(true).secure(true)
				.path("/auth/refresh").maxAge(0).sameSite("Strict").build();

		response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	@Transactional
	public void deleteUser(Authentication authentication) {
		User user = userRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new UsernameNotFoundException(authentication.getName() + " not found"));

		List<GroupMember> admins = user.getGroupMembership().stream()
				.filter(group -> group.getRole().equals(GroupRole.ADMIN)).collect(Collectors.toList());

		if (admins.size() > 0) {
			throw new GroupException(
					user.getUsername() + " is an admin of " + admins.size() + " group(s) and cannot be deleted");

		}
		userRepository.delete(user);
	}

	public User findUser(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
	}
}
