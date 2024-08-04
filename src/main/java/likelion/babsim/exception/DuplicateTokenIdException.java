package likelion.babsim.exception;

public class DuplicateTokenIdException extends RuntimeException{
    public DuplicateTokenIdException() {
    }

    public DuplicateTokenIdException(String message) {
        super(message);
    }

    public DuplicateTokenIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTokenIdException(Throwable cause) {
        super(cause);
    }
}
