package it.federico.friendstuff.exception;

public class UserDataExistsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

	public UserDataExistsException(String message) {
        super(message);
    }
    
}
