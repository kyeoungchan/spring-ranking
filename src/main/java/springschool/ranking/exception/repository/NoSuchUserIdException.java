package springschool.ranking.exception.repository;

public class NoSuchUserIdException extends DBException {
    public NoSuchUserIdException() {
        super();
    }

    public NoSuchUserIdException(String message) {
        super(message);
    }

    public NoSuchUserIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchUserIdException(Throwable cause) {
        super(cause);
    }
}
