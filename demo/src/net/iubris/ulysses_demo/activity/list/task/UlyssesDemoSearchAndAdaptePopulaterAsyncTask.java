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
package net.iubris.ulysses_demo.activity.list.task;

import java.util.List;

import javax.inject.Inject;

import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.ui.list.adapter.PlacesListAdapter;
import net.iubris.ulysses.ui.tasks.populate.list.aware.PopulateListAwareTask;
import net.iubris.ulysses_demo.activity.main.task.ExceptionUtils;
import android.app.Activity;

public class UlyssesDemoSearchAndAdaptePopulaterAsyncTask extends /*AdapterPopulaterAsyncTask*/ PopulateListAwareTask {
	
	@Inject private UlyssesSearcher ulyssesSearcher;
	
	public UlyssesDemoSearchAndAdaptePopulaterAsyncTask(Activity context, PlacesListAdapter adapter) {
		super(context, adapter);
	}
	
	@Override
	public List<Place> call() /*throws LocationTooNearException,
			LocationNotSoUsefulException, NoNetworkException,
			PlacesRetrievingException,
			PlacesUnbelievableZeroResultStatusException,
			PlacesTyrannusStatusException, Exception*/ {
		try {
			ulyssesSearcher.search();
		} catch(Exception e) {}
		
		return ulyssesSearcher.getResult();
	}
	
	@Override
	protected void onGenericException(Exception e) throws RuntimeException {
		ExceptionUtils.showException(e,context);
	}
	@Override
	protected void onException(NullPointerException e) throws RuntimeException {
		ExceptionUtils.showException(e,context);
	}
	
}
