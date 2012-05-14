package net.iubris.ulysses.fragment.list.adapter.asynctask;

import java.util.List;
import java.util.concurrent.Executor;

import net.iubris.ulysses.fragment.list.adapter.AdapterUtils;
import net.iubris.ulysses.fragment.list.adapter.PlacesHereListAdapter;
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
