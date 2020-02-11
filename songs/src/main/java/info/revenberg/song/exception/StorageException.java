package info.revenberg.song.exception;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 770300190260982957L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}