package net.iubris.ulysses.model.comparators;

import java.util.Comparator;

import net.iubris.ulysses.model.Place;

public class PlaceComparatorByAscendingAlphabetic implements Comparator<Place> {	
	@Override
	public int compare(Place sfe1, Place sfe2) {
		final String name1 = sfe1.getPlaceName();
		final String name2 = sfe2.getPlaceName();
		return String.CASE_INSENSITIVE_ORDER.compare(name1, name2);
	}	
}
