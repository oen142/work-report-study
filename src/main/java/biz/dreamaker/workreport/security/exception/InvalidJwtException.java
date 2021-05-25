package biz.dreamaker.workreport.security.exception;


public class InvalidJwtException extends RuntimeException {

    public InvalidJwtException() {
    }

    public InvalidJwtException(String message) {
        super(message);
    }
}
