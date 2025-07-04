package it.federico.friendstuff.dto.exception;

import java.time.Instant;
import java.util.Map;

public class ErrorExceptionDTO {
    
    private final int status;
    private final String message;
    private final Map<String, String> errors;
    private final Instant timestamp;

    public ErrorExceptionDTO(int status, String message) {
        this.status = status;
        this.message = message;
        this.errors = null;
        this.timestamp = Instant.now();
    }

    public ErrorExceptionDTO(int status,  Map<String, String> errors) {
        this.status = status;
        this.message = null;
        this.timestamp = Instant.now();
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
    

}
