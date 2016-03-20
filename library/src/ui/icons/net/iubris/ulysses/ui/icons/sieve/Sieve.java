/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * Sieve.java is part of 'Ulysses'.
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
package net.iubris.ulysses.ui.icons.sieve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.ui.icons.filter.Filter;
import roboguice.util.Ln;
import android.graphics.Bitmap;

//@Singleton
public class Sieve {

	private final List<Filter> filters = new ArrayList<Filter>();
	private final Bitmap defaultBitmap;
	
	public Sieve(/*@DefaultBitmap*/ Bitmap defaultBitmap) {
		this.defaultBitmap = defaultBitmap;
	}
	
	/**
	 *  filters order is important: first result is first match !
	 */
	public Sieve(Bitmap defaultBitmap, Filter... filters) {
		this(defaultBitmap);
		this.filters.addAll( Arrays.asList(filters) );
	}
	
	/**
	 *  order is important: first result is first match !
	 */
	public Sieve addFilter(Filter filter) {
		filters.add(filter);
		return this;
	}
	
	/**
	 *  order is important: first result is first match !
	 */
	public Sieve addFilters(List<Filter> filters) {
		filters.addAll(filters);
		return this;
	}

	public Bitmap find(Place place) {
		for (Filter filter: filters) {
			if (filter.matchOn(place))
				return filter.getBitmap();
		}
		Ln.d("no specific bitmap by type/name found for place '"+place.getPlaceName()+"', using defaultBitmap");
		return defaultBitmap;
	}
}
