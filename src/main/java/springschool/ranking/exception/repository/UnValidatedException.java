package springschool.ranking.exception.repository;

public class UnValidatedException extends RuntimeException {
    public UnValidatedException(String message) {
        super(message);
    }

    public UnValidatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnValidatedException(Throwable cause) {
        super(cause);
    }

    public UnValidatedException() {
    }
}
