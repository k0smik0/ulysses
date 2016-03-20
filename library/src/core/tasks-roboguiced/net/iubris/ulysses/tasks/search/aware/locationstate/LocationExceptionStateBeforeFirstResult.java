package net.iubris.ulysses.tasks.search.aware.locationstate;

import java.util.List;

import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.location.exceptions.LocationAwareSearchException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.ulysses.engine.searcher.location.aware.cache.DefaultUlyssesLocalizedSearcherCacheAware;
import net.iubris.ulysses.engine.searcher.location.aware.cache.DefaultUlyssesLocalizedSearcherCacheAware.CacheSearchExceptions;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.tasks.search.aware.SearchAwareTaskLocationStateable;
import net.iubris.ulysses.tasks.search.aware.locationstate.uimessage.UIMessageForLocationStateHandlerBeforeFirstResult;
import roboguice.util.Ln;
import android.location.Location;

// this models a LocationTooNearException throwing at the first search (just after install), 
// when connection occurs to be not available (it could be) and cache is (obviously) empty 
public class LocationExceptionStateBeforeFirstResult extends AbstractLocationExceptionState {
	private final UIMessageForLocationStateHandlerBeforeFirstResult uiMessageHandler;
	private final LocationExceptionAfterFirstResult locationExceptionDefault;

	public LocationExceptionStateBeforeFirstResult(SearchAwareTaskLocationStateable searchAwareTask,
			Class<? extends LocationAwareSearchException> extendingLocationAwareSearchExceptionClass,
			UIMessageForLocationStateHandlerBeforeFirstResult uiMessageHandler,
			LocationExceptionAfterFirstResult locationExceptionDefault
			) {
		super(searchAwareTask, extendingLocationAwareSearchExceptionClass);
		this.uiMessageHandler = uiMessageHandler;
		this.locationExceptionDefault = locationExceptionDefault;
	}

	@Override
	public void tryToSearch(Location location/*, Class<? extends LocationAwareSearchException> extendingLocationAwareSearchExceptionClass*/) throws NoNetworkException, NetworkAwareSearchException {
//		searchAwareTask.eventuallyHandleInUIPreSearchingOnLocationTooNearExceptionWhenNeverHappenedBefore(); // 1
		uiMessageHandler.eventuallyHandleInUIPreSearching(); // 1
		try {
			Ln.d("LocationTooNearExceptionStateNeverHappenedBefore: try with location: " + location);
			searchAwareTask.getLocalizedSearcher().search(location);
			searchAwareTask.setLocationExceptionState(this, locationExceptionDefault/*, extendingLocationAwareSearchExceptionClass*/);
		} catch (CacheEmptyException | CacheAwareSearchException e) {
			try {
				CacheSearchExceptions valueOf = DefaultUlyssesLocalizedSearcherCacheAware.CacheSearchExceptions .valueOf(e.getMessage());
//				searchAwareTask.eventuallyHandleInUISearchingFailedByCacheInLocationTooNearException(valueOf); // 2
				uiMessageHandler.eventuallyHandleInUISearchingFailedByCache(valueOf); // 2
			} catch (IllegalArgumentException e1) {
//				searchAwareTask.eventuallyHandleInUISearchingFailedByCacheInLocationTooNearExceptionWithoutSpecificReason(); // 3
				uiMessageHandler.eventuallyHandleInUISearchingFailedByCacheWithoutSpecificReason();
			}
		}
	}

	@Override
	public void completeExecution(/*Class<? extends LocationAwareSearchException> extendigLocationAwareSearchExceptionClass*/) {
		UlyssesLocalizedSearcher localizedSearcher = searchAwareTask.getLocalizedSearcher();
		List<Place> result = localizedSearcher.getResult();
		if (localizedSearcher.isFoundByCache()) {
//			searchAwareTask.eventuallyHandleInUISearchResultUsingCacheOnLocationTooNearExceptionIfNeverResultBefore(result); // 4
			uiMessageHandler.eventuallyHandleInUISearchResultUsingCache(result); // 4
			searchAwareTask.setResultFoundByCache();
			searchAwareTask.doOnSuccess(result);
		} else {
//			searchAwareTask.eventuallyHandleInUISearchResultUsingNetworkOnLocationTooNearExceptionIfNeverResultBefore(result);
			uiMessageHandler.eventuallyHandleInUISearchResultUsingNetwork(result);
			searchAwareTask.setResultFoundByNetwork();
		}
	}
}