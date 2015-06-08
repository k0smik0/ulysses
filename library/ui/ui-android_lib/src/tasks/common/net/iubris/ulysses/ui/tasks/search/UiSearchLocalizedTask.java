/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UIyssesSearchAsyncTask.java is part of 'Ulysses'.
 ******************************************************************************/
package net.iubris.ulysses.ui.tasks.search;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.diane.aware.cache.exceptions.CacheStateException;
import net.iubris.diane.aware.cache.exceptions.base.CacheTooOldException;
import net.iubris.diane.aware.network.exceptions.NetworkStateException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.exceptions.AwareSearchException;
import net.iubris.diane.searcher.aware.exceptions.base.StillSearchException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.ulysses.R;
import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.tasks.search.localized.SearchLocalizedTask;
import net.iubris.ulysses.ui._di.progressdialog.search.annotations.ProgressDialogForSearchPlaces;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

/**
 * for searching task not location aware (provides only network/cache awarenesss)
 */
@Singleton
public class UiSearchLocalizedTask extends SearchLocalizedTask {

	protected final Resources resources;
	
	protected final String search__success;
	
	protected final String search__exception_no_network;
	protected final String search__exception_places_retrieving_status;
	protected final String search__exception_place_unbelievable_zero_result_status;
	protected final String search__exception_places_tyrannus_status;
	protected final String search__exception_generic;
	
	@Inject @ProgressDialogForSearchPlaces protected ProgressDialog progressDialog;
	
	@Inject UlyssesLocalizedSearcher ulyssesLocalizedSearcher;
	
	protected UiSearchLocalizedTask(Context context) {
		super(context);
		
		resources = context.getResources();
		search__success = resources.getString(R.string.search__success);
		
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
	
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onException(LocationNotSoUsefulException arg0) throws RuntimeException {
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
	 * default implementation executes {@link #eventuallyCancelSearchWaitingUi}, then 
	 * super {@link SearchLocalizedTask#} e.printStackTrace()
	 */
	@Override
	protected void onException(NullPointerException e) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
		e.printStackTrace();
	}
	/**
	 * default: executes {@link #eventuallyCancelSearchWaitingUi}
	 */
	@Override
	protected void onGenericException(Exception e) throws RuntimeException {
		eventuallyCancelSearchWaitingUi();
	}
	
	/**
	 * default: {@link ProgressDialog#show()}
	 */
	protected void eventuallyShowSearchWaitingUi() {
		progressDialog.show();
	}
	/**
	 * default: {@link ProgressDialog#cancel()}
	 */
	protected void eventuallyCancelSearchWaitingUi() {
		progressDialog.cancel();
	}
	
	/**
	 * do nothing
	 */
	protected void eventuallyShowErrorMessage(String errorMessage, Exception e) {}
	/**
	 * do nothing
	 */
	protected void eventuallyShowSuccessMessage(String successMessage, List<PlaceEnhanced> result) {}
}
