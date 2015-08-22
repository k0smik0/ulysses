/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * PlaceComparatorByDiscendingRating.java is part of 'Ulysses'.
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
package net.iubris.ulysses.model.comparators;

import java.util.Comparator;

import net.iubris.ulysses.model.Place;

public class PlaceComparatorByDiscendingRating implements Comparator<Place> {	
	
	private boolean hasRating = false;
	
	@Override
	public int compare(Place placeHere1, Place placeHere2) {
		final float rating1 = placeHere1.getRating();
		final float rating2 = placeHere2.getRating();
		
		if (rating1>0 || rating2>0) {
//			Ln.d("ratingable!");
			hasRating = true;
		}
	
		if (rating1 < rating2 ) return 1;
		if (rating1 > rating2 ) return -1;
		return 0;
	}
	
	public void resetRatingExistenceChecker() {
		hasRating = false;
	}

	public boolean isExistingRating() {
		return hasRating;
	}
}