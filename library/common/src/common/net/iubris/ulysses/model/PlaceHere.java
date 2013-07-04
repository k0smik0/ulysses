/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * PlaceHere.java is part of 'Ulysses'.
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
package net.iubris.ulysses.model;


import net.iubris.socrates.model.http.response.data.details.Details;
import net.iubris.socrates.model.http.response.data.search.Place;
import android.location.Location;

public class PlaceHere {
	
	private final Place place;	
	private final float distance;
	private Details details;
	
	public PlaceHere(Place place, Location locationHere) {
		this.place = place;		
		this.distance = distance(place,locationHere);
	}
	
	public PlaceHere(Place place, Details details, Location locationHere) {
		this.place = place;
		this.details = details;
		this.distance = distance(place,locationHere);
	}	
	
	public Details getDetails() {
		return details;
	}
	public void setDetails(Details details) {
		this.details = details;
	}

	public Place getPlace() {
		return place;
	}
	public float getDistance() {
		return distance;
	}

	private static float distance(Place place, Location locationHere) {
		final Location placeLocation = new Location("dummy");
		net.iubris.socrates.model.http.response.data.geocoding.Location placeLoc = place.getGeometry().getLocation();
		placeLocation.setLatitude( placeLoc.getLatitude() );
		placeLocation.setLongitude( 	placeLoc.getLongitude() );		
		return locationHere.distanceTo(placeLocation);
	}
}
