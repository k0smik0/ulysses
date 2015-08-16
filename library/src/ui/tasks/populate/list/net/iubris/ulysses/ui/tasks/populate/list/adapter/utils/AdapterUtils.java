/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * AdapterUtils.java is part of 'Ulysses'.
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
package net.iubris.ulysses.ui.tasks.populate.list.adapter.utils;

import java.util.Collection;
import java.util.Comparator;

import net.iubris.ulysses.model.Place;
import android.location.Location;
import android.widget.ArrayAdapter;

public class AdapterUtils {
	
	/**
	 *clean adapter, then populate from list and notify for the change
	 * 
	 * @param adapterToPopulate 	arrayadapter to populate
	 * @param populatingList 			PlaceHere list used to populate adapter
	 */
	public static void clearAndPopulateAdapter(ArrayAdapter<Place> adapterToPopulate, Collection<Place> populatingList, Location location) {
		clearAndPopulate(adapterToPopulate, populatingList, location);
		adapterToPopulate.notifyDataSetChanged();
	}
	
	/**
	 * clean adapter, then populate from list, sort with comparator and notify for the change
	 * 
	 * @param adapterToPopulate 	arrayadapter to populate
	 * @param populatingList 			PlaceHere list used to populate adapter
	 * @param comparator				a PlaceHere comparator to sort adapter
	 */
	public static void clearAndPopulateAdapter(ArrayAdapter<Place> adapterToPopulate, Collection<Place> populatingList, Location location, Comparator<Place> comparator) {
		clearAndPopulate(adapterToPopulate, populatingList, location);
		adapterToPopulate.sort( comparator );
		adapterToPopulate.notifyDataSetChanged();
	}
	
	private static void clearAndPopulate(ArrayAdapter<Place> adapterToPopulate, Collection<Place> populatingList, Location locationHere) {
		adapterToPopulate.clear();
		for (Place place: populatingList) {
			place.setDistance( Place.distance(place, locationHere) );
			adapterToPopulate.add(place);
		}
	}
}
