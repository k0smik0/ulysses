/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * SearcherCallable.java is part of 'Ulysses'
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
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
 * along with 'Ulysses' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.asynctask;

import java.util.concurrent.Callable;

import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.network.PlacesNoNetworkException;

public interface SearcherCallable<V> extends Callable<V> {
	@Override
	public V call() throws PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException, PlacesNoNetworkException, SearchException, Exception;	
}
