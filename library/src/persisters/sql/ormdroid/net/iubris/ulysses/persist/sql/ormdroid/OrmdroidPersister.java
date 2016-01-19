package net.iubris.ulysses.persist.sql.ormdroid;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.socrates.config.SearchOptions;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.persist.Persister;
import roboguice.util.Ln;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.ORMDroidApplication;
import com.roscopeco.ormdroid.ORMDroidException;

import static com.roscopeco.ormdroid.Query.eql;

@Singleton
public class OrmdroidPersister implements Persister {
	
	private final SearchOptions searchOptions;

	@Inject
	public OrmdroidPersister(Context context, SearchOptions searchOptions) {
		this.searchOptions = searchOptions;
		ORMDroidApplication.initialize(context);
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
	public void setPlaces(Collection<Place> places) {
		// just for debug
		String msg = "storing "+places.size()+" places";
		String s="";
		for (Place p: places) {
			s+="|"+p.getPlaceName();
		}
		s.replaceFirst("|", "");
		Ln.d(msg+": "+s);
		
		for (Place place : places) {
			try {
				String state;
				Place placeExistant = Entity.query(Place.class).where( eql(Place.PLACENAME_COLUMN,place.getPlaceName()) ).execute();
				if (placeExistant!=null && placeExistant.equals(place)) {
					state = "_same";
					Ln.d(placeExistant.getPlaceName()+": save_state="+state);
//					int oldId = placeExistant.getId();
//					place.setId(oldId);
//					state = ""+place.save();
					
//					do nothing
				} else {
					state = ""+place.save();
					Ln.d(place.getPlaceName()+": save_state="+state);
				}
				
			} catch(ORMDroidException e) {
				Ln.d("setPlaces exception: "+e.getMessage());
			}
		}
	}

	@Override
	public List<Location> getLocations() {
		return null;
	}

	@Override
	public boolean isLocationStored(Location location) {
		return false;
	}

	@Override
	public void setLocation(Location location) {}

}