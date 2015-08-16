package net.iubris.ulysses.model;

import java.io.Serializable;

public class Location implements Serializable {
	private static final long serialVersionUID = 8145546699386327997L;
	private double latitude;
	private double longitude;
	public Location(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public Location() {}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
}