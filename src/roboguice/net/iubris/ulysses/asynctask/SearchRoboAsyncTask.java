/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesRoboAsyncTaskSearch.java is part of ulysses.
 * 
 * ulysses is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ulysses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with ulysses ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.asynctask;

import net.iubris.diane.searcher.locationaware.exceptions.location.LocationNotNewerStateException;
import net.iubris.diane.searcher.locationaware.exceptions.search.LocationAwareSearchException;
import net.iubris.diane.searcher.networkaware.exceptions.network.NoNetworkException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import android.content.Context;

public abstract class SearchRoboAsyncTask<SearchResult>
//<HC extends HermesClient<US,?>,US extends UlyssesSearcher> 
//<HC extends Activity & HermesClient<US>,US extends UlyssesSearcher>
extends net.iubris.diane.asynctask.SearchRoboAsyncTask<SearchResult>
implements SearcherCallable<SearchResult>
{
	
	protected SearchRoboAsyncTask(Context activity) {
		super(activity);
	}
	
	/**
	 * do not call
	 */	
	/*
	@Override
	public SearchResult call() throws 
		LocationAwareSearchException, LocationNotNewerStateException, NoNetworkException, 
		PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException,
		SearchException, Exception {
		return null;		
	}*/
	
	@Override
	protected void onException(LocationNotNewerStateException e) {
		//UIUtils.showShortToast(R.string.exception_location_not_newer_state, e, context);
		onException(e);		
	}
	@Override
	protected void onException(LocationAwareSearchException e) {
		
		//UIUtils.showShortToast(R.string.exception_location_aware_search, e, context);
	}
	@Override
	protected void onException(NoNetworkException e) {
		
		//UIUtils.showShortToast(R.string.exception_no_network, e, context);
	}	
	protected void onException(PlacesUnbelievableZeroResultStatusException e) {
		
		//UIUtils.showShortToast(R.string.exception_place_unbelievable_zero_result_status, e, context);		
	}
	protected void onException(PlacesTyrannusStatusException e) {
		
		//UIUtils.showShortToast(R.string.exception_places_tyrannus_status, e, context);
	}
}
