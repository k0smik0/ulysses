/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * SearchRoboAsyncTask.java is part of 'Ulysses'.
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ulysses' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Ulysses' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.asynctask;

import java.util.List;

import net.iubris.diane.asynctask.base.RoboSearchAsyncTask;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import android.content.Context;

public abstract class UIyssesSearchAsyncTask 
extends RoboSearchAsyncTask<UlyssesSearcher,Void,Void,List<PlaceHere>> {
	
//	private UlyssesSearcher ulyssesSearcher;
	
	protected UIyssesSearchAsyncTask(Context context/*, UlyssesSearcher ulyssesSearcher*/) {
		super(context);
//		this.ulyssesSearcher = ulyssesSearcher;
	}
	
	@Override
	public abstract List<PlaceHere> call() throws
		// from diane
		LocationTooNearException, LocationNotSoUsefulException, NoNetworkException,	
		// own
		PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException, Exception;
		/*{
			ulyssesSearcher.search(); 	return ulyssesSearcher.getResult();
	}*/
	
//	@Override
//	protected void onException(LocationNotNewerStateException e) {
		//UIUtils.showShortToast(R.string.exception_location_not_newer_state, e, context);
//		onException(e);		
//	}
	
	protected void onException(PlacesRetrievingException e) throws RuntimeException {}
	protected void onException(PlacesUnbelievableZeroResultStatusException e) throws RuntimeException {
		//UIUtils.showShortToast(R.string.exception_place_unbelievable_zero_result_status, e, context);		
	}
	protected void onException(PlacesTyrannusStatusException e) throws RuntimeException {
		//UIUtils.showShortToast(R.string.exception_places_tyrannus_status, e, context);
	}
}
