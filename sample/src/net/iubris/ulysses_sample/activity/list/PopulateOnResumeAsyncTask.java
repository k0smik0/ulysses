package net.iubris.ulysses_sample.activity.list;

import java.util.List;

import javax.inject.Inject;

import net.iubris.ulysses.list.adapter.PlacesHereListAdapter;
import net.iubris.ulysses.list.adapter.asynctask.AdapterPopulaterAsyncTask;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.searcher.aware.full.UlyssesSearcher;
import android.app.Activity;

final class PopulateOnResumeAsyncTask extends AdapterPopulaterAsyncTask {
	@Inject private UlyssesSearcher ulyssesSearcher;

	PopulateOnResumeAsyncTask(Activity context, PlacesHereListAdapter adapter) {
		super(context, adapter);
	}

	@Override
	public List<PlaceHere> call() throws Exception {
		return ulyssesSearcher.getResult();
	}
}