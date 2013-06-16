package net.iubris.ulysses.map;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class LocationUtils {
	public static LatLng locationToLatLng(Location location) {
		return new LatLng(location.getLatitude(), location.getLongitude());
	}
	
	public static LatLng locationToLatLng(net.iubris.socrates.model.http.response.data.geocoding.Location location) {
		return new LatLng(location.getLatitude(), location.getLongitude());
	}
}
