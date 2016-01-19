package net.iubris.ulysses.tasks.search.aware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.location.exceptions.LocationStateException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.location.exceptions.LocationAwareSearchException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.ulysses.engine.searcher.location.aware.cache.DefaultUlyssesLocalizedSearcherCacheAware;
import net.iubris.ulysses.engine.searcher.location.aware.cache.DefaultUlyssesLocalizedSearcherCacheAware.CacheSearchExceptions;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.model.Place;
import roboguice.util.Ln;
import android.location.Location;

// this model a LocationTooNearException throwing at the first search (just after install), 
// when connection occurs to be not available (it could be) and cache is (obviously) empty 
public class LocationExceptionStateNeverHappenedBefore_UsingMap implements LocationExceptionState {
	private final SearchAwareTaskLocationStateable searchAwareTask;
//	private final UIMessageForLocationStateNeverHappenedBeforHandler uiMessageHandler;
	private final LocationExceptionDefault_UsingMap locationExceptionDefault;
	
	private final Map<Class<? extends LocationStateException>, UIMessageForLocationStateNeverHappenedBeforHandler> uiMessageForLocationStateNeverHappenedBeforHandlersMap = new HashMap<>() ; 

	public LocationExceptionStateNeverHappenedBefore_UsingMap(SearchAwareTaskLocationStateable searchAwareTask, 
//			UIMessageForLocationStateNeverHappenedBeforHandler uiMessageHandler, 
			LocationExceptionDefault_UsingMap locationExceptionDefault,
			Map<Class<? extends LocationStateException>, UIMessageForLocationStateNeverHappenedBeforHandler> uiMessageForLocationStateNeverHappenedBeforHandlersMap) {
		this.searchAwareTask = searchAwareTask;
//		this.uiMessageHandler = uiMessageHandler;
		this.locationExceptionDefault = locationExceptionDefault;
		this.uiMessageForLocationStateNeverHappenedBeforHandlersMap.putAll(uiMessageForLocationStateNeverHappenedBeforHandlersMap);
	}

	@Override
	public void tryToSearch(Location location, Class<? extends LocationAwareSearchException> extendingLocationAwareSearchExceptionClass) throws NoNetworkException, NetworkAwareSearchException {
//		searchAwareTask.eventuallyHandleInUIPreSearchingOnLocationTooNearExceptionWhenNeverHappenedBefore(); // 1
//		uiMessageHandler.eventuallyHandleInUIPreSearching(); // 1
		UIMessageForLocationStateNeverHappenedBeforHandler uiMessageForLocationStateNeverHappenedBeforHandler = uiMessageForLocationStateNeverHappenedBeforHandlersMap.get(extendingLocationAwareSearchExceptionClass);
		if (uiMessageForLocationStateNeverHappenedBeforHandler != null) {
			uiMessageForLocationStateNeverHappenedBeforHandler.eventuallyHandleInUIPreSearching(); // 1
		}
		try {
			Ln.d("LocationTooNearExceptionStateNeverHappenedBefore: try with location: " + location);
			searchAwareTask.getLocalizedSearcher().search(location);
			searchAwareTask.setLocationExceptionState(this, locationExceptionDefault, extendingLocationAwareSearchExceptionClass);
		} catch (CacheEmptyException | CacheAwareSearchException e) {
			try {
				CacheSearchExceptions valueOf = DefaultUlyssesLocalizedSearcherCacheAware.CacheSearchExceptions .valueOf(e.getMessage());
//				searchAwareTask.eventuallyHandleInUISearchingFailedByCacheInLocationTooNearException(valueOf); // 2
//				uiMessageHandler.eventuallyHandleInUISearchingFailedByCache(valueOf); // 2
				if (uiMessageForLocationStateNeverHappenedBeforHandler != null) {
					uiMessageForLocationStateNeverHappenedBeforHandler.eventuallyHandleInUISearchingFailedByCache(valueOf); // 2
				}
			} catch (IllegalArgumentException e1) {
//				searchAwareTask.eventuallyHandleInUISearchingFailedByCacheInLocationTooNearExceptionWithoutSpecificReason(); // 3
//				uiMessageHandler.eventuallyHandleInUISearchingFailedByCacheWithoutSpecificReason();
				if (uiMessageForLocationStateNeverHappenedBeforHandler != null) {
					uiMessageForLocationStateNeverHappenedBeforHandler.eventuallyHandleInUISearchingFailedByCacheWithoutSpecificReason(); // 3
				}				
			}
		}
	}

	@Override
	public void completeExecution(Class<? extends LocationAwareSearchException> extendigLocationAwareSearchExceptionClass) {
		UlyssesLocalizedSearcher localizedSearcher = searchAwareTask.getLocalizedSearcher();
		List<Place> result = localizedSearcher.getResult();
		UIMessageForLocationStateNeverHappenedBeforHandler uiMessageForLocationStateNeverHappenedBeforHandler = uiMessageForLocationStateNeverHappenedBeforHandlersMap.get(extendigLocationAwareSearchExceptionClass);
		if (localizedSearcher.isFoundByCache()) {
//			searchAwareTask.eventuallyHandleInUISearchResultUsingCacheOnLocationTooNearExceptionIfNeverResultBefore(result); // 4
//			uiMessageHandler.eventuallyHandleInUISearchResultUsingCache(result); // 4
			if (uiMessageForLocationStateNeverHappenedBeforHandler != null) {
				uiMessageForLocationStateNeverHappenedBeforHandler.eventuallyHandleInUISearchResultUsingCache(result); // 4
			}
			searchAwareTask.setResultFoundByCache();
			searchAwareTask.doOnSuccess(result);
		} else {
//			searchAwareTask.eventuallyHandleInUISearchResultUsingNetworkOnLocationTooNearExceptionIfNeverResultBefore(result);
//			uiMessageHandler.eventuallyHandleInUISearchResultUsingNetwork(result);
			if (uiMessageForLocationStateNeverHappenedBeforHandler != null) {
				uiMessageForLocationStateNeverHappenedBeforHandler.eventuallyHandleInUISearchResultUsingNetwork(result);
			}
			searchAwareTask.setResultFoundByNetwork();
		}
	}
}