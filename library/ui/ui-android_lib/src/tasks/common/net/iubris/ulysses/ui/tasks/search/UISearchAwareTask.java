package net.iubris.ulysses.ui.tasks.search;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.diane.aware.cache.exceptions.CacheStateException;
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
import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.tasks.search.aware.SearchAwareTask;
import net.iubris.ulysses.ui._di.progressdialog.search.annotations.ProgressDialogForSearchPlaces;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;

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
	
//	@Inject protected Connector<VoyagerService,VoyagerSearcher> connector;
	@Inject @ProgressDialogForSearchPlaces protected ProgressDialog progressDialog;
	
	
//	@InjectView(R.id.button_list) protected Button buttonList;
//	@InjectView(R.id.button_map) protected Button buttonMap;
	
//	protected final Button buttonList;
//	protected final Button buttonMap;
	
//	@InjectView(R.anim.fade_in)
//	private 
//	final 
//	Animation fadeIn;
//	@InjectView(R.anim.fade_out)
//	private 
//	final 
//	Animation fadeOut;

//	protected final SearchUtils searchUtils;

//	private final Animation[] animations;
	
	protected UISearchAwareTask(Activity context/*, SearchUtils searchUtils*//*, Button buttonList, Button buttonMap*/) {
		super(context);
		
//		this.buttonList = buttonList;
//		this.buttonMap = buttonMap;
		
		
		
		resources = 
//			RoboGuice.getInjector(context).getInstance(Resources.class);
				context.getResources();
		search__success = resources.getString(R.string.search__success);
		
		search__exception_location_too_near = resources.getString(R.string.search__exception_location_too_near);
		search__exception_location_not_so_useful = resources.getString(R.string.search__exception_location_not_so_useful);
		
		search__exception_no_network = resources.getString(R.string.search__exception_no_network);
		search__exception_places_retrieving_status = resources.getString(R.string.search__exception_places_retrieving_status);
		search__exception_place_unbelievable_zero_result_status = resources.getString(R.string.search__exception_place_unbelievable_zero_result_status);
		search__exception_places_tyrannus_status = resources.getString(R.string.search__exception_places_tyrannus_status);
		search__exception_generic = resources.getString(R.string.search__exception_generic);
		
//		animations = SearchUtils.getAnimations(context);
//		fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
//		fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
		
//		buttonList = (Button) context.findViewById(R.id.button_list);
//		buttonMap = (Button) context.findViewById(R.id.button_map);
		
//Log.d("AbstractSearchTask:82",context+"\n"+progressDialog+"\n"+fadeIn+"\n"+connector+"\n"+buttonList);
		
//		this.searchUtils = 
////				new SearchUtils(context/*, buttonList, buttonMap,*/ /*fadeIn, fadeOut,*/ progressDialog);
//				searchUtils;
	}
	
	/**
	 * default implementation executes {@link #eventuallyShowSearchWaitingUi }
	 */
	@Override
	protected void onPreExecute() throws Exception {
		eventuallyShowSearchWaitingUi();
//		searchUtils.hideButtons();
	}
	
	
	
	
//	@Override
//	protected void onException(LocationTooNearException e) throws RuntimeException {
////		handleExceptionAndProposeSomeResult(search__exception_location_too_near,e);
//		searchUtils.handleResult(search__exception_location_too_near, searchAction);
//	}
//	@Override
//	protected void onException(LocationNotSoUsefulException e) throws RuntimeException {
////		handleExceptionAndProposeSomeResult(search__exception_location_not_so_useful,e);
//		searchUtils.handleResult(search__exception_location_not_so_useful, searchAction);
//	}
//	
//	@Override
//	protected void onException(NoNetworkException arg0) throws RuntimeException {
//		super.onException(arg0);
//	}
//	
//	// no more hermesable
//	/*protected void onException(ControllerUnavailableException e) throws RuntimeException {
//		progressDialog.cancel();
//		UIUtils.showShortToast(R.string.exception__controller_unavailable, context);
//	}*/
//	/**
//	 * calls super method, then calls {@link #eventuallyShowErrorMessage(String)} passing 'search__exception_places_retrieving_status'
//	 */
//	@Override
//	protected void onException(PlacesRetrievingException e) throws RuntimeException {
//		super.onException(e);
//		eventuallyShowErrorMessage(search__exception_places_retrieving_status);
////		UIUtils.showShortToast(search__exception_places_retrieving_status, context);
////		ExceptionUtils.showShortlyException(e,context);
//		
////		searchUtils.handleException(search__exception_places_retrieving_status, e);
//	}
//	/**
//	 * calls super method, then calls {@link #eventuallyShowErrorMessage(String)} passing 'search__exception_place_unbelievable_zero_result_status'
//	 */
//	@Override
//	protected void onException(PlacesUnbelievableZeroResultStatusException e) throws RuntimeException {
//		super.onException(e);
//		eventuallyShowErrorMessage(search__exception_place_unbelievable_zero_result_status);
////		UIUtils.showShortToast(search__exception_place_unbelievable_zero_result_status, context);
////		ExceptionUtils.showShortlyException(e,context);
//		
////		searchUtils.handleException(search__exception_place_unbelievable_zero_result_status, e);
//	}
//	/**
//	 * calls super method, then calls {@link #eventuallyShowErrorMessage(String)} passing 'search__exception_places_tyrannus_status'
//	 */
//	@Override
//	protected void onException(PlacesTyrannusStatusException e) throws RuntimeException {
//		super.onException(e);
//		eventuallyShowErrorMessage(search__exception_places_tyrannus_status);
////		UIUtils.showShortToast(search__exception_places_tyrannus_status, context);
////		ExceptionUtils.showShortlyException(e,context);
//		
////		searchUtils.handleException(search__exception_places_tyrannus_status, e);
//	}
//	
//	/**
//	 * calls super method, then calls {@link #eventuallyShowErrorMessage(String)} passing 'search__exception_generic'
//	 */
//	@Override
//	protected void onGenericException(Exception e) throws RuntimeException {
//		super.onGenericException(e);
//		eventuallyShowErrorMessage(search__exception_generic);
////		ExceptionUtils.showShortlyException(e,context);
//		
////		searchUtils.handleException(search__exception_generic, e);
//	}
	
	@Override
	protected void onSuccess(List<PlaceEnhanced> t) throws Exception {
		super.onSuccess(t);
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
}
