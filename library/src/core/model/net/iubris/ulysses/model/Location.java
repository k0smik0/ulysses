package net.iubris.ulysses.model;

import java.io.Serializable;
import java.math.BigDecimal;

import net.iubris.ulysses.data.Converter;
import roboguice.util.Ln;

public class Location implements Serializable, Comparable<Location> {
	private static final long serialVersionUID = 8145546699386327997L;
	private static final Converter converter = Converter.INSTANCE;
	
	private double latitude;
	private double longitude;
	public Location(double latitude, double longitude) {
		super();
		
		// 4^ decimal = 11m
		// 5^ decimal = 1,1m
		// 6^ decimal = 0,11m = 11cm
		// 5^ is enough!
		this.latitude = new BigDecimal(latitude).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		this.longitude = new BigDecimal(longitude).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		Ln.d("saved with:"+this.latitude+","+this.longitude);
	}
	public Location() {}
	public Location(String fromString) /*throws NoValidJSONStringException*/ {
//		this(JSONHandler.INSTANCE.fromString(fromJsonString,Location.class));
		this(Location.fromString(fromString));
	}
	private Location(Location location) {
		this(location.getLatitude(), location.getLongitude());
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String asString() {
//		return JSONHandler.INSTANCE.toString(this);
		return converter.asString(this);
	}
	public static Location fromString(String fromJsonString) /*throws NoValidJSONStringException*/ {
//		return JSONHandler.INSTANCE.fromString(fromJsonString,Location.class);
		return converter .asObject(fromJsonString,Location.class);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Location)) {
			return false;
		}
		Location ol = (Location)o;
		
		Ln.d("comparing "+ol+" to "+this);
		
		if (ol.getLatitude() == latitude && ol.getLongitude() == longitude) {
			return true;
		}
		return false;
	}
	
	/**
	 *  just for geoaddress cache, using a map with internal tree set
	 *  it compares first on latitude
	 */
	@Override
	public int compareTo(Location another) {
		if (this.latitude < another.getLatitude()) return -1;
        if (this.latitude > another.getLatitude()) return +1;
        if (this.longitude < another.getLongitude()) return -1;
        if (this.longitude > another.getLongitude()) return +1;
		return 0;
	}
	
	@Override
	public String toString() {
		return "[latitude:"+latitude+",longitude:"+longitude+"]";
	}
}