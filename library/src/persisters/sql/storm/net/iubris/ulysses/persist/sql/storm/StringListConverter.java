package net.iubris.ulysses.persist.sql.storm;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Converter;

@Converter(autoApply=true)
public class StringListConverter extends BaseAttributeConverter<List<String>> {

	@Override
	protected List<String> destringify(String dataAsString) {
		String[] split = dataAsString.split(SEPARATOR);
		return Arrays.asList(split);
	}

	@Override
	protected String stringify(List<String> data) {
		StringBuilder sb = new StringBuilder();
		for (String string : data) {
			sb.append(string).append(SEPARATOR);
		}
		sb.deleteCharAt(sb.length()-1); //delete last separator
		return sb.toString();
	}

}
