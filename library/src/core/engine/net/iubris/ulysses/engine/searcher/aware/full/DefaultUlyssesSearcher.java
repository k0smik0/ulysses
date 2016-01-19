/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesSearcher.java is part of 'Ulysses'.
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
package net.iubris.ulysses.engine.searcher.aware.full;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.cache.exceptions.base.CacheTooOldException;
import net.iubris.diane.aware.location.exceptions.base.LocationFreshNullException;
import net.iubris.diane.aware.location.state.three.ThreeStateLocationAwareLocationSupplier;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.exceptions.base.StillSearchException;
import net.iubris.diane.searcher.aware.full.base.DefaultFullAwareSearcher;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.model.Place;
import roboguice.util.Ln;
import android.util.Log;

@Singleton
public class DefaultUlyssesSearcher 
extends DefaultFullAwareSearcher<List<Place>> 
implements UlyssesSearcher {

	@Inject
	public DefaultUlyssesSearcher(ThreeStateLocationAwareLocationSupplier locationAwareSupplier,
//			LocalizedSearcherCacheNetworkAwareStrictChecking<Set<PlaceEnhanced>> localizedSearcher
			UlyssesLocalizedSearcher localizedSearcher) {
		super(locationAwareSupplier, localizedSearcher);
	}
	
	@Override
	protected void initResult() {
		List<Place> res = new ArrayList<Place>(); // NullObject pattern
		setResult(res);
	}
	
	@Override
	public synchronized Void search(Void... v) throws
		StillSearchException,
		LocationFreshNullException,
		LocationTooNearException,
		LocationNotSoUsefulException, NoNetworkException,
		CacheEmptyException, CacheAwareSearchException,
//		NoNetworkAndCacheEmptyException,
		PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException
		/*, NetworkAwareSearchException*/ {
		try {
			super.search();
			
			// TODO just for debug print
			List<Place> result = getResult();
			Ln.d("search completed (using location "+getLocation()+"); result size: "+result.size());
			
			// FIXME ? exceptions below are never throwed in this release, so we get away from method signature
		} catch (CacheTooOldException e) {
			Ln.d("CacheTooOldException: "+e.getMessage());
		} /*catch (CacheAwareSearchException e) {
			Log.d("UlyssesSearcher:69",e.getMessage());
		}*/ 
		catch (NetworkAwareSearchException e) {
			Log.d("UlyssesSearcher:72",e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/*@Override
	public synchronized Set<PlaceEnhanced> getResult() {
		Set<PlaceEnhanced> result = super.getResult();
//		Log.d(this.getClass().getName(),Thread.currentThread()+" result: "+result.size());
		Set<PlaceEnhanced> filteredResult = filterResult( result );
//		Log.d(this.getClass().getName(),Thread.currentThread()+" filtered result: "+filteredResult.size());
		return filteredResult;
	}*/
	
//	private Set<PlaceEnhanced> filterResult(Set<PlaceEnhanced> result) {
//		return ResultFilter.filterPlaces(result);
//	}
}
