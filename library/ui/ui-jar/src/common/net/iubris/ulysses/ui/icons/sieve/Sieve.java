package net.iubris.ulysses.ui.icons.sieve;

import java.util.ArrayList;
import java.util.List;

import net.iubris.socrates.model.http.response.data.search.Place;
import net.iubris.ulysses.ui.icons.filter.Filter;
import android.graphics.Bitmap;

public class Sieve {

	private final List<Filter> filters = new ArrayList<Filter>();
	private final Bitmap defaultBitmap;
	
//	@Inject
	public Sieve(/*@DefaultBitmap*/ Bitmap defaultBitmap) {
		this.defaultBitmap = defaultBitmap;
//Log.d("Sieve:21","constructor");
	}
	
	public Sieve addFilter(Filter filter) {
		filters.add(filter);
		return this;
	}
	public void addFilters(List<Filter> filters) {
		filters.addAll(filters);
	}

	public Bitmap find(Place place) {
		for (Filter filter: filters) {
			if (filter.matchOn(place))
				return filter.getBitmap();
		}
		return defaultBitmap;
	}
}
