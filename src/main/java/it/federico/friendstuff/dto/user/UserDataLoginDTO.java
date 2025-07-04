package it.federico.friendstuff.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserDataLoginDTO(@NotBlank(message = "Username cannot be empty") String username,
		@NotBlank(message = "Password cannot be empty") String password) {

}
