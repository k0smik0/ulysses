package net.iubris.ulysses.persist;

import java.util.Collection;
import java.util.List;

import net.iubris.ulysses.model.Place;
import android.location.Location;

public interface Persister {

	List<Place> searchPlaces(Location location);
	List<Place> getPlaces();
	void setPlaces(Collection<Place> places);
	
	List<Location> getLocations();
	boolean isLocationStored(Location location);
	void setLocation(Location location);
}
