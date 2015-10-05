/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesLocalizedSearcherCacheNetworkAware.java is part of 'Ulysses'.
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
package net.iubris.ulysses.engine.searcher.location.aware.full;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.util.Ln;
import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.cache.exceptions.base.CacheTooOldException;
import net.iubris.diane.aware.cache.states.three.ThreeStateCacheAware;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.searcher.location.aware.full.base.DefaultLocalizedSearcherCacheNetworkAwareStrictChecking;
import net.iubris.ulysses.engine.searcher.location.aware.cache.UlyssesLocalizedSearcherCacheAware;
import net.iubris.ulysses.engine.searcher.location.aware.network.UlyssesLocalizedSearcherNetworkAware;
import net.iubris.ulysses.model.Place;
import android.location.Location;
import android.util.Log;


/**
 * a localized searcher honoring Diane {@link DefaultLocalizedSearcherCacheNetworkAwareStrictChecking}<br/>
 * no cache awareness support in this version 
 */
@Singleton
public class DefaultUlyssesLocalizedSearcher 
extends DefaultLocalizedSearcherCacheNetworkAwareStrictChecking<List<Place>> 
implements UlyssesLocalizedSearcher {

	@Inject
	public DefaultUlyssesLocalizedSearcher(
//			LocalizedSearcherCacheAwareStrictChecking<List<PlaceEnhanced>> arg0,
			UlyssesLocalizedSearcherCacheAware arg0,
//			LocalizedSearcherNetworkAwareStrictChecking<List<PlaceEnhanced>> arg1,
			UlyssesLocalizedSearcherNetworkAware arg1,
			ThreeStateCacheAware arg2) {
		super(arg0, arg1, arg2);
		setResult( new ArrayList<Place>(0) ); // Null Object Pattern
	}
	
	@Override
	public Void search(Location... locations) throws NoNetworkException,
			NetworkAwareSearchException, CacheEmptyException {
		try {
//			Log.d("UlyssesLocalizedSearcherCacheNetworkAware:31","location: "+locations[0]);
			super.search(locations);
			
			List<Place> result = getResult();
			Ln.d("result: "+result);
			
		} catch (CacheTooOldException e) {
			Log.d("DefaultUlyssesLocalizedSearcher:74", e.getMessage() );
		} catch (CacheAwareSearchException e) {
			Log.d("DefaultUlyssesLocalizedSearcher:76", e.getMessage() );
		}
		return null;
	}
}
