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

import net.iubris.diane.aware.cache.exceptions.base.CacheTooOldException;
import net.iubris.diane.aware.location.state.three.ThreeStateLocationAwareLocationSupplier;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.exceptions.base.StillSearchException;
import net.iubris.diane.searcher.aware.full.base.DefaultFullAwareSearcher;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.searcher.location.aware.full.LocalizedSearcherCacheNetworkAwareStrictChecking;
import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.engine.searcher.filter.ResultFilter;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import android.util.Log;

@Singleton
public class UlyssesSearcher extends DefaultFullAwareSearcher<List<PlaceEnhanced>> {

	@Inject
	public UlyssesSearcher(ThreeStateLocationAwareLocationSupplier locationAwareSupplier,
			LocalizedSearcherCacheNetworkAwareStrictChecking<List<PlaceEnhanced>> localizedSearcher) {
		super(locationAwareSupplier, localizedSearcher);
		ArrayList<PlaceEnhanced> res = new ArrayList<PlaceEnhanced>(0); // NullObject pattern
		setResult(res);		
	}
	
	@Override
	public Void search(Void... v) throws
		StillSearchException,
		LocationTooNearException,
		LocationNotSoUsefulException, NoNetworkException,
		PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException
		/*, NetworkAwareSearchException*/ {
		try {
			return super.search();
			// exceptions below are never throwed here, so we get away from method signature
		} catch (CacheTooOldException e) {
			Log.d("UlyssesSearcher:39",e.getMessage());
		} catch (CacheAwareSearchException e) {
			Log.d("UlyssesSearcher:41",e.getMessage());
		} 
		catch (NetworkAwareSearchException e) {
			Log.d("UlyssesSearcher:42",e.getMessage());
//			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<PlaceEnhanced> getResult() {
		return filterResult( super.getResult() );
	}
	
	private List<PlaceEnhanced> filterResult(List<PlaceEnhanced> result) {
		return ResultFilter.filterPlaces(result);
	}
}
