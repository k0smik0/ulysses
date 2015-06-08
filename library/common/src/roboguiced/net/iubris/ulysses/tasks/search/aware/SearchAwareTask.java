/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UIyssesSearchAsyncTask.java is part of 'Ulysses'.
 ******************************************************************************/
package net.iubris.ulysses.tasks.search.aware;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.exceptions.base.StillSearchException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.tasks.search.RoboSearchAwareAsyncTask;
import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import android.content.Context;
import android.os.Handler;

public class SearchAwareTask extends RoboSearchAwareAsyncTask<UlyssesSearcher,Void,Void,List<PlaceEnhanced>> {

	/*
	@InjectResource(name = "net.iubris.ulysses_ui:string/exception_place_unbelievable_zero_result_status")
	protected String zeroResult;
	
	@InjectResource(name = "net.iubris.ulysses_ui:string/exception_places_tyrannus_status")
	protected String tyrannusResult;

	@InjectResource(name = "exception_places_retrieving_status")
	protected String retrievingException;
	*/

//	protected UlyssesSearchAsyncTask(Context context) {
//		super(context);
//	}
	
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
	 * calls {@link UlyssesSearcher#search()}
	 * @throws NetworkAwareSearchException 
	 * @throws StillSearchException 
	 * @throws PlacesTyrannusStatusException 
	 * @throws PlacesUnbelievableZeroResultStatusException 
	 * @throws PlacesRetrievingException 
	 * @throws NoNetworkException 
	 * @throws LocationNotSoUsefulException 
	 * @throws LocationTooNearException 
	 */
	@Override
	public List<PlaceEnhanced> call() throws LocationTooNearException, LocationNotSoUsefulException, NoNetworkException, PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException, StillSearchException, PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException {
		ulyssesSearcher.search();
		return ulyssesSearcher.getResult();
	}
	

	/*
	 * @Override public abstract List<PlaceHere> call() throws // from diane //
	 * LocationTooNearException, LocationNotSoUsefulException,
	 * NoNetworkException, // own // PlacesRetrievingException,
	 * PlacesUnbelievableZeroResultStatusException,
	 * PlacesTyrannusStatusException, Exception;
	 */

	// @Override
	// protected void onException(LocationNotNewerStateException e) {
	// UIUtils.showShortToast(R.string.exception_location_not_newer_state, e,
	// context);
	// onException(e);
	// }

//	/*
//	 * socrates zone
//	 */
//	/**
//	 * handle Socrates's search exception
//	 * default implementation executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	protected void onException(PlacesRetrievingException e) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * handle Socrates's search exception
//	 * default implementation executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	protected void onException(PlacesUnbelievableZeroResultStatusException e) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * handle Socrates's search exception
//	 * default implementation executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	protected void onException(PlacesTyrannusStatusException e) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/*
//	 * end socrates zone
//	 */
//	
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(AwareSearchException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(CacheAwareSearchException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(CacheStateException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(CacheTooOldException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(LocationAwareSearchException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(LocationNotSoUsefulException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(LocationStateException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(LocationTooNearException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(NetworkAwareSearchException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(NetworkStateException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(NoNetworkException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(SearchException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onException(StillSearchException arg0) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	
//	/**
//	 * default implementation executes {@link #eventuallyCancelSearchWaitingUi}, then e.printStackTrace()
//	 */
//	@Override
//	protected void onException(NullPointerException e) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//		e.printStackTrace();
//	}
//	/**
//	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
//	 */
//	@Override
//	protected void onGenericException(Exception e) throws RuntimeException {
//		eventuallyCancelSearchWaitingUi();
//	}
//	
//	/**
//	 * default implementation do nothing: you could override showing a dialog, toast or whatever 
//	 */
//	protected void eventuallyShowSearchWaitingUi() {}
//	/**
//	 * default implementation do nothing: you could override canceling a running dialog, or whatever 
//	 */
//	protected void eventuallyCancelSearchWaitingUi() {}
}
