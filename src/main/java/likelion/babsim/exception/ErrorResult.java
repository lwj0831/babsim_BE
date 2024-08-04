package likelion.babsim.exception;

public class ErrorResult {
    private String code;
    private String message;

    public ErrorResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
