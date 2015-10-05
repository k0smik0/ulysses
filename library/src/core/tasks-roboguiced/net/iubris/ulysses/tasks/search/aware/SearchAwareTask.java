/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UIyssesSearchAsyncTask.java is part of 'Ulysses'.
 ******************************************************************************/
package net.iubris.ulysses.tasks.search.aware;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.cache.exceptions.base.CacheTooOldException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.exceptions.base.StillSearchException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.tasks.search.RoboSearchAwareAsyncTask;
import net.iubris.ulysses.engine.searcher.aware.full.DefaultUlyssesSearcher;
import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.tasks.search.aware.exceptions.ZeroResultException;
import android.content.Context;
import android.os.Handler;

public class SearchAwareTask extends RoboSearchAwareAsyncTask<DefaultUlyssesSearcher,Void,Void,List<Place>> {

	@Inject protected UlyssesSearcher ulyssesSearcher;
	
	protected SearchAwareTask(Context context, Executor executor) {
		super(context, executor);
	}

	protected SearchAwareTask(Context context, Handler handler, Executor executor) {
		super(context, handler, executor);
	}

	protected SearchAwareTask(Context context, Handler handler) {
		super(context, handler);
	}

	protected SearchAwareTask(Context context) {
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
	 */
	@Override
	public List<Place> call() throws LocationTooNearException, LocationNotSoUsefulException, 
		NoNetworkException, CacheTooOldException, CacheEmptyException, CacheAwareSearchException,
		PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException, 
		StillSearchException, ZeroResultException {
		ulyssesSearcher.search();
		List<Place> result = ulyssesSearcher.getResult();
		
		if (result.size() == 0)
			throw new ZeroResultException();
		
		return ulyssesSearcher.getResult();
	}
	
	
}
