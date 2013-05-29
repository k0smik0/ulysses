/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * AdapterPopulaterRoboAsyncTask.java is part of 'Ulysses'.
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
package net.iubris.ulysses.fragment.list.adapter.asynctask;

import java.util.List;

import net.iubris.ulysses.asynctask.UIyssesSearchAsyncTask;
import net.iubris.ulysses.fragment.list.adapter.AdapterUtils;
import net.iubris.ulysses.fragment.list.adapter.PlacesHereListAdapter;
import net.iubris.ulysses.model.PlaceHere;
import android.content.Context;
import android.util.Log;

public abstract class AdapterPopulaterAsyncTask extends UIyssesSearchAsyncTask {

//	@InjectView(tag="loading_spinner") ProgressBar loadingSpinner;
	protected final PlacesHereListAdapter adapter;
	
	protected AdapterPopulaterAsyncTask(Context context, PlacesHereListAdapter adapter) {
		super(context);
		this.adapter = adapter;
	}

	@Override
	protected void onSuccess(List<PlaceHere> placesHere) {
		Log.d("AdapterPopulaterAsyncTask:42",""+placesHere.size());
//		adapter.
//		Log.d("AdapterPopulaterAsyncTask:46","loadingSpinner: "+loadingSpinner);
//		loadingSpinner.setVisibility(View.GONE);
		AdapterUtils.clearAndPopulateAdapter(adapter, placesHere/*, new PlaceComparatorByAscendingDistance()*/);		
	}	
}
