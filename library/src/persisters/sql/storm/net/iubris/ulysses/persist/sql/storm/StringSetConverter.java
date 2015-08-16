package net.iubris.ulysses.persist.sql.storm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Converter;

@Converter(autoApply=true)
public class StringSetConverter extends BaseAttributeConverter<Set<String>> {

	@Override
	protected Set<String> destringify(String dataAsString) {
		String[] split = dataAsString.split(SEPARATOR);
		return new HashSet<String>( Arrays.asList(split) );
	}

	@Override
	protected String stringify(Set<String> data) {
		StringBuilder sb = new StringBuilder();
		for (String string : data) {
			sb.append(string).append(SEPARATOR);
		}
		sb.deleteCharAt(sb.length()-1); //delete last separator
		return sb.toString();
	}

}
