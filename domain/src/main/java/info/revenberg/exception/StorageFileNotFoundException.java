package info.revenberg.exception;

public class StorageFileNotFoundException extends StorageException {

	private static final long serialVersionUID = -7968379019281006558L;

	public StorageFileNotFoundException(String message) {
		super(message);
	}

	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
    }
}