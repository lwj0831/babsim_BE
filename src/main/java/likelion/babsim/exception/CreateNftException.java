package likelion.babsim.exception;

public class CreateNftException extends RuntimeException{

    public CreateNftException() {
    }

    public CreateNftException(String message) {
        super(message);
    }

    public CreateNftException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateNftException(Throwable cause) {
        super(cause);
    }

    public CreateNftException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
