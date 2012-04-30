/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * IUlyssesNetworkSearcherDelegate.java is part of ulysses.
 * 
 * ulysses is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ulysses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with ulysses ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.controller.delegates.networkaware;

import java.util.List;

import android.location.Location;

import net.iubris.diane.searcher.locationaware.exceptions.location.LocationNullException;
import net.iubris.diane.searcher.networkaware.NetworkAwareByLocationSearcher;
import net.iubris.diane.searcher.networkaware.exceptions.network.NoNetworkException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.model.PlaceHere;

public interface IUlyssesNetworkSearcherDelegate extends NetworkAwareByLocationSearcher<Void,  List<PlaceHere>, Boolean> {
	@Override
	public Void search(Location location) throws LocationNullException, NoNetworkException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException;
}
