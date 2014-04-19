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
package net.iubris.ulysses.list.adapter.asynctask;

import java.util.List;

import net.iubris.ulysses.asynctask.UlyssesSearchAsyncTask;
import net.iubris.ulysses.list.adapter.AdapterUtils;
import net.iubris.ulysses.list.adapter.PlacesHereListAdapter;
import net.iubris.ulysses.model.PlaceHere;
import android.app.Activity;

public abstract class AdapterPopulaterAsyncTask extends UlyssesSearchAsyncTask {

//	@InjectView(tag="loading_spinner") ProgressBar loadingSpinner;
	protected final PlacesHereListAdapter adapter;
	
	protected AdapterPopulaterAsyncTask(Activity context, PlacesHereListAdapter adapter) {
		super(context);
		this.adapter = adapter;
	}
	
	@Override
	protected void onPreExecute() throws Exception {
		super.onPreExecute();
		((Activity) context).setProgressBarIndeterminateVisibility(true);		
	}
	
	@Override
	protected void onSuccess(List<PlaceHere> placesHere) {
//Log.d("AdapterPopulaterAsyncTask:42",""+placesHere.size());
//		adapter.
//		Log.d("AdapterPopulaterAsyncTask:46","loadingSpinner: "+loadingSpinner);
//		loadingSpinner.setVisibility(View.GONE);
		AdapterUtils.clearAndPopulateAdapter(adapter, placesHere/*, new PlaceComparatorByAscendingDistance()*/);
//		((Activity) context).setProgressBarIndeterminateVisibility(false);
	}	
}
