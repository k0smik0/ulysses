package net.iubris.ulysses.searcher.location.aware.full;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.iubris.diane.aware.cache.exceptions.base.CacheTooOldException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.searcher.location.aware.cache.LocalizedSearcherCacheAwareStrictChecking;
import net.iubris.diane.searcher.location.aware.full.base.DefaultLocalizedSearcherCacheNetworkAwareStrictChecking;
import net.iubris.diane.searcher.location.aware.network.LocalizedSearcherNetworkAwareStrictChecking;
import net.iubris.ulysses.model.PlaceHere;
import android.location.Location;
import android.util.Log;

public class UlyssesLocalizedSearcherCacheNetworkAware extends DefaultLocalizedSearcherCacheNetworkAwareStrictChecking<List<PlaceHere>> {

	@Inject
	public UlyssesLocalizedSearcherCacheNetworkAware(
			LocalizedSearcherCacheAwareStrictChecking<List<PlaceHere>> arg0,
			LocalizedSearcherNetworkAwareStrictChecking<List<PlaceHere>> arg1) {
		super(arg0, arg1);
		result = new ArrayList<PlaceHere>(0); // Null Object Pattern
	}
	
	@Override
	public Void search(Location... locations) throws NoNetworkException,
			NetworkAwareSearchException {
		try {
			Log.d("UlyssesLocalizedSearcherCacheNetworkAware:31","location: "+locations[0]);
			return super.search(locations);
		} catch (CacheTooOldException e) {
		} catch (CacheAwareSearchException e) {}
		return null;
	}

}
