/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
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
 * along with 'Ulysses' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.controller.delegates.networkaware.socrates;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import net.iubris.diane.searcher.locationaware.exceptions.search.LocationNullException;
import net.iubris.socrates.engines.details.DetailsRetriever;
import net.iubris.socrates.engines.details.exception.DetailsRetrieverException;
import net.iubris.socrates.engines.search.Searcher;
import net.iubris.socrates.engines.search.exception.SearcherException;
import net.iubris.socrates.model.http.response.data.details.Details;
import net.iubris.socrates.model.http.response.data.search.Place;
import net.iubris.socrates.model.http.response.details.DetailsResponse;
import net.iubris.socrates.model.http.response.exceptions.InvalidRequestException;
import net.iubris.socrates.model.http.response.exceptions.OverQuotaException;
import net.iubris.socrates.model.http.response.exceptions.RequestDeniedException;
import net.iubris.socrates.model.http.response.exceptions.ZeroResultException;
import net.iubris.socrates.model.http.response.search.SearchResponse;
import net.iubris.ulysses.model.PlaceHere;
import android.location.Location;

public class SocratesDelegate  {
	
	private final Searcher searcher;
	private final DetailsRetriever placeDetailsRetriever;
		
	@Inject
	public SocratesDelegate(Searcher searcher, DetailsRetriever detailsRetriever) {
		this.searcher = searcher;
		this.placeDetailsRetriever = detailsRetriever;
	}
	
	public List<PlaceHere> searchPlacesHere(Location here) throws LocationNullException, SearcherException, DetailsRetrieverException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException {
		final SearchResponse searchResponse = searcher.search( here );
		final List<Place> places = searchResponse.getStatus().act(searchResponse);
		final List<PlaceHere> placesHere = new ArrayList<PlaceHere>( places.size() );
		for (Place place: places) {
			placesHere.add( new PlaceHere(place, null, here) );
		}
		return placesHere;		
	}
	
	public List<PlaceHere> prova(Location here) throws LocationNullException, SearcherException, DetailsRetrieverException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException{
		List<PlaceHere> placesHere = searchPlacesHere(here);
		for (PlaceHere ph: placesHere) {
			ph.setDetails( 
				getDetails(	ph.getPlace().getReference() )
			);
		}
		return placesHere;
	}

	public List<PlaceHere> searchPlacesWithDetailsHere(Location here) throws LocationNullException, SearcherException, DetailsRetrieverException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException {
		if (here==null) throw new LocationNullException("no valid location");
		
		final SearchResponse searchResponse = searcher.search( here );

		final List<Place> places = searchResponse.getStatus().act(searchResponse);
//Ln.d(places.size());		
		final List<PlaceHere> placesHere = new ArrayList<PlaceHere>( places.size() );
		for (Place place: places) {
//Ln.d(place.getName() + " "+ place.getReferenceForDetails());
//Ln.d(here);
			placesHere.add( new PlaceHere(place, getDetails( place.getReference() ), here) );
		}
		return placesHere;
	}
	
	private Details getDetails(String reference) throws DetailsRetrieverException, ZeroResultException, OverQuotaException, RequestDeniedException, InvalidRequestException {
		final DetailsResponse placeDetailsResponse = placeDetailsRetriever.retrieveDetails( reference );
		return placeDetailsResponse.getStatus().act( placeDetailsResponse );		
	}
}
