package org.uhc.exception;

public class LockedUserLoginException extends Exception {

    /** long Short Description */
	private static final long serialVersionUID = 1L;

	public LockedUserLoginException() {
        super();
    }

    public LockedUserLoginException(String message) {
        super(message);
    }

    public LockedUserLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LockedUserLoginException(Throwable cause) {
        super(cause);
    }
}
