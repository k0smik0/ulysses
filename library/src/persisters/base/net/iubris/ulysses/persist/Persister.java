package net.iubris.ulysses.persist;

import java.util.Collection;
import java.util.List;

import net.iubris.ulysses.model.GeoAddress;
import net.iubris.ulysses.model.Place;
import android.location.Location;

public interface Persister {

	List<Place> searchPlaces(Location location);
	List<Place> getPlaces();
	void savePlaces(Collection<Place> places);
	
//	List<Location> getLocations();
//	boolean isLocationStored(Location location);
//	void setLocation(Location location);
	
	Collection<GeoAddress> getGeoAddresses();
	void saveGeoAddress(GeoAddress geoAddress);
	GeoAddress find(GeoAddress geoAddress);
}
