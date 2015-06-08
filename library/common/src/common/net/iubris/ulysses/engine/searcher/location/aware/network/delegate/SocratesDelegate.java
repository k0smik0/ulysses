/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * SocratesDelegate.java is part of 'Ulysses'.
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
package net.iubris.ulysses.engine.searcher.location.aware.network.delegate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.iubris.socrates.engines.details.DetailsRetriever;
import net.iubris.socrates.engines.details.exception.DetailsRetrieverException;
import net.iubris.socrates.engines.search.Searcher;
import net.iubris.socrates.engines.search.exception.PlacesSearcherException;
import net.iubris.socrates.model.http.response.data.details.Details;
import net.iubris.socrates.model.http.response.data.search.Place;
import net.iubris.socrates.model.http.response.details.DetailsResponse;
import net.iubris.socrates.model.http.response.exceptions.InvalidRequestException;
import net.iubris.socrates.model.http.response.exceptions.OverQuotaException;
import net.iubris.socrates.model.http.response.exceptions.RequestDeniedException;
import net.iubris.socrates.model.http.response.exceptions.ZeroResultException;
import net.iubris.socrates.model.http.response.search.SearchResponse;
import net.iubris.ulysses.engine.model.PlaceEnhanced;
import android.location.Location;

public class SocratesDelegate  {
	
	private final Searcher searcher;
	private final DetailsRetriever placeDetailsRetriever;
		
	@Inject
	public SocratesDelegate(Searcher searcher, DetailsRetriever detailsRetriever) {
		this.searcher = searcher;
		this.placeDetailsRetriever = detailsRetriever;
	}
	
	public List<PlaceEnhanced> searchPlacesHere(Location here) throws PlacesSearcherException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException {
		final List<Place> places = getPlaces(here);
		final List<PlaceEnhanced> placesHere = new ArrayList<PlaceEnhanced>( places.size() );
		for (Place place: places) {
			placesHere.add( new PlaceEnhanced(place, here) );
		}
		return placesHere;		
	}
	/*
	public List<PlaceHere> prova(Location here) throws LocationNullException, SearcherException, DetailsRetrieverException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException{
		List<PlaceHere> placesHere = searchPlacesHere(here);
		for (PlaceHere ph: placesHere) {
			ph.setDetails( 
				getDetails(	ph.getPlace().getReference() )
			);
		}
		return placesHere;
	}*/

	public List<PlaceEnhanced> searchPlacesWithDetailsHere(Location here) throws /*LocationNullException,*/ PlacesSearcherException, DetailsRetrieverException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException {
		final List<Place> places = getPlaces(here);		
//Ln.d(places.size());		
		final List<PlaceEnhanced> placesHere = new ArrayList<PlaceEnhanced>( places.size() );
		for (Place place: places) {
//Ln.d(place.getName() + " "+ place.getReferenceForDetails());
//Ln.d(here);
			placesHere.add( new PlaceEnhanced(place, here, getDetails( place.getReference() )) );
		}
		return placesHere;
	}
	
	private List<Place> getPlaces(Location location) throws PlacesSearcherException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException {
//		if (location==null) throw new LocationNullException("null location?!");
		final SearchResponse searchResponse = searcher.search( location );
		final List<Place> places = searchResponse.getStatus().handleStatusAndGetData(searchResponse);
		return places;
	}
	
	private Details getDetails(String reference) throws DetailsRetrieverException, ZeroResultException, OverQuotaException, RequestDeniedException, InvalidRequestException {
		final DetailsResponse placeDetailsResponse = placeDetailsRetriever.retrieve( reference );
		return placeDetailsResponse.getStatus().handleStatusAndGetData( placeDetailsResponse );		
	}
}
