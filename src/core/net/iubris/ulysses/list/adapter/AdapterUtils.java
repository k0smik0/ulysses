/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * AdapterUtils.java is part of 'Ulysses'
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ulysses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Ulysses' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.list.adapter;

import java.util.Comparator;
import java.util.List;

import net.iubris.ulysses.model.PlaceHere;

public class AdapterUtils {
	/**
	 * clean adapter, then populate from list, sort with comparator and notify for the change
	 * 
	 * @param adapterToPopulate 	arrayadapter to populate
	 * @param populatingList 			PlaceHere list used to populate adapter
	 * @param comparator				a PlaceHere comparator to sort adapter
	 */
	public static void clearAndPopulateAdapter(PlacesHereListAdapter adapterToPopulate,List<PlaceHere> populatingList, Comparator<PlaceHere> comparator) {
		adapterToPopulate.clear();
		for (PlaceHere placeHere: populatingList) {
			adapterToPopulate.add(placeHere);
		}
		adapterToPopulate.sort( comparator );
		adapterToPopulate.notifyDataSetChanged();
	}
}
