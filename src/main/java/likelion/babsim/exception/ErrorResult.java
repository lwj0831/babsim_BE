package likelion.babsim.exception;

public class ErrorResult {
    private String errorType;
    private String errorInfo;

    public ErrorResult(String errorType, String errorInfo) {
        this.errorType = errorType;
        this.errorInfo = errorInfo;
    }
}
