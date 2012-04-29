package net.iubris.ulysses.model;


import net.iubris.socrates.model.data.places.details.PlaceDetails;
import net.iubris.socrates.model.data.places.search.Place;
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
