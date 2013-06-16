package net.iubris.ulysses.ui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class PlacesUtils {

	public static String getUsefulAddress(String formattedAddress, int infoToRemove) {
		List<String> asList = new LinkedList<String>( Arrays.asList(formattedAddress.split(",")));
		for (int i=0;i<infoToRemove;i++) 
			asList.remove(asList.size()-1); // removing Country, then City, etc
		
		String address ="";
		for (String s: asList) {
			address = address.concat(s).concat(" ");
		}
		return address;
		/*
		String string = asList.toString();
		Log.d("PlacesUtils:16,",string);
		String replaced = string.replace("[", "").replace("]", "");
		Log.d("PlacesUtils:18,",replaced);
		return replaced;*/
	}

	public static String getUsefulDistance(float distance) {
		if (distance > 1000) {
			distance = distance/1000;
			String unit = "km";
			String format = String.format("%.1f", distance);
			return format+unit;
		}
		
		String unit = "m";
		String format = String.format(Locale.US, "%.0f", distance);
		return format+unit;
//		return Math.floor(distance-0.5d)+unit;
	}
}
