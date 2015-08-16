package net.iubris.ulysses.persist.sql.ormdroid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.persist.Persister;
import roboguice.util.Ln;
import android.content.Context;
import android.location.Location;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.ORMDroidApplication;
import static com.roscopeco.ormdroid.Query.eql;

@Singleton
public class OrmdroidPersister implements Persister {
	
	@Inject
	public OrmdroidPersister(Context context) {
		ORMDroidApplication.initialize(context);
	}

	@Override
	public List<Place> searchPlaces(Location location) {
		List<Place> results = new ArrayList<Place>();
		List<Place> places = Entity.query(Place.class).executeMulti();
		Iterator<Place> iterator = places.iterator();
		while (iterator.hasNext()) {
			Place place = iterator.next();
			if (Place.distance(place, location) < 5000) {
				results.add(place);
			};
		}
		return results;
	}

	@Override
	public List<Place> getPlaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPlaces(Collection<Place> places) {
		Ln.d("storing "+places.size()+" places");
		for (Place place : places) {
			try {
				Place placeFrom = Entity.query(Place.class).where( eql("placeName",place.getPlaceName()) ).execute();
//				if (placeFrom!=null)
					placeFrom=place;
				int state = placeFrom.save();
				Ln.d(state);
			} catch(Exception e) {
				Ln.d(e.getMessage());
			}
		}
	}

	@Override
	public List<Location> getLocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLocationStored(Location location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setLocation(Location location) {
		// TODO Auto-generated method stub

	}

}