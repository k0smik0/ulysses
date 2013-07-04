/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UIyssesSearchAsyncTask.java is part of 'Ulysses'.
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
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.asynctask;

import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import android.content.Context;

public abstract class UIyssesSearchAsyncTask extends UlyssesAsyncTask {

	/*
	@InjectResource(name = "net.iubris.ulysses_ui:string/exception_place_unbelievable_zero_result_status")
	protected String zeroResult;
	
	@InjectResource(name = "net.iubris.ulysses_ui:string/exception_places_tyrannus_status")
	protected String tyrannusResult;

	@InjectResource(name = "exception_places_retrieving_status")
	protected String retrievingException;
	*/

	protected UIyssesSearchAsyncTask(Context context) {
		super(context);
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

	protected void onException(PlacesRetrievingException e) throws RuntimeException {
//		UIUtils.showShortToast(retrievingException, context);
	}

	protected void onException(PlacesUnbelievableZeroResultStatusException e) throws RuntimeException {
//		UIUtils.showShortToast(zeroResult, context);
	}

	protected void onException(PlacesTyrannusStatusException e) throws RuntimeException {
//		UIUtils.showShortToast(tyrannusResult, context);
	}
}
