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


import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import roboguice.util.Ln;
import net.iubris.socrates.model.http.response.data.details.Details;

import com.roscopeco.ormdroid.Entity;


/**
 * a enhanced wrapper for {@link Place}, including:<br/>
 * 1. distance from actual location and Place's location<br/>
 * 2. {@link Details} for place 
 */

//@Entity(name="places")
//@DatabaseTable(tableName="places")
public class Place extends Entity implements Serializable, Comparable<Place> {
	
	private static final long serialVersionUID = 8907278452980472496L;
	private static final double UNREACHABLE_DISTANCE = 1000000000000L; // 10E12 m
	
//	@Id 
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	@DatabaseField()
	public int id;
	
//	@Column
	private String placeName;
//	@Column
	
	private String placeId;

//	
	private String locationAsString;
//	public static final int LocationColumn = 4;
	private static final String SEPARATOR = "#";
//	@Column	
//	@Convert(converter=LocationConverter.class, disableConversion=false)
//	private Location location;
	
//	@Column
	
	private String iconUrl;
//	@Column
	
	private float rating;
//	@Column
	
//	@Convert(converter=StringSetConverter.class)
//	private Set<String> types;
	private String typesAsString;
	private String vicinity;
//	@Column
	
//	@Convert(converter=StringListConverter.class)
//	private List<String> photosUrls;
	private String photosUrlsAsString;
	
	private boolean permanentlyClosed;

//	@Column
	
	private String formattedAddress;
//	@Column
	
	private String internationalPhoneNumber;
//	@Column
	
	private int reviewsCount;
//	@Column
	
	private String website;
	
	
//	@Column
	
	private double distance;
//	@Column
	
	private String plusUrl;
	
	

	
	public Place() {
		super();
	}
	
	/*public PlaceEnhanced(Place place, Location locationHere) {
		this.place = place;		
		this.distance = distance(place,locationHere);
	}
	
	public PlaceEnhanced(Place place, Location locationHere, Details details) {
		this(place, locationHere);
		this.details = details;
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
	public void setPlace(Place place) {
		this.place = place;
	}*/
	
	public Place(String name, String id, Location location, String iconUrl,
			float rating, 
			Set<String> types, 
			String vicinity,
			List<String> photosUrl, 
			boolean permanentlyClosed,
			String formattedAddress, String internationalPhoneNumber,
			String googlePlusUrl, 
			String website, int reviewsCount) {
		this(name,id, location, iconUrl, rating, types, vicinity, photosUrl, permanentlyClosed, 
				formattedAddress, internationalPhoneNumber, googlePlusUrl, website);
		this.reviewsCount = reviewsCount;
	}
	public Place(String name, String id, Location location, String iconUrl,
			float rating, Set<String> types, String vicinity,
			List<String> photosUrl, boolean permanentlyClosed,
			String formattedAddress, String internationalPhoneNumber,
			String plusUrl, String website) {
		this(name,id, location, iconUrl, rating, types, vicinity, photosUrl, permanentlyClosed);
		this.formattedAddress = formattedAddress;
		this.internationalPhoneNumber = internationalPhoneNumber;
		this.plusUrl = plusUrl;
		this.website = website;
	}
	public Place(String name, String placeId, Location location, String icon,
			float rating, Set<String> types, String vicinity,
			List<String> photosUrl, boolean permanentlyClosed) {
		this.placeName = name;
		this.placeId = placeId;
		this.locationAsString = stringifyLocation(location);
//		this.location = location;
		this.iconUrl = icon;
		this.rating = rating;
//		this.types = types;
		this.typesAsString = stringify(types);
		this.vicinity = vicinity;
//		this.photosUrls = photosUrl;
		this.photosUrlsAsString = stringify(photosUrl);
		this.permanentlyClosed = permanentlyClosed;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPlaceName() {
		return placeName;
	}
	public void setName(String name) {
		this.placeName = name;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	
//	public String getLocationAsString() {
//		return locationAsString;
//	}
//	public void setLocationAsString(String locationAsString) {
//		this.locationAsString = locationAsString;
//	}
	public Location getLocation() {
		return destringifyLocation(locationAsString);
//		return location;
	}
	private Location destringifyLocation(String locationAsString) {
		String[] split = locationAsString.split(SEPARATOR);
		return new Location(Double.parseDouble( split[0] ), Double.parseDouble( split[1] ));
	}
	public void setLocation(Location location) {
		this.locationAsString = stringifyLocation(location);
//		this.location = location;
	}
	private String stringifyLocation(Location location) {
		return location.getLatitude()+SEPARATOR+location.getLongitude();
	}

	public String getIcon() {
		return iconUrl;
	}
	public void setIcon(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}

	public Set<String> getTypes() {
//		return types;
		return destringifySet(typesAsString);
	}
	protected Set<String> destringifySet(String dataAsString) {
//		Ln.d(dataAsString);
		String[] split = dataAsString.split(SEPARATOR);
//		Ln.d(split.length);
		List<String> asList = Arrays.asList(split);
//		Ln.d(asList.size());
		Set<String> hashSet = new HashSet<String>( asList );
//		Ln.d(hashSet.size());
		return hashSet;
	}

	public void setTypes(Set<String> types) {
		this.typesAsString = stringify(types);
//		this.types = types;
	}
	protected String stringify(Set<String> data) {
		if (data==null)
			return "";
		
		StringBuilder sb = new StringBuilder();
		for (String string : data) {
			sb.append(string).append(SEPARATOR);
		}
		if (data.size()>0)
			sb.deleteCharAt(sb.length()-1); //delete last separator
		return sb.toString();
	}
	

	public String getVicinity() {
		return vicinity;
	}
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public List<String> getPhotosUrls() {
		return destringify(photosUrlsAsString);
//		return photosUrls;
	}
	protected List<String> destringify(String dataAsString) {
		String[] split = dataAsString.split(SEPARATOR);
		return Arrays.asList(split);
	}

	public void setPhotosUrls(List<String> photosUrls) {
		this.photosUrlsAsString = stringify( photosUrls );
	}
	protected String stringify(List<String> data) {
		if (data==null)
			return "";
		
		StringBuilder sb = new StringBuilder();
		for (String string : data) {
			sb.append(string).append(SEPARATOR);
		}
		if (data.size()>0)
			sb.deleteCharAt(sb.length()-1); //delete last separator
		return sb.toString();
	}


	public boolean isPermanentlyClosed() {
		return permanentlyClosed;
	}
	public void setPermanentlyClosed(boolean permanentlyClosed) {
		this.permanentlyClosed = permanentlyClosed;
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public String getInternationalPhoneNumber() {
		return internationalPhoneNumber;
	}
	public void setInternationalPhoneNumber(String internationalPhoneNumber) {
		this.internationalPhoneNumber = internationalPhoneNumber;
	}

	public int getReviewsCount() {
		return reviewsCount;
	}
	public void setReviewsCount(int reviewsCount) {
		this.reviewsCount = reviewsCount;
	}
	
	public String getPlacesUri() {
		return plusUrl;
	}
	public void setPlacesUri(String plusUrl) {
		this.plusUrl = plusUrl;
	}

	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}

	
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public void resetDistance() {
		this.distance = UNREACHABLE_DISTANCE;
	}

	public static float distance(Place place, android.location.Location locationHere) {
		Location placeLoc = place.getLocation();
		
//		Ln.d("comparing "+placeLoc+" with "+locationHere);
		
		final android.location.Location placeLocation = new android.location.Location("DummyProvider");
		placeLocation.setLatitude( placeLoc.getLatitude() );
		placeLocation.setLongitude( placeLoc.getLongitude() );
		
		return locationHere.distanceTo(placeLocation);
	}

	@Override
	public int compareTo(Place another) {
		if (this.getDistance() < another.getDistance())
			return -1;
		if (this.getDistance() > another.getDistance())
			return 1;
		return 0;
	}
	
	@Override
	public boolean equals(Object other) {
		String placeId2 = ((Place)other).getPlaceId();
		String placeName2 = ((Place)other).getPlaceName();
		
//		Ln.d(placeId+" "+placeId2);
//		Ln.d(placeName+" "+placeName2);
		
		if (placeId.equals(placeId2) &&
				placeName.equals(placeName2))
				return true;
		return false;
	}
	
	/*public static class Location implements Serializable {
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
	}*/
}
