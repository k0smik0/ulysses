package net.iubris.ulysses.model.comparators;

import java.util.Comparator;

import net.iubris.ulysses.model.PlaceHere;

public class PlaceComparatorByAscendingDistance implements Comparator<PlaceHere> {
	@Override
	public int compare(PlaceHere placeHere1, PlaceHere placeHere2) {
		final float distance1 = placeHere1.getDistance();
		final float distance2 = placeHere2.getDistance();
		
		if (distance1 > distance2) return 1;
		if (distance1 < distance2) return -1;
		return 0;
	}	
}
