/*******************************************************************************
 * 
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * ResultFilter.java is part of 'Ulysses'.
 ******************************************************************************/
package net.iubris.ulysses.engine.searcher.filter;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import net.iubris.socrates.model.http.response.data.search.PlaceType;
import net.iubris.ulysses.engine.model.PlaceEnhanced;
import android.util.Log;

public class ResultFilter {

	public static List<PlaceEnhanced> filterPlaces(List<PlaceEnhanced> places) {
		ListIterator<PlaceEnhanced> listIterator = places.listIterator();
		while (listIterator.hasNext()) {
			PlaceEnhanced placeHere = listIterator.next();
			Set<PlaceType> types = placeHere.getPlace().getTypes();
			if (types.contains(PlaceType.bar)) {
				if (types.contains(PlaceType.liquor_store) || types.contains(PlaceType.cafe) || types.contains(PlaceType.bakery))
					continue;
				if (!types.toArray()[0].equals(PlaceType.bar)) {
Log.d("ResultFilter:30","removing: "+placeHere.getPlace().getName()+" - "+types);
					listIterator.remove();
				}
			}
		}
		return places;
	}
}
