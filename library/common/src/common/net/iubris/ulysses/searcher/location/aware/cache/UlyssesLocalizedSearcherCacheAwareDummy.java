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
package net.iubris.ulysses.searcher.location.aware.cache;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import net.iubris.diane.aware.cache.states.three.ThreeStateCacheAware;
import net.iubris.diane.searcher.location.aware.cache.base.AbstractLocalizedSearcherCacheAwareStrictChecking;
import net.iubris.ulysses.model.PlaceHere;
import android.location.Location;

public class UlyssesLocalizedSearcherCacheAwareDummy extends AbstractLocalizedSearcherCacheAwareStrictChecking<List<PlaceHere>> {

	@Inject
	public UlyssesLocalizedSearcherCacheAwareDummy(ThreeStateCacheAware arg0) {
		super(arg0);
	}
	
	@Override
	public Void search(Location... location) /*throws CacheTooOldException,
			CacheAwareSearchException*/ {
		return null;
	}

	@Override
	public List<PlaceHere> getResult() {
		return Collections.emptyList();
	}

	@Override
	protected void doSearch(Location arg0) /*throws CacheAwareSearchException */{}

}
