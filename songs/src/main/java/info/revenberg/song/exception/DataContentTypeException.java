package info.revenberg.song.exception;

/**
 * for HTTP 400 errors
 */
public final class DataContentTypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataContentTypeException() {
        super();
    }

    public DataContentTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataContentTypeException(String message) {
        super(message);
    }

    public DataContentTypeException(Throwable cause) {
        super(cause);
    }
}