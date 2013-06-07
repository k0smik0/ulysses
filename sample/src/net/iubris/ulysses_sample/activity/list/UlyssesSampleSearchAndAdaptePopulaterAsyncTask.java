package net.iubris.ulysses_sample.activity.list;

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
import net.iubris.ulysses_sample.activity.main.Utils;
import android.app.Activity;

class UlyssesSampleSearchAndAdaptePopulaterAsyncTask extends AdapterPopulaterAsyncTask {
	
	@Inject private UlyssesSearcher ulyssesSearcher;
	
	UlyssesSampleSearchAndAdaptePopulaterAsyncTask(Activity context, PlacesHereListAdapter adapter) {
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