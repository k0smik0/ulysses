/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * AdapterPopulaterAsyncTask.java is part of 'Ulysses'.
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
package net.iubris.ulysses.ui.tasks.populate.list._base;


import java.util.List;

import roboguice.util.Ln;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.model.comparators.PlaceComparatorByAscendingDistance;
import net.iubris.ulysses.tasks._base.AbstractUlyssesTask;
import net.iubris.ulysses.ui.tasks.populate.list.adapter.utils.AdapterUtils;
import android.app.Activity;
import android.location.Location;
import android.widget.ArrayAdapter;

public abstract class AbstractPopulateListAsyncTask extends AbstractUlyssesTask implements PopulateListTask {

//	@InjectView(tag="loading_spinner") ProgressBar loadingSpinner;
	protected final ArrayAdapter<Place> adapter;

	protected final PlaceComparatorByAscendingDistance placeComparatorByAscendingDistance = new PlaceComparatorByAscendingDistance();

	private Location location;
	
	protected AbstractPopulateListAsyncTask(Activity activity, ArrayAdapter<Place> adapter) {
		super(activity);
		this.adapter = adapter;
	}
	
	@Override
	protected void onPreExecute() throws Exception {
//		super.onPreExecute();
//		Ln.d("preExecute");
		((Activity) context).setProgressBarIndeterminateVisibility(true);
	}
	
	@Override
	protected void onSuccess(List<Place> places) {
//Ln.d("AbstractPopulateListAsyncTask:51 "+placeEnhanceds.size());
//		Log.d("AdapterPopulaterAsyncTask:46","loadingSpinner: "+loadingSpinner);
//		loadingSpinner.setVisibility(View.GONE);
//		((Activity) context).setProgressBarIndeterminateVisibility(false);
		AdapterUtils.clearAndPopulateAdapter(adapter, places, location, placeComparatorByAscendingDistance);
//Ln.d("populated");
		((Activity) context).setProgressBarIndeterminateVisibility(false);
	}
	
	public void execute(Location location) {
		this.location = location;
		Ln.d("using location: "+location);
		super.execute();
	}
	
	@Deprecated
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		super.execute();
	}
}
