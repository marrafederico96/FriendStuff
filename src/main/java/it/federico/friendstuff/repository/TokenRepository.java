package it.federico.friendstuff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.federico.friendstuff.model.token.RefreshToken;
import it.federico.friendstuff.model.user.User;

@Repository
public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

	public RefreshToken findByRefreshTokenValue(String refreshTokenValue);

	public RefreshToken findByUserAndRevokedFalse(User user);
}
