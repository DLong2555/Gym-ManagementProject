package renewal.gym.error;

public class CustomServiceException extends RuntimeException {
    public CustomServiceException() {
        super();
    }

    public CustomServiceException(String message) {
        super(message);
    }

    public CustomServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomServiceException(Throwable cause) {
        super(cause);
    }
}
