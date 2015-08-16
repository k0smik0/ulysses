package net.iubris.ulysses.persist.sql.storm;

import javax.persistence.AttributeConverter;

public abstract class BaseAttributeConverter<X> implements AttributeConverter<X, String> {

	protected static final String SEPARATOR = "#";
	protected abstract X destringify(String dataAsString);
	protected abstract String stringify(X data);
	
	@Override
	public String convertToDatabaseColumn(X arg0) {
		return stringify(arg0);
	}
	@Override
	public X convertToEntityAttribute(String dataAsString) {
		return destringify(dataAsString);
	}
}
