/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * PlacesRetrievingException.java is part of 'Ulysses'.
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


public class PlacesRetrievingException extends NetworkAwareSearchException {

	public PlacesRetrievingException(String string) {
		super(string);
	}
	public PlacesRetrievingException(Throwable cause) {
		super(cause);
	}
	public PlacesRetrievingException(Throwable arg0, String arg1) {
		super(arg0, arg1);
	}
	private static final long serialVersionUID = 4930413813602225645L;
}
