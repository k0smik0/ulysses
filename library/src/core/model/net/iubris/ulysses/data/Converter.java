package net.iubris.ulysses.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.iubris.ulysses.model.Location;

public enum Converter {

	INSTANCE;
	
	private static final String SEPARATOR_FIRST_LEVEL = "#";
	private static final String EMPTY_STRING = "";
	
	/*public <T> List<T> asList(String dataAsString, Class<T> clazz) {
		if (clazz.equals(String.class)) {
			return (List<T>) asList(dataAsString);
		} else {
			return asListRecursive(dataAsString, clazz);
		}
	}*/
	
	public List<String> asList(String dataAsString) {
		if (dataAsString.isEmpty()) {
			return Collections.emptyList();
		}
		String[] split = dataAsString.split(SEPARATOR_FIRST_LEVEL);
		return Arrays.asList(split);
	}
	public <T extends Stringable> List<T> asListRecursive(String dataAsStringOfData, Class<T> clazz) {
		List<String> asList = asList(dataAsStringOfData);
		List<T> list = new ArrayList<>();
		for (String string : asList) {
			try {
				Constructor<T> constructor = clazz.getConstructor(String.class);
				T t = constructor.newInstance(string);
				list.add( t );
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e ) {
				e.printStackTrace();
			}
		}
		return Collections.emptyList();
	}

	public String asString(Location location) {
		return location.getLatitude()+SEPARATOR_FIRST_LEVEL+location.getLongitude();
	}
	
	public String asString(Collection<?> items) {
		if (items==null || items.isEmpty()) {
			return EMPTY_STRING;
		}
		
		StringBuilder sb = new StringBuilder();
		try {
			@SuppressWarnings("unchecked")
			Collection<Stringable> stringables = (Collection<Stringable>) items;
			for (Stringable sg : stringables) {
				sb.append(sg.asString()).append(SEPARATOR_FIRST_LEVEL);
			}
		} catch (ClassCastException e) {
			for (Object o : items) {
				sb.append(o).append(SEPARATOR_FIRST_LEVEL);
			}
		}		
		sb.deleteCharAt(sb.length()-1); //delete last separator
		return sb.toString();
	}

	public Location asLocation(String locationAsString, Class<Location> locationClass) {
		String[] split = locationAsString.split(SEPARATOR_FIRST_LEVEL);
		return new Location(Double.parseDouble( split[0] ), Double.parseDouble( split[1] ));
	}
	
	public interface Stringable {
		String asString();
	}
}
