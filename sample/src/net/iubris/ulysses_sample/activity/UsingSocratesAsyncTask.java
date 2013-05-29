package net.iubris.ulysses_sample.activity;

import java.util.List;

import javax.inject.Inject;

import net.iubris.socrates.engines.search.Searcher;
import net.iubris.socrates.engines.search.exception.PlacesSearcherException;
import net.iubris.socrates.engines.search.url.exception.LocationNullException;
import net.iubris.socrates.model.http.response.data.search.Place;
import net.iubris.socrates.model.http.response.search.SearchResponse;
import roboguice.util.RoboAsyncTask;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

final class UsingSocratesAsyncTask extends RoboAsyncTask<List<Place>> {

	@Inject Searcher searcher;

	UsingSocratesAsyncTask(Context context) {
		super(context);
	}

	@Override
	public List<Place> call() throws LocationNullException,PlacesSearcherException {
		Location location = null;
		location = new Location("dummy");
		location.setLongitude(11.376861);
		location.setLatitude(44.493477);

		SearchResponse searchResponse = searcher.search(location);
		List<Place> results = searchResponse.getResults();
		
		return  results;
	}

	@Override
	protected void onSuccess(List<Place> result) throws RuntimeException {
		Toast.makeText(context, "socrates finds: "+result.size(), Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onException(Exception e) throws RuntimeException {
		Log.d("UsingSocratesAsyncTask", "onException: "+e.getMessage());
		e.printStackTrace();
	}
}