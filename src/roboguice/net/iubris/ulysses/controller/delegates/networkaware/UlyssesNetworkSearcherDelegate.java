/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesNetworkSearcherDelegate.java is part of 'Ulysses'
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
package net.iubris.ulysses.controller.delegates.networkaware;

import java.util.List;

import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.diane.searcher.locationaware.exceptions.search.LocationNullException;
import net.iubris.diane.searcher.networkaware.base.AbstractNetworkAwareSearcher;
import net.iubris.diane.searcher.networkaware.exceptions.network.NoNetworkException;
import net.iubris.diane.searcher.networkaware.exceptions.search.NetworkAwareSearchException;
import net.iubris.socrates.engine.details.exception.PlaceDetailsRetrieverException;
import net.iubris.socrates.engine.searches.exception.PlacesSearcherException;
import net.iubris.socrates.model.http.exceptions.PlacesInvalidRequestException;
import net.iubris.socrates.model.http.exceptions.PlacesOverQuotaException;
import net.iubris.socrates.model.http.exceptions.PlacesRequestDeniedException;
import net.iubris.socrates.model.http.exceptions.PlacesZeroResultException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.SocratesDelegate;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.network.PlacesNoNetworkException;
import net.iubris.ulysses.model.PlaceHere;
import android.location.Location;
import android.net.ConnectivityManager;

import com.google.inject.Inject;

public class UlyssesNetworkSearcherDelegate extends
		AbstractNetworkAwareSearcher<Void, List<PlaceHere>> 
		implements IUlyssesNetworkSearcherDelegate{

	private final SocratesDelegate socratesDelegate;
	private List<PlaceHere> result;
	
	@Inject
	public UlyssesNetworkSearcherDelegate(ConnectivityManager connectivityManager, 
			SocratesDelegate socratesDelegate) {
		super(connectivityManager);
		this.socratesDelegate = socratesDelegate;
	}
	
	@Override
	public final Void search(Location location) throws 
			LocationNullException, NoNetworkException, 
			PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException {
		try {
			result = socratesDelegate.searchPlacesWithDetailsHere(location);
		} catch (PlacesSearcherException e) {				
			//e.printStackTrace();
			throw new PlacesNoNetworkException( e.getMessage() );
		} catch (PlaceDetailsRetrieverException e) {
			//e.printStackTrace();
			throw new PlacesNoNetworkException( e.getMessage() );
		} catch (PlacesZeroResultException e) {
			//e.printStackTrace();
			throw new PlacesUnbelievableZeroResultStatusException( e.getMessage() );
		}  catch (PlacesOverQuotaException e) {
			//e.printStackTrace();
			throw new PlacesTyrannusStatusException( e.getMessage() );
		} catch (PlacesRequestDeniedException e) {
			//e.printStackTrace();
			throw new PlacesTyrannusStatusException( e.getMessage() );
		} catch (PlacesInvalidRequestException e) {
			//e.printStackTrace();
			throw new PlacesTyrannusStatusException( e.getMessage() );
		}
		return null;
	}
			
	@Override
	public List<PlaceHere> getSearchResult() {
		return result;
	}	

	/**
	 * @return null
	 */
	@Override
	public Void search() throws NetworkAwareSearchException, SearchException {
		return null;
	}
}
