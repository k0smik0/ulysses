/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * PopulateMapAsyncTask.java is part of 'Ulysses'.
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
package net.iubris.ulysses.ui.tasks.map.aware;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.ui.icons.sieve.Sieve;
import net.iubris.ulysses.ui.tasks.map._base.AbstractPopulateMapTask;
import net.iubris.ulysses.ui.tasks.map._base.exceptions.LocationNullException;
import net.iubris.ulysses.ui.tasks.map._utils.LocationUtils;
import net.iubris.ulysses.ui.toast.utils.UIUtils;
import android.app.Activity;
import android.location.Location;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds.Builder;

/**
 *	task for populate map with aware search result, including own position (in search and in map visualization) 
 *
 */
@Singleton
public class PopulateMapAwareTask extends AbstractPopulateMapTask {

	private Location myLocation;
	
	@Inject private UlyssesSearcher ulyssesSearcher;
	
	public PopulateMapAwareTask(Activity activity,
			GoogleMap map, 
//			int mapFragmentResId,
			Fragment mapFragment,
			Sieve sieve) {
		super(activity, map, mapFragment, sieve);
	}
	
	@Override
	public void execute(Location myLocation) {
//		if (myLocation==null) throw new LocationNullException();
		this.myLocation = myLocation;
		super.execute();
	}
	
	@Override
	public List<PlaceEnhanced> call() throws LocationNullException {
		if (myLocation==null) throw new LocationNullException();
		return ulyssesSearcher.getResult();
	}
	
	protected void onException(LocationNullException arg0) throws RuntimeException {
		UIUtils.showShortToast("location null", context);
		super.onException(arg0);
	}
	
	
	/**
	 * don't call this: instead call {@link #execute(Location)}
	 */
	@Deprecated
	@Override
	public final void execute() {
		super.execute();
	}
	
	@Override
	protected void buildBoundsAndZoom(Builder boundsBuilder) {
		includeMe(boundsBuilder);
		super.buildBoundsAndZoom(boundsBuilder);
	}
	
	private void includeMe(Builder boundsBuilder) {
		boundsBuilder.include(LocationUtils.locationToLatLng(myLocation));
	}
}
