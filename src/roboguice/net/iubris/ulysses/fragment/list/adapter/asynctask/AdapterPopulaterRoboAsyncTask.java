/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * AdapterPopulaterRoboAsyncTask.java is part of 'Ulysses'
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
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
 * along with 'Ulysses' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.fragment.list.adapter.asynctask;

import java.util.List;
import java.util.concurrent.Executor;

import net.iubris.ulysses.list.adapter.AdapterUtils;
import net.iubris.ulysses.list.adapter.PlacesHereListAdapter;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.model.comparators.PlaceComparatorByAscendingDistance;
import roboguice.util.RoboAsyncTask;
import android.content.Context;
import android.os.Handler;

public abstract class AdapterPopulaterRoboAsyncTask
//	<HC extends Activity & HermesClient<US>, US extends UlyssesSearcher> 
extends RoboAsyncTask<List<PlaceHere>> {

	protected final PlacesHereListAdapter adapter;
	
	protected AdapterPopulaterRoboAsyncTask(Context context, PlacesHereListAdapter adapter) {
		super(context);
		this.adapter = adapter;
	}	

    protected AdapterPopulaterRoboAsyncTask(Context context, Handler handler,PlacesHereListAdapter adapter) {
    	super(context,handler);
    	this.adapter = adapter;
    }

    protected AdapterPopulaterRoboAsyncTask(Context context, Handler handler, Executor executor,PlacesHereListAdapter adapter) {
        super(context, handler, executor);
        this.adapter = adapter;
    }

    protected AdapterPopulaterRoboAsyncTask(Context context, Executor executor,PlacesHereListAdapter adapter) {
        super(context,executor);
        this.adapter = adapter;
    }

	@Override
	protected void onSuccess(List<PlaceHere> placesHere) {
		AdapterUtils.clearAndPopulateAdapter(adapter, placesHere, new PlaceComparatorByAscendingDistance());		
	}	
}
