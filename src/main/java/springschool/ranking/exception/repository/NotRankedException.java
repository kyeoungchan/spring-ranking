package springschool.ranking.exception.repository;

public class NotRankedException extends DBException {
    public NotRankedException() {
        super();
    }

    public NotRankedException(String message) {
        super(message);
    }

    public NotRankedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotRankedException(Throwable cause) {
        super(cause);
    }
}
