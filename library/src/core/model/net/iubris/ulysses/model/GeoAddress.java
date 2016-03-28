package net.iubris.ulysses.model;

import java.io.Serializable;

import com.roscopeco.ormdroid.Column;
import com.roscopeco.ormdroid.Entity;

public class GeoAddress extends Entity implements Serializable {

	private static final long serialVersionUID = -9140826814945860421L;
	
	public static final String ADDRESS_COLUMN = "formatted_address";
	
	@Column(primaryKey=true, name="id",forceMap=true)
	private int id;
	
	private Location location;
	@Column(forceMap=true,name="location_as_string")
	private String locationAsString;
	
	// we can use formatted address since address reading is localized-dependant
	@Column(forceMap=true,name=ADDRESS_COLUMN)
	private String formattedAddress;
	
	public GeoAddress(Location location, String formattedAddress) {
		super();
		setLocation(location);
		this.formattedAddress = formattedAddress;
	}
	
	public GeoAddress() {
		super();
	}

	public Location getLocation() {
		if (location==null) {
			Location.fromString(locationAsString);
		}
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
		this.locationAsString = location.asString();
	}
	public String getLocationAsString() {
		return locationAsString;
	}

	

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other==null) {
			return false;
		}
		GeoAddress oGA = (GeoAddress) other;
		if (oGA.getFormattedAddress()==formattedAddress && oGA.getLocation()==location) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return GeoAddress.class.getSimpleName()+":[id:"+id+",location:"+location+",formattedAddress:'"+formattedAddress+"']";
	}
}
