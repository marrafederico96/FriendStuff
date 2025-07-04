package it.federico.friendstuff.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import it.federico.friendstuff.dto.exception.ErrorExceptionDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserDataExistsException.class)
	public ResponseEntity<ErrorExceptionDTO> handleUserDataExistsException(UserDataExistsException ex) {

		ErrorExceptionDTO error = new ErrorExceptionDTO(HttpStatus.CONFLICT.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ErrorExceptionDTO> handlePasswordMismatchException(PasswordMismatchException ex) {
		ErrorExceptionDTO error = new ErrorExceptionDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorExceptionDTO> handleMethodargumentNotValidException(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ErrorExceptionDTO errorExceptionDTO = new ErrorExceptionDTO(HttpStatus.FORBIDDEN.value(), errors);

		return new ResponseEntity<>(errorExceptionDTO, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(TokenExpireException.class)
	public ResponseEntity<ErrorExceptionDTO> handleTokenExpireException(TokenExpireException ex) {
		ErrorExceptionDTO error = new ErrorExceptionDTO((HttpStatus.UNAUTHORIZED.value()), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(FriendshipException.class)
	public ResponseEntity<ErrorExceptionDTO> handleFriendshipExistsException(FriendshipException ex) {
		ErrorExceptionDTO error = new ErrorExceptionDTO(HttpStatus.CONFLICT.value(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(GroupException.class)
	public ResponseEntity<ErrorExceptionDTO> handleGroupException(GroupException ex) {
		ErrorExceptionDTO error = new ErrorExceptionDTO(HttpStatus.CONFLICT.value(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(EventException.class)
	public ResponseEntity<ErrorExceptionDTO> handleEventException(EventException ex) {
		ErrorExceptionDTO error = new ErrorExceptionDTO(HttpStatus.CONFLICT.value(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ExpenseException.class)
	public ResponseEntity<ErrorExceptionDTO> handleExpenseException(ExpenseException ex) {
		ErrorExceptionDTO error = new ErrorExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorExceptionDTO> handleRuntimeException(RuntimeException ex) {
		ErrorExceptionDTO error = new ErrorExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
