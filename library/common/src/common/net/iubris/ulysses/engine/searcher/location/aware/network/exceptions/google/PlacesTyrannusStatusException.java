/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * PlacesTyrannusStatusException.java is part of 'Ulysses'.
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
package net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google;

import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;


/**
 * Map OVER_QUOTA or INVALID_REQUEST or REQUEST_DENIED from GooglePlaces API
 *  
 * @author "Massimiliano Leone - massimiliano.leone@iubris.net"
  */
public class PlacesTyrannusStatusException extends NetworkAwareSearchException {

	public PlacesTyrannusStatusException(String message) {
		super(message);
	}
	public PlacesTyrannusStatusException(Throwable cause) {
		super(cause);
	}
	public PlacesTyrannusStatusException(Throwable arg0, String arg1) {
		super(arg0, arg1);
	}

	private static final long serialVersionUID = 7742158513201454837L;
}
