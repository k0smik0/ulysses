/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * SearchAwareTask.java is part of 'Ratafia'.
 * 
 * 'Ratafia' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ratafia' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with 'Ratafia'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.search.tasks;


import java.util.List;

import javax.inject.Inject;

import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.exceptions.AwareSearchException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.ulysses._inject.progressdialog.providers.search.SearchType;
import net.iubris.ulysses.engine.model.PlaceHere;
import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import android.app.Activity;

public class SearchAwareTask extends AbstractUISearchTask {
	
//	@Inject LocationProvider locationProviderTemp;

	// no more hermesable
//	@Inject Connector<VoyagerService, VoyagerSearcher> connector;
	
//	private final Button buttonList;
//	private final Button buttonMap;
	
//	@Inject @ProgressDialogForSearchPlaces private ProgressDialog progressDialog;
//	private final Animation fadeIn;
//	private final Animation fadeOut;

//	private Resources resources;

//	private final String search__success;
	private final String search__exception_location_too_near;
	private final String search__exception_location_not_so_useful;
//	private final String search__exception_no_network;
//	private final String search__exception_places_retrieving_status;
//	private final String search__exception_place_unbelievable_zero_result_status;
//	private final String search__exception_places_tyrannus_status;
//	private final String search__exception_generic;
	
//	@InjectView(R.id.button_list) private Button buttonList;
//	@InjectView(R.id.button_map) private Button buttonMap;
	

//	@Inject 
//	private 
////	final 
//	SearchUtils searchUtils;

	private final SearchType searchAction = SearchType.AWARE;
	@Inject private UlyssesSearcher searcher;
	
//	@Inject
	public SearchAwareTask(Activity context/*, Button buttonList, Button buttonMap*/) {
//		super(context);
		super(context/*, buttonList, buttonMap*/);
//		this.buttonList = buttonList;
//		this.buttonMap = buttonMap;
		
//		Resources resources = RoboGuice.getInjector(context).getInstance(Resources.class);
//		search__success = resources.getString(R.string.search__success);
//		search__exception_location_too_near = resources.getString(R.string.search__exception_location_too_near);
//		search__exception_location_not_so_useful = resources.getString(R.string.search__exception_location_not_so_useful);
//		search__exception_no_network = resources.getString(R.string.search__exception_no_network);
//		search__exception_places_retrieving_status = resources.getString(R.string.search__exception_places_retrieving_status);
//		search__exception_place_unbelievable_zero_result_status = resources.getString(R.string.search__exception_place_unbelievable_zero_result_status);
//		search__exception_places_tyrannus_status = resources.getString(R.string.search__exception_places_tyrannus_status);
//		search__exception_generic = resources.getString(R.string.search__exception_generic);
		
		/*
		searchUtils = new SearchUtils(
//				progressDialog,
//				(RatafiaMainActivity) 
				context
//				,buttonList,buttonMap
				);
				*/
	}

	/*
	@Override
	protected void onPreExecute() throws Exception {
//		Toast.makeText(context, "...searching...", Toast.LENGTH_LONG).show();
		searchUtils.hideButtons();
		progressDialog.show();
	}*/
	
	
	@Override
	public List<PlaceHere> call() throws 
		LocationTooNearException, LocationNotSoUsefulException,
		NoNetworkException, PlacesRetrievingException,
		PlacesUnbelievableZeroResultStatusException,
		PlacesTyrannusStatusException, NetworkAwareSearchException,
		AwareSearchException
		/*,ControllerUnavailableException*/ {
//Log.d("SearchAwareTask:55","start search");
		
//			VoyagerAwareSearcher searcher = connector.getController().getAwareSearcher();
			// RESTORE
			searcher.search();
			List<PlaceHere> result = searcher.getResult();
//Log.d("SearchAwareTask:59","end search");
//return buildDummyResult();
			return result;
	}
	@Override
	protected void onSuccess(List<PlaceHere> result) throws RuntimeException {
//		Log.d(getClass().getSimpleName()+"134","success!");
		/*progressDialog.cancel();
		String success = context.getResources().getString(R.string.search__success);
		*/Toast.makeText(context, result.size()+" "+success, Toast.LENGTH_SHORT).show();
		((RatafiaMainActivity)context).setAction(SearchAction.AWARE);
		showButtons();
		
		searchUtils.handleResult(result.size()+" "+search__success, SearchType.AWARE);
	}
	
//	private void showButtons() {
//		buttonList.startAnimation(fadeOut);
//		buttonList.startAnimation(fadeIn);
//		buttonList.setClickable(true);
//		buttonList.setVisibility(View.VISIBLE);
//		
//		buttonMap.startAnimation( fadeOut );
//		buttonMap.startAnimation( fadeIn );
//		buttonMap.setClickable(true);
//		buttonMap.setVisibility(View.VISIBLE);
//	}
	
	/*
	protected void onException(ControllerUnavailableException e) throws RuntimeException {
		progressDialog.cancel();
		UIUtils.showShortToast(R.string.exception__controller_unavailable, context);
	}*/
	@Override
	protected void onException(NoNetworkException e) throws RuntimeException {
//		handleExceptionAndProposeSomeResult(search__exception_no_network,e);
		searchUtils.handleResult(search__exception_no_network, searchAction);
	}

	@Override
	protected void onException(LocationTooNearException e) throws RuntimeException {
//		handleExceptionAndProposeSomeResult(search__exception_location_too_near,e);
		searchUtils.handleResult(search__exception_location_too_near, searchAction);
	}
	@Override
	protected void onException(LocationNotSoUsefulException e) throws RuntimeException {
//		handleExceptionAndProposeSomeResult(search__exception_location_not_so_useful,e);
		searchUtils.handleResult(search__exception_location_not_so_useful, searchAction);
	}
	/*
	@Override
	protected void onException(PlacesRetrievingException e) throws RuntimeException {
//		progressDialog.cancel();
//		UIUtils.showShortToast(search__exception_places_retrieving_status, context);
//		ExceptionUtils.showShortlyException(e,context);
		searchUtils.handleException(search__exception_places_retrieving_status, e);
	}
	@Override
	protected void onException(PlacesUnbelievableZeroResultStatusException e) throws RuntimeException {
//		progressDialog.cancel();
//		UIUtils.showShortToast(search__exception_place_unbelievable_zero_result_status, context);
//		ExceptionUtils.showShortlyException(e,context);
		searchUtils.handleException(search__exception_place_unbelievable_zero_result_status, e);
	}
	@Override
	protected void onException(PlacesTyrannusStatusException e) throws RuntimeException {
//		progressDialog.cancel();
//		UIUtils.showShortToast(search__exception_places_tyrannus_status, context);
//		ExceptionUtils.showShortlyException(e,context);
		searchUtils.handleException(search__exception_places_tyrannus_status, e);
	}
	@Override
	protected void onGenericException(Exception e) throws RuntimeException {
//		progressDialog.cancel();
//		ExceptionUtils.showShortlyException(e,context);
		searchUtils.handleException(search__exception_generic, e);
	}*/
	
	/*
	private void handleExceptionAndProposeSomeResult(String message
//	, Exception e
	) {
		progressDialog.cancel();
//		ExceptionUtils.showShortlyException(e,context);
		UIUtils.showShortToast(message, context);
		((RatafiaMainActivity)context).setAction(SearchAction.AWARE);
		showButtons();
	}*/
	
//	private Animation buildFadeInAnimation() {
////		final Animation animation = new AlphaAnimation(0.1f, 1); 
////		animation.setDuration(1); // duration - a second
//		final Animation animation = new AlphaAnimation(1, 0.1f); // Change alpha from fully visible to invisible		
//		animation.setDuration(500); // duration - half a second
//		animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
//		animation.setRepeatCount(Animation.RESTART); // 
//		animation.setRepeatMode(Animation.REVERSE);
//		return animation;
//	}
//	private Animation buildFadeOutAnimation() {
//		final Animation animation = new AlphaAnimation(0.1f, 1); // Change alpha from fully visible to invisible		
//		animation.setDuration(500); // duration - half a second
//		animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
//		animation.setRepeatCount(Animation.RESTART); // 
//		animation.setRepeatMode(Animation.REVERSE);		
////		final Animation animation = new AlphaAnimation(1, 0.1f); // Change alpha from fully visible to invisible
////		animation.setDuration(1); // duration - a second
//		return animation;
//	}
	
	/*
	private List<PlaceHere> buildDummyResult() {
		List<PlaceHere> phs = new ArrayList<PlaceHere>();
		
		android.location.Location location = locationProviderTemp.getLocation();
		Log.d("SAT:162",""+location);
		
		HashSet<PlaceType> dummyTypes = new HashSet<PlaceType>();
		dummyTypes.add(PlaceType.liquor_store);
		
		Place p0 = new Place();
		Place p1 = new Place();
		
		Location l0 = new Location();
		Location l1 = new Location();
		
		Geometry g0 = new Geometry();
		Geometry g1 = new Geometry();
		
		Class<? extends Place> placeClass = p0.getClass();
		Class<? extends Location> locationClass = l0.getClass();
		Class<? extends Geometry> geometryClass = g0.getClass();
		
		try {
			// p0
			Field n0 = placeClass.getDeclaredField("name");
			n0.setAccessible(true);
			n0.set(p0, "Wolf");
			Field t0 = placeClass.getDeclaredField("types");
			t0.setAccessible(true);
			t0.set(p0, dummyTypes);
			Field lat0 = locationClass.getDeclaredField("latitude");
			lat0.setAccessible(true);
			lat0.setDouble(l0, 44.493031);
			Field lng0 = locationClass.getDeclaredField("longitude");
			lng0.setAccessible(true);
			lng0.setDouble(l0, 11.366598);
			
			Field loc0 = geometryClass.getDeclaredField("location");
			loc0.setAccessible(true);
			loc0.set(g0,l0);
			Field ge0 = placeClass.getDeclaredField("geometry");
			ge0.setAccessible(true);
			ge0.set(p0, g0);
			
			//p1
			Field n1 = placeClass.getDeclaredField("name");
			n1.setAccessible(true);
			n1.set(p1, "051");
			Field t1 = placeClass.getDeclaredField("types");
			t1.setAccessible(true);
			t1.set(p1, dummyTypes);
			Field lat1 = locationClass.getDeclaredField("latitude");
			lat1.setAccessible(true);
			lat1.setDouble(l1, 44.492893);
			Field lng1 = locationClass.getDeclaredField("longitude");
			lng1.setAccessible(true);
			lng1.setDouble(l1, 11.342394);
			
			Field loc1 = geometryClass.getDeclaredField("location");
			loc1.setAccessible(true);
			loc1.set(g1,l1);
			Field ge1 = placeClass.getDeclaredField("geometry");
			ge1.setAccessible(true);
			ge1.set(p1, g1);

			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		PlaceHere ph0 = new PlaceHere(p0, location);
		PlaceHere ph1 = new PlaceHere(p1, location);
		
		phs.add(ph0);
		phs.add(ph1);
		
		return phs;
	}*/

}
