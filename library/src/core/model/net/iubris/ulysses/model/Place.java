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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.iubris.ulysses.data.Converter;
import net.iubris.ulysses.data.Converter.Stringable;
import roboguice.util.Ln;

import com.roscopeco.ormdroid.Column;
import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Table;
//import roboguice.util.Ln;


/**
 * a enhanced wrapper for {@link com.google.android.gms.location.places.Place}, including:<br/>
 * 1. distance from actual location and Place's location<br/>
 * 2. place details from {@link net.iubris.socrates.model.http.response.data.details.Details} 
 */

//@Entity(name="places")
//@DatabaseTable(tableName="places")
@Table(name="place")
public class Place extends Entity implements Serializable, Comparable<Place> {
	
	private static final long serialVersionUID = 8907278452980472496L;
	public static final double UNREACHABLE_DISTANCE = 1000000000000L; // 10E12 m - distance Earth-Moon=10E8m...
	
	public static final String PLACENAME_COLUMN = "placeName";
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	@DatabaseField()
	@Column(primaryKey=true, name="id",forceMap=true)
	private int id;
	
	@Column(forceMap=true)
	private String placeName;
	
	@Column(forceMap=true)
//	@Column(primaryKey=true, name="id",forceMap=true)
	private String placeId;


	private Location location;
	@Column(forceMap=true)
	private String locationAsString;
//	public static final int LocationColumn = 4;
//	private static final String SEPARATOR = "#";
//	@Column(forceMap=true)	
//	@Convert(converter=LocationConverter.class, disableConversion=false)
	
	@Column(forceMap=true)
	private String iconUrlString;
	
	private Set<String> types;
	@Column(forceMap=true)
//	@Convert(converter=StringSetConverter.class)
	private String typesAsString;
	
	@Column(forceMap=true)
	private String vicinity;
	
	private List<String> photosUrls;
	@Column(forceMap=true)
//	@Convert(converter=StringListConverter.class)
	private String photosUrlsAsString;

	@Column(forceMap=true)
	private boolean permanentlyClosed;

	@Column(forceMap=true)
	private String formattedAddress;
	
	@Column(forceMap=true)
	private String internationalPhoneNumber;
	
	private List<Review> reviews;
	@Column(forceMap=true)
	private String reviewsAsString;
	
	@Column(forceMap=true)
	private float rating;
	
//	@Column(forceMap=true)
//	private int reviewsCount;
	
	@Column(forceMap=true)
	private String website;
	
	@Column(forceMap=true)
	private double distance = UNREACHABLE_DISTANCE;
	
	@Column(forceMap=true)
	private String googlePlusUrl;
	
	private final Converter converter = Converter.INSTANCE;
	
	
	
	public Place() {
		super();
	}
	
	public Place(String placeName, String placeId, Location location, String iconUrlString,
			float rating, 
			Set<String> types, 
			String vicinity,
			List<String> photosUrl, 
			boolean permanentlyClosed,
			String formattedAddress, String internationalPhoneNumber,
			String googlePlusUrl, 
			String website, List<Review> reviews) {
		this(placeName, placeId, location, iconUrlString, rating, types, vicinity, photosUrl, permanentlyClosed, formattedAddress, internationalPhoneNumber, googlePlusUrl, website);
		setReviews(reviews);
//		this.reviewsCount = reviewsCount;
	}
	public Place(String placeName, String placeId, Location location, String iconUrlString,
			float rating, Set<String> types, String vicinity,
			List<String> photosUrl, boolean permanentlyClosed,
			String formattedAddress, String internationalPhoneNumber,
			String googlePlusUrl, String website) {
		this(placeName, placeId, location, iconUrlString, rating, types, vicinity, photosUrl, permanentlyClosed);
		this.formattedAddress = formattedAddress;
		this.internationalPhoneNumber = internationalPhoneNumber;
		this.googlePlusUrl = googlePlusUrl;
		this.website = website;
	}
	public Place(String placeName, String placeId, Location location, String icon,
			float rating, Set<String> types, String vicinity,
			List<String> photosUrl, boolean permanentlyClosed) {
		super();
		this.placeName = placeName;
		this.placeId = placeId;
//		this.locationAsString = stringifyLocation(location);
//		this.location = location;
		setLocation(location);
		this.iconUrlString = icon;
		this.rating = rating;
//		this.types = types;
//		this.typesAsString = stringify(types);
		setTypes(types);
		this.vicinity = vicinity;
//		this.photosUrls = photosUrl;
//		this.photosUrlsAsString = stringify(photosUrl);
		setPhotosUrls(photosUrl);
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
	public void setPlaceName(String name) {
		this.placeName = name;
	}
	
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	
	public String getLocationAsString() {
		
		return locationAsString;
	}
//	public void setLocationAsString(String locationAsString) {
//		this.locationAsString = locationAsString;
//	}
	public Location getLocation() {
		if (location==null) {
//			try {
//				location = Location.fromJsonString(locationAsString);
//			} catch (NoValidJSONStringException e) {
//				return location;
//			}
			location = Location.fromString(locationAsString);
		}
//		return destringifyLocation(locationAsString);
		return location;
	}
//	private Location destringifyLocation(String locationAsString) {
//		String[] split = locationAsString.split(SEPARATOR);
//		return new Location(Double.parseDouble( split[0] ), Double.parseDouble( split[1] ));
//	}
	public void setLocation(Location location) {
		this.location = location;
		this.locationAsString = location.asString();
	}
//	private String stringifyLocation(Location location) {
//		return location.getLatitude()+SEPARATOR+location.getLongitude();
//	}

	public String getIconUrlString() {
		return iconUrlString;
	}
	public void setIconUrlString(String iconUrlString) {
		this.iconUrlString = iconUrlString;
	}

	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}

	public Set<String> getTypes() {
//		return destringifyToSet(typesAsString);
		if (types==null) {
			// using json
			/*try {
				types = (Set<String>)JSONHandler.INSTANCE.fromString(typesAsString, Set.class);
			} catch (NoValidJSONStringException e) {
				return Collections.emptySet();
			}*/
			new HashSet<String>(converter.asList(typesAsString));
		}
		return types;
	}
//	protected Set<String> destringifyToSet(String dataAsString) {
//		Set<String> hashSet = new HashSet<String>( destringifyToList(dataAsString) );
////		Ln.d(hashSet.size());
//		return hashSet;
//	}

	public void setTypes(Set<String> types) {
		this.types = types;
		this.typesAsString = 
//				stringify(types);
//				JSONHandler.INSTANCE.toString(types)
				converter.asString(types);
	}
//	protected String stringify(Set<String> data) {
//		if (data==null)
//			return "";
//		
//		StringBuilder sb = new StringBuilder();
//		for (String string : data) {
//			sb.append(string).append(SEPARATOR);
//		}
//		if (data.size()>0)
//			sb.deleteCharAt(sb.length()-1); //delete last separator
//		return sb.toString();
//	}
	

	public String getVicinity() {
		return vicinity;
	}
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public List<String> getPhotosUrls() {
		if (photosUrls==null) {
			// using json
			/*try {
				photosUrls = (List<String>)JSONHandler.INSTANCE.fromString(photosUrlsAsString, List.class);
			} catch (NoValidJSONStringException e) {
//				e.printStackTrace();
				return Collections.emptyList();
			}*/
			photosUrls = converter.asList(photosUrlsAsString);
		}		
		return photosUrls;
	}
	/*protected List<String> destringifyToList(String dataAsString) {
		if (dataAsString.isEmpty()) {
			return null;
		}
		String[] split = dataAsString.split(SEPARATOR);
		return Arrays.asList(split);
	}*/

	public void setPhotosUrls(List<String> photosUrls) {
		this.photosUrls = photosUrls;
		this.photosUrlsAsString = 
//				stringify( photosUrls )
//				JSONHandler.INSTANCE.toString(photosUrls);
				converter.asString(photosUrls);
	}
	/*protected String stringify(Collection<String> data) {
		if (data==null)
			return "";
		
		StringBuilder sb = new StringBuilder();
		for (String string : data) {
			sb.append(string).append(SEPARATOR);
		}
		if (data.size()>0)
			sb.deleteCharAt(sb.length()-1); //delete last separator
		return sb.toString();
	}*/


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
	
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
		Ln.d("storing "+reviews.size()+"as string");
		this.reviewsAsString = converter.asString(reviews);
		Ln.d("reviewsAsString "+reviewsAsString);
	}
	public List<Review> getReviews() {
		if (reviews==null) {
			/*List<String> asList = converter.asList(reviewsAsString);
			reviews = new ArrayList<>();
			for (String string : asList) {
				reviews.add(Review.fromString(string));
			}*/
			Ln.d("place:"+placeName);
			Ln.d("reviewsAsString:"+reviewsAsString);
			reviews = converter.asListRecursive(reviewsAsString, Review.class);
			Ln.d("num reviews: "+reviews.size());
		}
		return reviews;
	}
	public void addReview(Review review) {
		reviews.add(review);		
	}

	public int getReviewsCount() {
		return getReviews().size();
	}
//	public void setReviewsCount(int reviewsCount) {
//		this.reviewsCount = reviewsCount;
//	}
	
	public String getPlusUrl() {
		return googlePlusUrl;
	}
	public void setPlusUrl(String plusUrl) {
		this.googlePlusUrl = plusUrl;
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
		if (!(other instanceof Place)) {
			return false;
		}
		Place p = ((Place)other);
		String placeId2 = p.getPlaceId();
		String placeName2 = p.getPlaceName();
		
//		Ln.d(placeId+" "+placeId2);
//		Ln.d(placeName+" "+placeName2);
		
		if ( placeId.equals(placeId2) && placeName.equals(placeName2) ) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String msg = "";
		msg += placeName+" "+distance+" "/*+photosUrlsAsString+" "+placeId*/;
		return msg;
	}

	public void updateValues(String placeName, String placeId,
			net.iubris.ulysses.model.Location location, String iconUrlString,
			float rating, Set<String> types, String vicinity,
			List<String> photosUrls, boolean permanentlyClosed,
			String formattedAddress, String internationalPhoneNumber,
			List<Review> reviews, String website, double distance, String plusUrl) {
		setPlaceName(placeName);
		setPlaceId(placeId);
		setLocation(location);
		setIconUrlString(iconUrlString);
		setRating(rating);
		setTypes(types);
		setVicinity(vicinity);
		setPhotosUrls(photosUrls);
		setPermanentlyClosed(permanentlyClosed);
		setFormattedAddress(formattedAddress);
		setInternationalPhoneNumber(internationalPhoneNumber);
		setReviews(reviews);
//		setReviewsCount(reviewsCount);
		setWebsite(website);
		setDistance(distance);
		setPlusUrl(plusUrl);
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
	
	public static class Review implements Stringable, Serializable/*, Comparable<Review>*/ {

		private static final long serialVersionUID = -2584426579916379622L;
		
		private String authorName;
		private URI authorUri;
		private long time;
		private String text;		
//		private String authorUrlAsString;
		
		private static final String SEPARATOR = "|"; 

		public Review(String authorName, URI authorUrl, long time, String text) {
			this.authorName = authorName;
			this.authorUri = authorUrl;
			this.time = time;
			this.text = text;
		}
		public Review(Review review) {
			this(review.getAuthorName(), review.getAuthorUri(), review.getTime(), review.getText());
		}
		public Review(String fromString) {
			this(fromString(fromString));
		}
		public Review() {}
		public String getAuthorName() {
			return authorName;
		}
		public void setAuthorName(String authorName) {
			this.authorName = authorName;
		}
		public URI getAuthorUri() {
			return authorUri;
		}
		public void setAuthorUri(URI authorUri) {
			this.authorUri = authorUri;
//			this.authorUrlAsString = authorUrl.toString();
		}

		public long getTime() {
			return time;
		}
		public void setTime(long time) {
			this.time = time;
		}

		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
		@Override
		public String asString() {
			return authorName+SEPARATOR+authorUri+SEPARATOR+time+SEPARATOR+text;
		}
		public static Review fromString(String fromString) /*throws NoValidJSONStringException*/ {
//			return JSONHandler.INSTANCE.fromString(fromJsonString,Location.class);
//			return converter.as asLocation(fromString,Location.class);
//			Ln.d("fromString:"+fromString);
			String[] split = fromString.split("\\"+SEPARATOR);
			int length = split.length;
//			for (int i=0;i<split.length;i++) {
//				Ln.d("split["+i+"]:"+split[i]);
//			}
			try {
//				Ln.d("the long: "+split[2]);
				String authorName = split[0];
				URI authorUri = new URI(split[1]);
				long time = Long.parseLong(split[2]);
				String text = (length==4?split[3]:"");
				return new Review(authorName, authorUri, time, text);
			} catch (NumberFormatException | URISyntaxException e) {
//				e.printStackTrace();
				String text = (length==4?split[3]:"");
				return new Review(split[0], null, Long.parseLong(split[2]), text);
			}
		}
		
		@Override
		public String toString() {
			return "[authorName:"+authorName+",authorUri:"+authorUri+",time:"+time+",text:'"+text+"']";
		}
		
		/*@Override
		public int compareTo(Review another) {
			if (time<another.getTime()) {
				return -1;
			};
			if (time>another.getTime()) {
				return 1;
			};
			return 0;
		}*/		
	}
	
}
