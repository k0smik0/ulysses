/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * PlaceHere.java is part of 'Ulysses'
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
package net.iubris.ulysses.model;


import net.iubris.socrates.model.data.place.details.PlaceDetails;
import net.iubris.socrates.model.data.place.search.Place;
import android.location.Location;

public class PlaceHere {
	
	private final Place place;	
	private final float distance;
	private PlaceDetails placeDetails;	
	
	public PlaceHere(Place place, Location locationHere) {
		this.place = place;		
		this.distance = distance(place,locationHere);
	}
	
	public PlaceHere(Place place, PlaceDetails placeDetails, Location locationHere) {
		this.place = place;
		this.placeDetails = placeDetails;
		this.distance = distance(place,locationHere);
	}	
	
	public PlaceDetails getPlaceDetails() {
		return placeDetails;
	}
	public void setPlaceDetails(PlaceDetails placeDetails) {
		this.placeDetails = placeDetails;
	}

	public Place getPlace() {
		return place;
	}
	public float getDistance() {
		return distance;
	}

	private static float distance(Place place, Location locationHere) {
		final Location placeLocation = new Location("dummy");
		placeLocation.setLatitude( 
				place.getGeometry().getLocation().getLatitude() );
		placeLocation.setLongitude( 
				place.getGeometry().getLocation().getLongitude() );
		return locationHere.distanceTo(placeLocation);
	}
}
