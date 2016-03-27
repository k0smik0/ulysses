package net.iubris.ulysses.data;

import java.io.IOException;

import com.google.api.client.json.gson.GsonFactory;

public enum JSONHandler {
	
	INSTANCE;
	
	private final GsonFactory gsonFactory;

	private JSONHandler() {
		gsonFactory = new GsonFactory();
	}
	
	public <T> T fromString(String jsonAsString, Class<T> destinationClass) throws NoValidJSONStringException {
		if (jsonAsString==null) {
			throw new NoValidJSONStringException("null string as argument");
		}
		if (jsonAsString.isEmpty()) {
			throw new NoValidJSONStringException("empty string as argument");
		}
		try {
			return gsonFactory.fromString(jsonAsString, destinationClass);
		} catch (IOException e) {
			throw new NoValidJSONStringException(jsonAsString +" not valid");
		}
	}
	
	public <T> String toString(T item) {
		return gsonFactory.toString(item);
	}
	
	public class NoValidJSONStringException extends Exception {
		private static final long serialVersionUID = 1L;
		public NoValidJSONStringException() {
			super();
		}
		public NoValidJSONStringException(String detailMessage, Throwable throwable) {
			super(detailMessage, throwable);
		}
		public NoValidJSONStringException(String detailMessage) {
			super(detailMessage);
		}
		public NoValidJSONStringException(Throwable throwable) {
			super(throwable);
		}
	}
}
