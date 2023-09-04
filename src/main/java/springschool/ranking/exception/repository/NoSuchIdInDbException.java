package springschool.ranking.exception.repository;

public class NoSuchIdInDbException extends DBException {
    public NoSuchIdInDbException() {
        super();
    }

    public NoSuchIdInDbException(String message) {
        super(message);
    }

    public NoSuchIdInDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchIdInDbException(Throwable cause) {
        super(cause);
    }
}
