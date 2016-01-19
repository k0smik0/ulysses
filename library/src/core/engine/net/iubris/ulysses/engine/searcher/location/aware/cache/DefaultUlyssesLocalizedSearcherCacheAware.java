/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesLocalizedSearcherCacheAwareDummy.java is part of 'Ulysses'.
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
package net.iubris.ulysses.engine.searcher.location.aware.cache;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.cache.states.three.ThreeStateCacheAware;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.location.aware.cache.base.AbstractLocalizedSearcherCacheAwareStrictChecking;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.persist.Persister;
import android.location.Location;

@Singleton
public class DefaultUlyssesLocalizedSearcherCacheAware 
extends AbstractLocalizedSearcherCacheAwareStrictChecking<List<Place>> 
implements UlyssesLocalizedSearcherCacheAware {
	
	public enum CacheSearchExceptions {
		RESULTS_LESS_THAN_MINIMUM,
		RESULTS_EMPTY
	}
	public static final int MINIMUM = 20;
	

	private final Persister persister;
	private List<Place> result;

	@Inject
	public DefaultUlyssesLocalizedSearcherCacheAware(ThreeStateCacheAware arg0, Persister persister) {
		super(arg0);
		this.persister = persister;
//		Log.d("UlyssesLocalizedSearcherCacheAware", "ComplexPreferencesPersister: "+complexPreferencesPersister);
	}
	
	@Override
	public List<Place> getResult() {
		return result;
	}
	
	@Override
	protected void doSearch(Location location) throws CacheEmptyException, CacheAwareSearchException {
		List<Place> found = persister.searchPlaces(location);
		result = found;
		if (found.size() == 0) {
			throw new CacheEmptyException(CacheSearchExceptions.RESULTS_EMPTY.name());
		}
		if (found.size() < MINIMUM) {
			throw new CacheAwareSearchException(CacheSearchExceptions.RESULTS_LESS_THAN_MINIMUM.name());
		}
//			new HashList<Place>();
//		Log.d("UlyssesLocalizedSearcherCacheAware", "found some results from cache? "+result.size());
	}
}