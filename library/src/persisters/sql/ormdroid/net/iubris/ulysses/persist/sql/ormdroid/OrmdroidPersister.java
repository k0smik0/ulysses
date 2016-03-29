package net.iubris.ulysses.persist.sql.ormdroid;


import static com.roscopeco.ormdroid.Query.eql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.socrates.config.SearchOptions;
import net.iubris.ulysses.model.GeoAddress;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.persist.Persister;
import roboguice.util.Ln;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.ORMDroidApplication;
import com.roscopeco.ormdroid.ORMDroidException;

@Singleton
public class OrmdroidPersister implements Persister {
	
	private final SearchOptions searchOptions;

	@Inject
	public OrmdroidPersister(Context context, SearchOptions searchOptions) {
		this.searchOptions = searchOptions;
		ORMDroidApplication.initialize(context);
//		ORMDroidApplication.
		SQLiteDatabase database = ORMDroidApplication.getDefaultDatabase();
		Ln.d("database: "+database.getPath());
//		ORMDroidApplication.getSingleton().enableLoggingVerbose(true);
	}

	@Override
	public List<Place> searchPlaces(Location location) {
		List<Place> results = new ArrayList<Place>();
		List<Place> places = Entity.query(Place.class).executeMulti();
		
		Iterator<Place> iterator = places.iterator();
		while (iterator.hasNext()) {
			Place place = iterator.next();
			if (Place.distance(place, location) < searchOptions.getRadius()) {
				results.add(place);
			};
		}
		return results;
	}

	@Override
	public List<Place> getPlaces() {
		List<Place> places = Entity.query(Place.class).executeMulti();
		return places;
	}

	@Override
	public void savePlaces(Collection<Place> places) {
		// just for debug
//		String s="";
//		int reallyStored = 0;
		// end debug
		for (Place place : places) {			
			try {
				// insert only if not existant
				/*
				String state;
				Place placeExistant = Entity.query(Place.class).where( eql(Place.PLACENAME_COLUMN,place.getPlaceName()) ).execute();
				if (placeExistant!=null && placeExistant.equals(place)) {
					state = "_same";
//					Ln.d(placeExistant.getPlaceName()+": save_state="+state);
//					int oldId = placeExistant.getId();
//					place.setId(oldId);
//					state = ""+place.save();
					
//					do nothing
				} else {
					state = ""+place.save();
					Ln.d(place.getPlaceName()+": save_state="+state);
					
					s+="|"+place.getPlaceName();
					reallyStored++;
				}*/
				
				// update if existant
				Place placeExistant = Entity.query(Place.class).where( eql(Place.PLACENAME_COLUMN, place.getPlaceName()) ).execute();
				if (placeExistant!=null) {
//					placeExistant.delete();
//					place.save();
					placeExistant.updateValues(place.getPlaceName(), 
							place.getPlaceId(), 
							place.getLocation(), 
							place.getIconUrlString(), 
							place.getRating(),
							place.getTypes(),
							place.getVicinity(),
							place.getPhotosUrls(),
							place.isPermanentlyClosed(),
							place.getFormattedAddress(),
							place.getInternationalPhoneNumber(),
							place.getReviews(),
							place.getWebsite(),
							place.getDistance(),
							place.getPlusUrl());
//					Ln.d("place:"+placeExistant+" existant, to update");
				} else {
					placeExistant = place;
//					Ln.d("place:"+placeExistant+" new, to insert");
				}
				placeExistant.save();
			} catch(ORMDroidException e) {
				Ln.d("setPlaces exception: "+e.getMessage());
			}
		}
		// just for debug - used in "not existant IF"
//		s.replaceFirst("|", "");
//		String msg = "storing "+reallyStored+"/"+places.size()+" places";
//		Ln.d(msg+": "+s);		
	}

/*	@Override
	public List<Location> getLocations() {
		return null;
	}

	@Override
	public boolean isLocationStored(Location location) {
		return false;
	}

	@Override
	public void setLocation(Location location) {}*/

	@Override
	public Collection<GeoAddress> getGeoAddresses() {
		List<GeoAddress> geoAddresses = Entity.query(GeoAddress.class).executeMulti();
		if (geoAddresses==null) {
			Ln.d("geoAddresses was null...");
			geoAddresses = Collections.emptyList();
		}
		return geoAddresses;
	}
	@Override
	public void saveGeoAddress(GeoAddress geoAddress) {
		GeoAddress geoaddressExistant = Entity.query(GeoAddress.class).where( eql(GeoAddress.ADDRESS_COLUMN, geoAddress.getFormattedAddress()) ).execute();
		/*String state;
		if (geoaddressExistant!=null && geoaddressExistant.equals(geoAddress)) {
			state = "_same";
//			Ln.d(placeExistant.getPlaceName()+": save_state="+state);
//			int oldId = placeExistant.getId();
//			place.setId(oldId);
//			state = ""+place.save();
			
//			do nothing
		} else {
			state = ""+geoAddress.save();
			Ln.d(geoAddress.getFormattedAddress() +": save_state="+state);
		}*/
		if (geoaddressExistant==null) {
			geoaddressExistant = geoAddress;
			Ln.d("inserted geoaddress: "+geoAddress);
		} else {
			// update
			Ln.d("updating geoaddress: "+geoaddressExistant);
			geoaddressExistant.setFormattedAddress( geoAddress.getFormattedAddress() );
			geoaddressExistant.setLocation( geoAddress.getLocation() );
			Ln.d("updated geoaddress: "+geoaddressExistant);
		}
		geoaddressExistant.save();
	}
	
	@Override
	public GeoAddress find(GeoAddress geoAddress) {
		List<GeoAddress> executeMulti = Entity.query(GeoAddress.class).executeMulti();
		for (GeoAddress ga : executeMulti) {
			if (ga.getLocation().equals(geoAddress.getLocation())) {
				Ln.d("GeoAddress:"+ga);
				return ga;
			}
		}
		return null;
	}

}