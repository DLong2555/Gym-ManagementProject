package renewal.gym.error;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException() {
        super();
    }

    public ExternalApiException(String message) {
        super(message);
    }

    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalApiException(Throwable cause) {
        super(cause);
    }
}
