/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * PlaceComparatorByAscendingDistance.java is part of 'Ulysses'.
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

public class PlaceComparatorByAscendingDistance implements Comparator<Place> {
	@Override
	public int compare(Place placeHere1, Place placeHere2) {
		final double distance1 = placeHere1.getDistance();
		final double distance2 = placeHere2.getDistance();
		
		if (distance1 > distance2) return 1;
		if (distance1 < distance2) return -1;
		return 0;
	}	
}
