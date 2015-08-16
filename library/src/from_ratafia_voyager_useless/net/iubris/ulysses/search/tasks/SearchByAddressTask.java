/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * SearchByAddressTask.java is part of 'Ratafia'.
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


import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.ulysses._inject.progressdialog.providers.search.SearchType;
import net.iubris.ulysses.engine.model.PlaceHere;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Button;

public class SearchByAddressTask extends AbstractUISearchTask {
	
//	@Inject Connector<RatafiaService, RatafiaController> connector;
//	@Inject @ProgressDialogForSearchPlaces private ProgressDialog progressDialog;
	
	private String address;
	
//	private final String search__success;
//	private final String search__exception_no_network;
//	private final String search__exception_places_retrieving_status;
//	private final String search__exception_place_unbelievable_zero_result_status;
//	private final String search__exception_places_tyrannus_status;
//	private final String search__exception_generic;
	
	private SearchType searchAction = SearchType.LOCALIZED;
//	@Inject
//	private 
//	final 
//	SearchUtils searchUtils;
	
	@Inject UlyssesLocalizedSearcher ulyssesLocalizedSearcher;
	
	
//	@Inject
	public SearchByAddressTask(Activity context/*, String address*/
			, Button buttonList, Button buttonMap
			) {
		super(context, buttonList, buttonMap);
//		this.address = address;
		
		/*
		searchUtils = new SearchUtils(
//				progressDialog,
				context
//				,buttonList,buttonMap
				);
				*/
		
		/*Resources resources = RoboGuice.getInjector(context).getInstance(Resources.class);
		
		search__success = resources.getString(R.string.search__success);
		search__exception_no_network = resources.getString(R.string.search__exception_no_network);
		search__exception_places_retrieving_status = resources.getString(R.string.search__exception_places_retrieving_status);
		search__exception_place_unbelievable_zero_result_status = resources.getString(R.string.search__exception_place_unbelievable_zero_result_status);
		search__exception_places_tyrannus_status = resources.getString(R.string.search__exception_places_tyrannus_status);
		search__exception_generic = resources.getString(R.string.search__exception_generic);*/	
	}
	
//	@Override
	public void execute(String address) {
		this.address = address;
		super.execute();
	}
	
	/*
	@Override
	protected void onPreExecute() throws Exception {
//		Toast.makeText(context, "...searching...", Toast.LENGTH_LONG).show();
		searchUtils.hideButtons();
		progressDialog.show();
	}*/

	/**
	 * default:<br/>
	 * 1. Location[] locations = {@link #getDummyLocations() }<br/> 
	 * 2. invoke {@link #callLocalizedSearcher(Location[]) } passing 'locations' to it;
	 */
	@Override
	public List<PlaceHere> call() throws Exception {
		Location[] dummyLocations = getDummyLocations();
		return callLocalizedSearcher(dummyLocations);
	}
	protected Location[] getDummyLocations() throws IOException {
		List<Address> addresses = new Geocoder(context).getFromLocationName(address, 5);
		Address address0 = addresses.get(0);
//Log.d("",""+address0);
		Location[] ls = new Location[1];
		ls[0] = new Location("dummy");
		ls[0].setLatitude(address0.getLatitude());
		ls[0].setLongitude(address0.getLongitude());
		return ls;
	}
	/**
	 * @param locations
	 * @return {@link PlaceHere}
	 * 
	 * default: use {@link UlyssesLocalizedSearcher } to retrieve results
	 * @throws NetworkAwareSearchException 
	 * @throws NoNetworkException 
	 */
	protected List<PlaceHere> callLocalizedSearcher(Location[] locations) throws NoNetworkException, NetworkAwareSearchException {
		ulyssesLocalizedSearcher.search(locations);
		return ulyssesLocalizedSearcher.getResult(); 
	}
	@Override
	protected void onSuccess(List<PlaceHere> result) throws Exception {
		/*progressDialog.cancel();
		super.onSuccess(result);
		String success = context.getResources().getString(R.string.search__success);
		Toast.makeText(context, result.size()+" "+success, Toast.LENGTH_SHORT).show();
		((RatafiaMainActivity)context).setAction(SearchAction.LOCALIZED);*/
		searchUtils.handleResult(result.size()+" "+search__success, searchAction);
	}
	
	
	@Override
	protected void onException(NoNetworkException e) throws RuntimeException {
//		handleExceptionAndProposeSomeResult(search__exception_no_network,e);
		searchUtils.handleResult(search__exception_no_network, searchAction );
	}
	
	/*private void handleExceptionAndProposeSomeResult(String message, Exception e) {
		progressDialog.cancel();
		ExceptionUtils.showShortlyException(e,context);
		UIUtils.showShortToast(message, context);
		((RatafiaMainActivity)context).setAction(SearchAction.AWARE);
		showButtons();
	}*/
	
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
	}
*/
}
