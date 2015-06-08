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
package net.iubris.ulysses.ui.tasks.list._base;


import java.util.List;

import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.tasks._base.AbstractUlyssesTask;
import net.iubris.ulysses.ui.tasks.list.adapter.utils.AdapterUtils;
import android.app.Activity;
import android.widget.ArrayAdapter;

public abstract class AbstractPopulateListAsyncTask extends /*UlyssesSearchAsyncTask*/ AbstractUlyssesTask implements PopulateListTask {

//	@InjectView(tag="loading_spinner") ProgressBar loadingSpinner;
	protected final ArrayAdapter<PlaceEnhanced> adapter;
	
	protected AbstractPopulateListAsyncTask(Activity context, ArrayAdapter<PlaceEnhanced> adapter) {
		super(context);
		this.adapter = adapter;
	}
	
	@Override
	protected void onPreExecute() throws Exception {
		super.onPreExecute();
//		Ln.d("preExecute");
		((Activity) context).setProgressBarIndeterminateVisibility(true);
	}
	
	@Override
	protected void onSuccess(List<PlaceEnhanced> placeEnhanceds) {
//Ln.d("AbstractPopulateListAsyncTask:51 "+placeEnhanceds.size());
//		Log.d("AdapterPopulaterAsyncTask:46","loadingSpinner: "+loadingSpinner);
//		loadingSpinner.setVisibility(View.GONE);
//		((Activity) context).setProgressBarIndeterminateVisibility(false);
		AdapterUtils.clearAndPopulateAdapter(adapter, placeEnhanceds/*, new PlaceComparatorByAscendingDistance()*/);
//Ln.d("populated");
	}	
}
