package net.iubris.ulysses.tasks.search.aware.locationstate.uimessage;

import java.util.List;

import net.iubris.ulysses.engine.searcher.location.aware.cache.DefaultUlyssesLocalizedSearcherCacheAware.CacheSearchExceptions;
import net.iubris.ulysses.model.Place;

public interface UIMessageForLocationStateHandlerBeforeFirstResult extends UIMessageForLocationState {
	void eventuallyHandleInUIPreSearching();
	void eventuallyHandleInUISearchingFailedByCache(CacheSearchExceptions valueOf);
	void eventuallyHandleInUISearchingFailedByCacheWithoutSpecificReason();
	void eventuallyHandleInUISearchResultUsingCache(List<Place> result);
	void eventuallyHandleInUISearchResultUsingNetwork(List<Place> result);
}
