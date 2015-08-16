package net.iubris.ulysses.engine.searcher.location.aware.full;

import java.util.List;

import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.searcher.location.aware.full.LocalizedSearcherCacheNetworkAwareStrictChecking;
import net.iubris.ulysses.model.Place;
import android.location.Location;

public interface UlyssesLocalizedSearcher 
extends LocalizedSearcherCacheNetworkAwareStrictChecking<List<Place>>{
	@Override
	public Void search(Location... arg0) throws NoNetworkException,
	NetworkAwareSearchException, CacheEmptyException;
}
