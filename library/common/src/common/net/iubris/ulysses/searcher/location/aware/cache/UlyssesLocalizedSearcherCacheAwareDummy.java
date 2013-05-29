package net.iubris.ulysses.searcher.location.aware.cache;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import net.iubris.diane.aware.cache.states.three.ThreeStateCacheAware;
import net.iubris.diane.searcher.location.aware.cache.base.AbstractLocalizedSearcherCacheAwareStrictChecking;
import net.iubris.ulysses.model.PlaceHere;
import android.location.Location;

public class UlyssesLocalizedSearcherCacheAwareDummy extends AbstractLocalizedSearcherCacheAwareStrictChecking<List<PlaceHere>> {

	@Inject
	public UlyssesLocalizedSearcherCacheAwareDummy(ThreeStateCacheAware arg0) {
		super(arg0);
	}
	
	@Override
	public Void search(Location... location) /*throws CacheTooOldException,
			CacheAwareSearchException*/ {
		return null;
	}

	@Override
	public List<PlaceHere> getResult() {
		return Collections.emptyList();
	}

	@Override
	protected void doSearch(Location arg0) /*throws CacheAwareSearchException */{}

}
