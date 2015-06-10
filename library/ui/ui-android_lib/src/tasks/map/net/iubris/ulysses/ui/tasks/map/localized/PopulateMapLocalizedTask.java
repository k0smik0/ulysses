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
package net.iubris.ulysses.ui.tasks.map.localized;




import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.ulysses.engine.model.PlaceEnhanced;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.ui.icons.sieve.Sieve;
import net.iubris.ulysses.ui.tasks.map._base.AbstractPopulateMapTask;
import net.iubris.ulysses.ui.tasks.map.aware.PopulateMapAwareTask;
import android.app.Activity;
import android.location.Location;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;

/**
 * 
 * task class for populate map with localized search result (= by address, not including own position)
 *
 */
@Singleton
public class PopulateMapLocalizedTask extends AbstractPopulateMapTask {

	public PopulateMapLocalizedTask(Activity activity, GoogleMap map, Fragment mapFragment, Sieve sieve) {
		super(activity, map, mapFragment, sieve);
	}

	@Inject private UlyssesLocalizedSearcher ulyssesLocalizedSearcher;
	
//	private Location myLocation;
	
	/**
	 * this method exists just for compatibility with sibling {@link PopulateMapAwareTask}<br/>
	 * instead, use {@link #execute()}
	 * @param dummyLocation {@link Location}: is really unused
	 */
	@Deprecated
	@Override
	public void execute(Location dummyLocation) {
		super.execute();
	}

	@Override
	public List<PlaceEnhanced> call() {
		return ulyssesLocalizedSearcher.getResult();
	}
}
