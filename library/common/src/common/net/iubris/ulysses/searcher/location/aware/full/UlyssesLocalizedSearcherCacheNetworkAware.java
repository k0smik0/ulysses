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
package net.iubris.ulysses.searcher.location.aware.full;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.iubris.diane.aware.cache.exceptions.base.CacheTooOldException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.searcher.location.aware.cache.LocalizedSearcherCacheAwareStrictChecking;
import net.iubris.diane.searcher.location.aware.full.base.DefaultLocalizedSearcherCacheNetworkAwareStrictChecking;
import net.iubris.diane.searcher.location.aware.network.LocalizedSearcherNetworkAwareStrictChecking;
import net.iubris.ulysses.model.PlaceHere;
import android.location.Location;

public class UlyssesLocalizedSearcherCacheNetworkAware extends DefaultLocalizedSearcherCacheNetworkAwareStrictChecking<List<PlaceHere>> {

	@Inject
	public UlyssesLocalizedSearcherCacheNetworkAware(
			LocalizedSearcherCacheAwareStrictChecking<List<PlaceHere>> arg0,
			LocalizedSearcherNetworkAwareStrictChecking<List<PlaceHere>> arg1) {
		super(arg0, arg1);
		result = new ArrayList<PlaceHere>(0); // Null Object Pattern
	}
	
	@Override
	public Void search(Location... locations) throws NoNetworkException,
			NetworkAwareSearchException {
		try {
//			Log.d("UlyssesLocalizedSearcherCacheNetworkAware:31","location: "+locations[0]);
			return super.search(locations);
		} catch (CacheTooOldException e) {
		} catch (CacheAwareSearchException e) {}
		return null;
	}

}
