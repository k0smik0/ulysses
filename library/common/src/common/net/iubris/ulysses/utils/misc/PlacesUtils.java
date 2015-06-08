/*******************************************************************************
 * 
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * PlacesUtils.java is part of 'Ulysses'.
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ulysses' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.utils.misc;

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
//		String format = String.format(Locale.US, "%.0f", distance);
		String format = String.format(Locale.getDefault(), "%.0f", distance);
		return format+unit;
//		return Math.floor(distance-0.5d)+unit;
	}
}
