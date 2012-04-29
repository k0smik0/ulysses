package net.iubris.ulysses.controller.delegates.cacheaware;

import java.util.ArrayList;
import java.util.List;

import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.diane.searcher.locationaware.exceptions.location.LocationNullException;
import net.iubris.ulysses.model.PlaceHere;
import android.location.Location;

public class UlyssesCacheSearcherDelegate implements IUlyssesCacheSearcherDelegate {

	private List<PlaceHere> result = new ArrayList<PlaceHere>();
	
	@Override
	public Void search(Location location) throws LocationNullException {
		return null;
	}

	@Override
	public List<PlaceHere> getSearchResult() {
		return result;
	}

	@Override
	public Void search() throws SearchException {
		return null;
	}

}
