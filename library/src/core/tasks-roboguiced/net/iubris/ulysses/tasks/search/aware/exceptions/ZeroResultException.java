package net.iubris.ulysses.tasks.search.aware.exceptions;

public class ZeroResultException extends Exception {

	private static final long serialVersionUID = -2419835193089457807L;

	public ZeroResultException() {
	}

	public ZeroResultException(String detailMessage) {
		super(detailMessage);
	}

	public ZeroResultException(Throwable throwable) {
		super(throwable);
	}

	public ZeroResultException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
