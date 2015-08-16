package net.iubris.ulysses.engine.persist.sql.ormlite;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.ulysses.engine.persist.Persister;
import net.iubris.ulysses.model.Place;
import android.content.Context;
import android.location.Location;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.RuntimeExceptionDao;

@Singleton
public class OrmlitePersister implements Persister {
	
//	private final RuntimeExceptionDao<Place, Integer> placeDao;
//	private Context context;
	private UlyssesDatabaseHelper helper;

	@Inject
	public OrmlitePersister(Context context) {
//		this.context = context;
		helper = OpenHelperManager.getHelper(context, UlyssesDatabaseHelper.class);
	}

	@Override
	public Set<Place> searchPlaces(Location location) {
		Set<Place> results = new HashSet<Place>();
		
		try {
			RuntimeExceptionDao<Place, Integer> placeDao = 
//			OpenHelperManager.getHelper(context, UlyssesDatabaseHelper.class)
			helper
					.getPlaceDao();
			CloseableIterator<Place> iterator = placeDao.closeableIterator();
			while (iterator.hasNext()) {
				Place place = iterator.next();
				if (Place.distance(place, location) < 5000) {
					results.add(place);
				};
			}
			iterator.close();
//			OpenHelperManager.releaseHelper();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}


	@Override
	public void setPlaces(Collection<Place> places) {
		RuntimeExceptionDao<Place, Integer> placeDao = 
//				OpenHelperManager.getHelper(context, UlyssesDatabaseHelper.class)
				helper
				.getPlaceDao();
		for (Place place : places) {
			placeDao.createIfNotExists(place);
		}
//		OpenHelperManager.releaseHelper();
	}

	@Override
	public Set<Location> getLocations() { return null; }

	@Override
	public boolean isLocationStored(Location location) { return false; }

	@Override
	public void setLocation(Location location) {}
	
	public void releaseHelper() {
		OpenHelperManager.releaseHelper();
		helper = null;
	}

	@Override
	public void setPlaces(Collection<net.iubris.ulysses.engine.model.Place> arg0) {
		// TODO Auto-generated method stub
		
	}
}
