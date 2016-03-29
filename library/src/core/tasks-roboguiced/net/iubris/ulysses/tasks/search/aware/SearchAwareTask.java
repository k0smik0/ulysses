/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UIyssesSearchAsyncTask.java is part of 'Ulysses'.
 ******************************************************************************/
package net.iubris.ulysses.tasks.search.aware;

import java.util.concurrent.Executor;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.cache.exceptions.base.CacheTooOldException;
import net.iubris.diane.aware.location.exceptions.base.LocationFreshNullException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.exceptions.base.StillSearchException;
import net.iubris.diane.searcher.aware.full.exception.NoNetworkAndCacheEmptyException;
import net.iubris.diane.searcher.aware.location.exceptions.LocationAwareSearchException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.tasks.search.RoboSearchAwareAsyncTask;
import net.iubris.ulysses.engine.searcher.aware.full.DefaultUlyssesSearcher;
import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.tasks.search.aware.exceptions.ZeroResultException;
import net.iubris.ulysses.tasks.search.aware.locationstate.LocationExceptionState;
import net.iubris.ulysses.tasks.search.aware.locationstate.provider.MetaProviderForLocationExceptionStateForLocationNotSoUsefulException;
import net.iubris.ulysses.tasks.search.aware.locationstate.provider.MetaProviderForLocationExceptionStateForLocationTooNearException;
import roboguice.util.Ln;
import android.content.Context;
import android.location.Location;
import android.os.Handler;

@Singleton
public class SearchAwareTask extends RoboSearchAwareAsyncTask<DefaultUlyssesSearcher,Void,Void,List<Place>> implements SearchAwareTaskLocationStateable {

	@Inject 
	protected UlyssesSearcher ulyssesSearcher;
	@Inject 
	protected UlyssesLocalizedSearcher ulyssesLocalizedSearcher;
	
	@Inject
	protected MetaProviderForLocationExceptionStateForLocationNotSoUsefulException metaProviderForLocationExceptionStateForLocationNotSoUsefulException;
	@Inject
	protected MetaProviderForLocationExceptionStateForLocationTooNearException metaProviderForLocationExceptionStateForLocationTooNearException;
	
	private List<Place> result;
	private boolean foundByNetwork;
	private boolean foundByCache;
	
	
	protected SearchAwareTask(Context context, Executor executor) {
		super(context, executor);
	}
	protected SearchAwareTask(Context context, Handler handler, Executor executor) {
		super(context, handler, executor);
	}
	protected SearchAwareTask(Context context, Handler handler) {
		super(context, handler);
	}
	@Inject
	public SearchAwareTask(Context context) {
		super(context);
	}	
	/**
	 * calls {@link DefaultUlyssesSearcher#search()}
	 * @throws NetworkAwareSearchException 
	 * @throws StillSearchException 
	 * @throws PlacesTyrannusStatusException 
	 * @throws PlacesUnbelievableZeroResultStatusException 
	 * @throws PlacesRetrievingException 
	 * @throws NoNetworkException 
	 * @throws LocationNotSoUsefulException 
	 * @throws LocationTooNearException 
	 * @throws ZeroResultException 
	 * @throws  
	 */
	@Override
	public List<Place> call() throws LocationFreshNullException, LocationTooNearException, LocationNotSoUsefulException, 
		NoNetworkException, CacheTooOldException, CacheEmptyException, CacheAwareSearchException, NoNetworkAndCacheEmptyException,
		PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException, 
		StillSearchException, ZeroResultException  {
		ulyssesSearcher.search();
		result = ulyssesSearcher.getResult();
		if (result.size() == 0) {
			throw new ZeroResultException();
		}	
		return result;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onSuccess(List<Place> result) throws Exception {
		super.onSuccess(result);
		doOnSuccess(result);	
	}
	
	/**
	 * try to get any result using cache result
	 */
	@Override
	protected void onException(NoNetworkException e) throws RuntimeException {
		eventuallyHandleInUIPreSearchingByCacheInOnNoNetworkException(); // 1
		foundByNetwork = false;
		List<Place> result = ulyssesSearcher.getResult();
		int size = result.size();
		Ln.d("results: "+result.size());
		if (size==0) {
			Throwable cause = e.getCause();
			// if NoNetworkException && CacheEmpty 
			if (cause instanceof CacheEmptyException) {
				onException((CacheEmptyException)cause);
			} else {
				return;
			}
		} else { // results!
			// since we have some result, we change LocationExceptionStateS to default implementations
//			setLocationExceptionStatesDefault();
			doOnSuccess(result);
			if (ulyssesSearcher.isFoundByCache()) {
				eventuallyHandleInUISearchingFoundByCacheInOnNoNetworkException(); // 2
			} else { // here reached when results from last search (in-memory)
				eventuallyHandleInUISearchingFoundByNetworkUsingInMemoryResultsInOnNoNetworkException(); // 3
			}
		}
	}
	protected void eventuallyHandleInUIPreSearchingByCacheInOnNoNetworkException() {} // 1
	protected void eventuallyHandleInUISearchingFoundByCacheInOnNoNetworkException() {} // 2
//	protected void eventuallyHandleInUISearchingFailedByCacheInOnNoNetworkException() {}
//	protected void eventuallyHandleInUIAnyExceptionSearchingByCacheInOnNoNetworkException(Exception e) {}
	protected void eventuallyHandleInUISearchingFoundByNetworkUsingInMemoryResultsInOnNoNetworkException() {} // 3
	
	
	// LocationTooNear - start
	@Override
	protected void onException(LocationTooNearException e)  {
		super.onException(e);
		Ln.d("onException[LocationTooNearException]: "+e.getMessage());
		
		final Location location = ulyssesSearcher.getLocation();
		Ln.d("onException[LocationTooNearException]: using location: "+location);
		
		try {
			metaProviderForLocationExceptionStateForLocationTooNearException
				.eventuallyInit(this).get()			
				.tryToSearch( ulyssesSearcher.getLocation() );
		} catch (NoNetworkException e1) {
			onException(e1);
		} catch (NetworkAwareSearchException  e2) {
			onException(e2);
		}
	}
//	protected void eventuallyHandleInUIPreSearchingOnLocationTooNearExceptionWhenNeverHappenedBefore() {} // 1
//	protected void eventuallyHandleInUISearchingFailedByCacheInLocationTooNearException(CacheSearchExceptions cacheSearchExceptions) {}; // 2
//	protected void eventuallyHandleInUISearchingFailedByCacheInLocationTooNearException() {}; // 2
//	protected void eventuallyHandleInUISearchingFailedByCacheInLocationTooNearExceptionWithoutSpecificReason() {} // 3
//	protected void eventuallyHandleInUISearchResultUsingCacheOnLocationTooNearExceptionIfNeverResultBefore(List<Place> resultForced) {} // 4
//	protected void eventuallyHandleInUISearchResultUsingNetworkOnLocationTooNearExceptionIfNeverResultBefore(List<Place> resultForced) {} // 5	
//	protected void eventuallyShowErrorMessageOnLocationTooNearException(LocationTooNearException e) {}
//	protected void eventuallyShowErrorMessageOnLocationTooNearException() {} // 6
	// LocationTooNear - end
	
	// LocationNotSoUseful - start
	@Override
	protected void onException(LocationNotSoUsefulException e) {
		super.onException(e);
		Ln.d("onException[LocationNotSoUsefulException]: "+e.getMessage());
		
		final Location location = ulyssesSearcher.getLocation();
		Ln.d("onException[LocationNotSoUsefulException]: using location: "+location);
		
		try {
			metaProviderForLocationExceptionStateForLocationNotSoUsefulException
				.eventuallyInit(this).get()
				.tryToSearch( ulyssesSearcher.getLocation() );
		} catch (NoNetworkException e1) {
			onException(e);
		} catch (NetworkAwareSearchException e1) {
			onException(e);
		}
	}
//	protected void eventuallyHandleInUIPreSearchingOnLocationNotSoUsefulExceptionWhenNeverHappenedBefore() {}
//	protected void eventuallyHandleInUISearchingFailedByCacheInNotSoUsefulException(CacheSearchExceptions cacheSearchExceptions) {};
//	protected void eventuallyHandleInUISearchingFailedByCacheInNotSoUsefulExceptionWithoutSpecificReason() {}
//	protected void eventuallyHandleInUISearchResultUsingNetworkOnNotSoUsefulExceptionIfNeverResultBefore(List<Place> resultForced) {}
//	protected void eventuallyHandleInUISearchResultUsingCacheOnNotSoUsefulExceptionIfNeverResultBefore(List<Place> resultForced) {}
//	protected void eventuallyShowErrorMessageOnLocationNotSoUsefulException(LocationNotSoUsefulException e) {}
//	protected void eventuallyShowErrorMessageOnLocationNotSoUsefulException() {}
	// LocationNotSoUseful - end	

	@Override
	public UlyssesSearcher getSearcher() {
		return ulyssesSearcher;
	}
	@Override
	public UlyssesLocalizedSearcher getLocalizedSearcher() {
		return ulyssesLocalizedSearcher;
	}
	@Override
	public void doOnSuccess(List<Place> result) throws RuntimeException {
		setLocationExceptionStatesAfterFirstResult();
		if (ulyssesSearcher.isFoundByCache()) {
			setResultFoundByCache();
		} else {
			setResultFoundByNetwork();
		}
	}
		
	@Override
	public void setLocationExceptionState(LocationExceptionState currentLocationExceptionState, LocationExceptionState futureLocationExceptionState) {
		LocationExceptionState locationExceptionStateToBeCompletedExecution = currentLocationExceptionState;
		setLocationExceptionState(futureLocationExceptionState, futureLocationExceptionState.getHandlingLocationAwareSearchExceptionClass() );
		locationExceptionStateToBeCompletedExecution.completeExecution();
	}
	private void setLocationExceptionState(LocationExceptionState futureLocationExceptionState, Class<? extends LocationAwareSearchException> extendingLocationAwareSearchExceptionClass) {
		try {
			LocationStateExceptionClassHandler.valueOf(extendingLocationAwareSearchExceptionClass.getClass().getSimpleName()).setLocationExceptionState(futureLocationExceptionState, this);
		} catch (IllegalArgumentException e) {
			Ln.d("no LocationStateExceptionClassHandler instance for: "+extendingLocationAwareSearchExceptionClass);
		} 
	}
	private enum LocationStateExceptionClassHandler {
		LocationTooNearException {
			@Override
			public void setLocationExceptionState(LocationExceptionState futureLocationExceptionState, SearchAwareTask searchAwareTask) {
				searchAwareTask.metaProviderForLocationExceptionStateForLocationTooNearException.setLocationExceptionState(futureLocationExceptionState);
			}
		},
		LocationNotSoUsefulException {
			@Override
			public void setLocationExceptionState(LocationExceptionState futureLocationExceptionState, SearchAwareTask searchAwareTask) {
				searchAwareTask.metaProviderForLocationExceptionStateForLocationNotSoUsefulException.setLocationExceptionState(futureLocationExceptionState);
			}
		};
		abstract public void setLocationExceptionState(LocationExceptionState futureLocationExceptionState, SearchAwareTask searchAwareTask);
	}
	private void setLocationExceptionStatesAfterFirstResult() {
		this.metaProviderForLocationExceptionStateForLocationTooNearException.setAfterFirstResult();
		this.metaProviderForLocationExceptionStateForLocationNotSoUsefulException.setAfterFirstResult();
	}
	@Override
	public void setResultFoundByNetwork() {
		foundByNetwork = true;
		foundByCache = false;
	}
	@Override
	public void setResultFoundByCache() {
		foundByNetwork = false;
		foundByCache = true;
	}
	
	
	protected void onException(ZeroResultException arg0) throws RuntimeException {}
	protected void onException(CacheEmptyException e) throws RuntimeException {}
	
	public List<Place> getResult() {
		return result;
	}
	public boolean isFoundByCache() {
		return foundByCache;
	}
	public boolean isFoundByNetwork() {
		return foundByNetwork;
	}
	
}
