/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesSampleSearchAndAdaptePopulaterAsyncTask.java is part of 'Ulysses'.
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ulysses' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses_sample.activity.list.task;

import java.util.List;

import javax.inject.Inject;

import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.ulysses.list.adapter.PlacesHereListAdapter;
import net.iubris.ulysses.list.adapter.asynctask.AdapterPopulaterAsyncTask;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses_sample.activity.main.task.Utils;
import android.app.Activity;

public class UlyssesSampleSearchAndAdaptePopulaterAsyncTask extends AdapterPopulaterAsyncTask {
	
	@Inject private UlyssesSearcher ulyssesSearcher;
	
	public UlyssesSampleSearchAndAdaptePopulaterAsyncTask(Activity context, PlacesHereListAdapter adapter) {
		super(context, adapter);
	}
	
	@Override
	public List<PlaceHere> call() throws LocationTooNearException,
			LocationNotSoUsefulException, NoNetworkException,
			PlacesRetrievingException,
			PlacesUnbelievableZeroResultStatusException,
			PlacesTyrannusStatusException, Exception {
		ulyssesSearcher.search();
		return ulyssesSearcher.getResult();
	}
	
	@Override
	protected void onException(LocationTooNearException e) throws RuntimeException {
		Utils.showException(e,context);
	}
	@Override
	protected void onException(LocationNotSoUsefulException e) throws RuntimeException {
		Utils.showException(e,context);
	}
	@Override
	protected void onGenericException(Exception e) throws RuntimeException {
		Utils.showException(e,context);
	}
	
}
