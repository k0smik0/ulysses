package net.iubris.ulysses.ui.tasks.search;

import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.diane.aware.cache.exceptions.CacheStateException;
import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.cache.exceptions.base.CacheTooOldException;
import net.iubris.diane.aware.location.exceptions.LocationStateException;
import net.iubris.diane.aware.network.exceptions.NetworkStateException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.exceptions.AwareSearchException;
import net.iubris.diane.searcher.aware.exceptions.base.StillSearchException;
import net.iubris.diane.searcher.aware.location.exceptions.LocationAwareSearchException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.ulysses.R;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.tasks.search.aware.SearchAwareTask;
import net.iubris.ulysses.tasks.search.aware.exceptions.ZeroResultException;
import net.iubris.ulysses.ui._di.progressdialog.search.annotations.ProgressDialogForSearchPlaces;
import roboguice.util.Ln;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.util.Log;

@Singleton
public class UISearchAwareTask extends SearchAwareTask {

	protected final Resources resources;
	
	protected final String search__success;
	
	protected final String search__exception_location_too_near;
	protected final String search__exception_location_not_so_useful;
	
	protected final String search__exception_no_network;
	protected final String search__exception_places_retrieving_status;
	protected final String search__exception_place_unbelievable_zero_result_status;
	protected final String search__exception_places_tyrannus_status;
	protected final String search__exception_generic;
	

	@Inject @ProgressDialogForSearchPlaces protected ProgressDialog progressDialog;
	
	
	protected UISearchAwareTask(Activity activity) {
		super(activity, Executors.newSingleThreadExecutor());
		
		resources = activity.getResources();
		search__success = resources.getString(R.string.search__success);
		
		search__exception_location_too_near = resources.getString(R.string.search__exception_location_too_near);
		search__exception_location_not_so_useful = resources.getString(R.string.search__exception_location_not_so_useful);
		
		search__exception_no_network = resources.getString(R.string.search__exception_no_network);
		search__exception_places_retrieving_status = resources.getString(R.string.search__exception_places_retrieving_status);
		search__exception_place_unbelievable_zero_result_status = resources.getString(R.string.search__exception_place_unbelievable_zero_result_status);
		search__exception_places_tyrannus_status = resources.getString(R.string.search__exception_places_tyrannus_status);
		search__exception_generic = resources.getString(R.string.search__exception_generic);
	}
	
	
	/**
	 * default implementation executes {@link #eventuallyShowSearchWaitingUi }
	 */
	@Override
	protected void onPreExecute() throws Exception {
		eventuallyShowSearchWaitingUi();
	}
	
	
	@Override
	protected void onSuccess(List<Place> places) throws Exception {
		doOnSuccess(places);
	}
	
	protected void doOnSuccess(List<Place> places) {
		if (ulyssesSearcher.isFoundByCache())
			eventuallyNotifyIsFoundByCache();
		
		eventuallyCancelSearchWaitingUi();
		
		printPhotosDetailsForDebug(places);
	}
	private void printPhotosDetailsForDebug(List<Place> places) {
		if (places==null)
			return;
		
		for (Place place : places) {
			List<String> photosUrls = place.getPhotosUrls();
			if (photosUrls!=null && photosUrls.size() > 1) {
				Ln.d(place.getPlaceName()+" has multiple photos:");
				Ln.d(photosUrls.toArray());
			}
		}
	}
	
	protected void eventuallyNotifyIsFoundByCache() {}
	
	protected void onException(ZeroResultException e) {
		eventuallyCancelSearchWaitingUi();
	}
	
	/**
	 * default: {@link ProgressDialog#show()}
	 */
	protected void eventuallyShowSearchWaitingUi() {
		if (!((Activity)context).isFinishing())
			progressDialog.show();
	}
	/**
	 * default: {@link ProgressDialog#cancel()}
	 */
	protected void eventuallyCancelSearchWaitingUi() {
		if (!((Activity)context).isFinishing())
			progressDialog.cancel();
	}
	
	/**
	 * default: do nothing
	 */
	protected void eventuallyShowErrorMessage(String errorMessage, Exception e) {}
	/**
	 * default: do nothing
	 */
	protected void eventuallyShowSuccessMessage(String successMessage, List<Place> result) {}
	
	
	
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(AwareSearchException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(CacheAwareSearchException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(CacheStateException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(CacheTooOldException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	
	
	protected void onException(CacheEmptyException e) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(LocationAwareSearchException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}, then
	 * {@link #eventuallyShowErrorMessage(String)} passing search__exception_location_not_so_useful
	 */
	@Override
	protected void onException(LocationNotSoUsefulException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
		eventuallyShowErrorMessage(search__exception_location_not_so_useful, arg0);
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(LocationStateException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(LocationTooNearException arg0) throws RuntimeException {
		Log.d("UISearchawareTask","LocationTooNearException");
		eventuallyCancelSearchWaitingUi();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(NetworkAwareSearchException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(NetworkStateException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(NoNetworkException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(SearchException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(StillSearchException arg0) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	
	/**
	 * default implementation executes {@link #eventuallyCancelSearchWaitingUi}, 
	 * then e.printStackTrace()
	 */
	@Override
	protected void onException(NullPointerException e) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
		e.printStackTrace();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}, then
	 * {@link #eventuallyShowErrorMessage(String)} passing 'search__exception_generic'
	 */
	@Override
	protected void onGenericException(Exception e) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
		eventuallyShowErrorMessage(search__exception_generic, e);
	}
	
	
	
	/*
	 * socrates zone
	 */
	/**
	 * handle Socrates's search exception
	 * default implementation executes {@link #eventuallyCancelSearchWaitingUi}, then
	 * {@link #eventuallyShowErrorMessage(String)} passing 'search__exception_places_retrieving_status'
	 */
	protected void onException(PlacesRetrievingException e) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
		eventuallyShowErrorMessage(search__exception_places_retrieving_status, e);
	}
	/**
	 * handle Socrates's search exception
	 * default implementation executes {@link #eventuallyCancelSearchWaitingUi}, then
	 * {@link #eventuallyShowErrorMessage(String)} passing 'search__exception_place_unbelievable_zero_result_status'
	 */
	protected void onException(PlacesUnbelievableZeroResultStatusException e) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
		eventuallyShowErrorMessage(search__exception_place_unbelievable_zero_result_status, e);
	}
	/**
	 * handle Socrates's search exception
	 * default implementation executes {@link #eventuallyCancelSearchWaitingUi}, then
	 * {@link #eventuallyShowErrorMessage(String)} passing 'search__exception_places_tyrannus_status'
	 */
	protected void onException(PlacesTyrannusStatusException e) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
		eventuallyShowErrorMessage(search__exception_places_tyrannus_status, e);
	}	
	/*
	 * end socrates zone
	 */
}
