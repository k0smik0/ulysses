/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesLocationSearcherDelegate.java is part of 'Ulysses'.
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
 * along with 'Ulysses' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.controller.delegates.locationaware;

import java.util.Collections;
import java.util.List;

import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.diane.searcher.locationaware.base.AbstractLocationAwareObserverSearcher;
import net.iubris.diane.searcher.locationaware.exceptions.search.LocationAwareSearchException;
import net.iubris.kusor.locator.KLocator;
import net.iubris.ulysses.model.PlaceHere;

public class UlyssesLocationSearcherDelegate extends AbstractLocationAwareObserverSearcher<Void, List<PlaceHere>> implements IUlyssesLocationSearcherDelegate {

	private List<PlaceHere> result = Collections.emptyList();

	
	public UlyssesLocationSearcherDelegate(KLocator kLocator,
			Integer distanceMinimumThreshold, 
			long timeMinimumThreshold) {
		super(kLocator, distanceMinimumThreshold, timeMinimumThreshold);
	}

	@Override
	public Void search() throws LocationAwareSearchException, SearchException {
		return null;
	}

	/**
	 * @return empty list
	 */
	@Override
	public List<PlaceHere> getSearchResult() {
		return result;
	}

}
