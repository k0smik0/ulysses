package net.iubris.ulysses.model.comparators;

import java.util.Comparator;

import net.iubris.ulysses.model.PlaceHere;

public class PlaceComparatorByDiscendingRating implements Comparator<PlaceHere> {	
	@Override
	public int compare(PlaceHere placeHere1, PlaceHere placeHere2) {
		final float rating1 = placeHere1.getPlace().getRating();
		final float rating2 = placeHere2.getPlace().getRating();
	
		if (rating1 < rating2 ) return 1;
		if (rating1 > rating2 ) return -1;
		return 0;
	}	
}

