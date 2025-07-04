package it.federico.friendstuff.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.federico.friendstuff.dto.token.TokenResponseDTO;
import it.federico.friendstuff.dto.user.FriendshipUserReceiverDTO;
import it.federico.friendstuff.dto.user.UserDataLoginDTO;
import it.federico.friendstuff.dto.user.UserDataRegisterDTO;
import it.federico.friendstuff.dto.user.UserInfoDTO;
import it.federico.friendstuff.model.user.User;
import it.federico.friendstuff.service.token.TokenService;
import it.federico.friendstuff.service.user.FriendshipService;
import it.federico.friendstuff.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class UserController {

	private final UserService userService;
	private final TokenService tokenService;
	private final FriendshipService friendshipService;

	public UserController(UserService userService, TokenService tokenService, FriendshipService friendshipService) {
		this.userService = userService;
		this.tokenService = tokenService;
		this.friendshipService = friendshipService;
	}

	@PostMapping("/auth/register")
	public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody UserDataRegisterDTO userDataRegisterDTO,
			HttpServletResponse response) {
		UserDataLoginDTO userDataLoginDTO = userService.registerUser(userDataRegisterDTO);
		return new ResponseEntity<>(userService.loginUser(userDataLoginDTO, response), HttpStatus.OK);
	}

	@GetMapping("/user/me")
	public ResponseEntity<UserInfoDTO> getUser(Authentication authentication) {
		User user = userService.findUser(authentication.getName());
		UserInfoDTO userInfoDTO = new UserInfoDTO(user.getUsername(), user.getFirstName(), user.getLastName(),
				user.getEmail());
		return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
	}

	@PostMapping("/auth/login")
	public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody UserDataLoginDTO userDataLoginDTO,
			HttpServletResponse response) {
		return new ResponseEntity<>(userService.loginUser(userDataLoginDTO, response), HttpStatus.OK);
	}

	@PostMapping("/auth/refresh")
	public ResponseEntity<TokenResponseDTO> refresh(@CookieValue(required = false) String refreshToken,
			HttpServletResponse response) {
		TokenResponseDTO tokenResponseDTO = tokenService.refresh(refreshToken, response);
		return new ResponseEntity<>(tokenResponseDTO, HttpStatus.OK);
	}

	@PostMapping("/auth/logout")
	public ResponseEntity<Void> logout(Authentication authentication, HttpServletResponse response) {
		userService.logoutUser(authentication, response);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/user/delete")
	public ResponseEntity<Void> delete(Authentication authentication) {
		userService.deleteUser(authentication);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/user/add-friend")
	public ResponseEntity<Void> addFriend(@RequestBody FriendshipUserReceiverDTO username,
			Authentication authentication) {
		friendshipService.addFriend(username, authentication);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/user/accept")
	public ResponseEntity<Void> acceptFriend(@RequestBody FriendshipUserReceiverDTO username,
			Authentication authentication) {
		friendshipService.acceptFriendship(username, authentication);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/user/denied")
	public ResponseEntity<Void> rejectFriend(@RequestBody FriendshipUserReceiverDTO username,
			Authentication authentication) {
		friendshipService.rejectFriendship(username, authentication);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
