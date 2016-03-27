package net.iubris.ulysses.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.iubris.ulysses.model.Location;

public enum Converter {

	INSTANCE;
	
	private static final String SEPARATOR = "#";
	
	
	public List<String> asList(String dataAsString) {
		if (dataAsString.isEmpty()) {
			return null;
		}
		String[] split = dataAsString.split(SEPARATOR);
		return Arrays.asList(split);
	}

	public String asString(Location location) {
		return location.getLatitude()+SEPARATOR+location.getLongitude();
	}
	public String asString(Collection<String> data) {
		if (data==null)
			return "";
		
		StringBuilder sb = new StringBuilder();
		for (String string : data) {
			sb.append(string).append(SEPARATOR);
		}
		if (data.size()>0) {
			sb.deleteCharAt(sb.length()-1); //delete last separator
		}
		return sb.toString();
	}


	public Location asObject(String locationAsString, Class<Location> class1) {
		String[] split = locationAsString.split(SEPARATOR);
		return new Location(Double.parseDouble( split[0] ), Double.parseDouble( split[1] ));
	}
}
