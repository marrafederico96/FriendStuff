package it.federico.friendstuff.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDataRegisterDTO(@NotBlank(message = "Username cannot be empty") String username,
		@NotBlank(message = "Email cannot be empty") @Email String email,
		@NotBlank(message = "Password cannot be empty") String password,
		@NotBlank(message = "Confirm Password cannot be empty") String confirmPassword,
		@NotBlank(message = "First Name cannot be empty") String firstName,
		@NotBlank(message = "Last Name cannot be empty") String lastName) {

}
