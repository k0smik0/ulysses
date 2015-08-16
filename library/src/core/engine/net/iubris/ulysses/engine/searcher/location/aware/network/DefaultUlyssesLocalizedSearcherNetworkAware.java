/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesLocalizedSearcherNetworkAware.java is part of 'Ulysses'.
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
package net.iubris.ulysses.engine.searcher.location.aware.network;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.diane.aware.network.state.checker.CheckerStateNetworkAware;
import net.iubris.diane.searcher.location.aware.network.base.AbstractLocalizedSearcherNetworkAwareStrictChecking;
import net.iubris.socrates.engines.details.exception.DetailsRetrieverException;
import net.iubris.socrates.engines.search.exception.PlacesSearcherException;
import net.iubris.socrates.model.http.response.exceptions.InvalidRequestException;
import net.iubris.socrates.model.http.response.exceptions.NotFoundException;
import net.iubris.socrates.model.http.response.exceptions.OverQuotaException;
import net.iubris.socrates.model.http.response.exceptions.RequestDeniedException;
import net.iubris.socrates.model.http.response.exceptions.UnknowErrorException;
import net.iubris.socrates.model.http.response.exceptions.ZeroResultException;
import net.iubris.ulysses.engine.searcher.location.aware.network.delegate.SocratesDelegate;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.engine.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.persist.Persister;
import roboguice.util.Ln;
import android.location.Location;

@Singleton
public class DefaultUlyssesLocalizedSearcherNetworkAware 
extends AbstractLocalizedSearcherNetworkAwareStrictChecking<List<Place>> 
implements UlyssesLocalizedSearcherNetworkAware {

	private final SocratesDelegate socratesDelegate;
	private final Persister persister;
	
	private List<Place> result;
	
	@Inject
	public DefaultUlyssesLocalizedSearcherNetworkAware(CheckerStateNetworkAware checkerStateNetworkAware, 
			SocratesDelegate socratesDelegate
			,Persister persister
			) {
		super(checkerStateNetworkAware);
		this.socratesDelegate = socratesDelegate;
		this.persister = persister;
		this.result = new ArrayList<Place>(0); // NullObject Pattern
	}

	@Override
	public List<Place> getResult() {
		return result;
	}
	
	/*@Override
	public Void search(Location... locations) throws NoNetworkException, PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException, NetworkAwareSearchException {
		return super.search(locations);
//		try {
//			return super.search(locations);
//		} catch (NetworkAwareSearchException e) {
//			// bad hack - but needed because it doesn't never come here
//			Log.d("UlyssesLocalizedSearcherNetworkAware:47",e.getCause().getMessage());
////			e.printStackTrace();
//			return null;
//		}
	}*/
	
	@Override
	protected void doSearch(Location location) throws PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException {
//Log.d("UlyssesLocalizedSearcherNetworkAware:54","doSearch");
//		persister.setLocation(location);
		try {
			List<Place> r = searchByNetworkUsingDelegate(location);
			if (r!=null) {
				Ln.d("r not null: "+r.size());
				result = r;
				persister.setPlaces(r);
			}
		} catch (PlacesSearcherException e) {
			throw new PlacesRetrievingException(e);
		} 
		catch (DetailsRetrieverException e) {
			throw new PlacesRetrievingException(e);
		} catch (NotFoundException e) {
			throw new PlacesRetrievingException(e);
		} 
		catch (ZeroResultException e) {
			throw new PlacesUnbelievableZeroResultStatusException(e);
		}  catch (OverQuotaException e) {
			throw new PlacesTyrannusStatusException(e);
		} catch (RequestDeniedException e) {
			throw new PlacesTyrannusStatusException(e);
		} catch (InvalidRequestException e) {
			throw new PlacesTyrannusStatusException(e);
		} catch (UnknowErrorException e) {
			throw new PlacesTyrannusStatusException(e);
		} catch(Exception e) {
			throw new PlacesRetrievingException(e);
		}
	}
	
	protected List<Place> searchByNetworkUsingDelegate(Location location) throws PlacesSearcherException, DetailsRetrieverException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException, NotFoundException, UnknowErrorException {
//		.searchPlacesHere(location);
		return socratesDelegate.searchGooglePlacesWithDetailsHere(location);
	}
}
