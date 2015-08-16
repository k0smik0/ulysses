package net.iubris.ulysses.ui.tasks.populate.map._base.exceptions;

public class LocationNullException extends Exception {

	private static final long serialVersionUID = 7982265541595026263L;

	public LocationNullException() {
		super();
	}

	public LocationNullException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public LocationNullException(String detailMessage) {
		super(detailMessage);
	}

	public LocationNullException(Throwable throwable) {
		super(throwable);
	}
}
