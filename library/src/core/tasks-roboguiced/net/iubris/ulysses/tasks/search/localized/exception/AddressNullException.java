package net.iubris.ulysses.tasks.search.localized.exception;

public class AddressNullException extends Exception {

	private static final long serialVersionUID = -67340843066545036L;

	public AddressNullException() {
	}

	public AddressNullException(String detailMessage) {
		super(detailMessage);
	}

	public AddressNullException(Throwable throwable) {
		super(throwable);
	}

	public AddressNullException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
