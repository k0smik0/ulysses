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

import net.iubris.diane.searcher.locationaware.exceptions.search.LocationNullException;
import net.iubris.socrates.engine.details.PlaceDetailsRetriever;
import net.iubris.socrates.engine.details.exception.PlaceDetailsRetrieverException;
import net.iubris.socrates.engine.searches.PlacesSearcher;
import net.iubris.socrates.engine.searches.exception.PlacesSearcherException;
import net.iubris.socrates.model.data.place.details.PlaceDetails;
import net.iubris.socrates.model.data.place.details.PlaceDetailsResponse;
import net.iubris.socrates.model.data.place.search.Place;
import net.iubris.socrates.model.data.place.search.PlacesSearchResponse;
import net.iubris.socrates.model.http.exceptions.PlacesInvalidRequestException;
import net.iubris.socrates.model.http.exceptions.PlacesOverQuotaException;
import net.iubris.socrates.model.http.exceptions.PlacesRequestDeniedException;
import net.iubris.socrates.model.http.exceptions.PlacesZeroResultException;
import net.iubris.ulysses.model.PlaceHere;
import android.location.Location;

public class SocratesDelegate  {
	
	private final PlacesSearcher placesSearcher;
	private final PlaceDetailsRetriever placeDetailsRetriever;
		
	public SocratesDelegate(PlacesSearcher placesSearcher, PlaceDetailsRetriever placeDetailsRetriever) {
		this.placesSearcher = placesSearcher;
		this.placeDetailsRetriever = placeDetailsRetriever;
	}

	public List<PlaceHere> searchPlacesWithDetailsHere(Location here) throws LocationNullException, PlacesSearcherException, PlaceDetailsRetrieverException, PlacesOverQuotaException, PlacesZeroResultException, PlacesRequestDeniedException, PlacesInvalidRequestException {
		if (here==null) throw new LocationNullException("no valid location");
		
		final PlacesSearchResponse placesSearchResponse = placesSearcher.searchPlaces( here );

		final List<Place> places = placesSearchResponse.getStatus().act(placesSearchResponse);
//Ln.d(places.size());		
		final List<PlaceHere> placesHere = new ArrayList<PlaceHere>( places.size() );
		for (Place place: places) {
//Ln.d(place.getName() + " "+ place.getReferenceForDetails());
//Ln.d(here);
			placesHere.add( new PlaceHere(place, getDetails( place.getReferenceForDetails() ), here) );
		}
		return placesHere;
	}	
	
	private PlaceDetails getDetails(String reference) throws PlaceDetailsRetrieverException, PlacesZeroResultException, PlacesOverQuotaException, PlacesRequestDeniedException, PlacesInvalidRequestException {
//Ln.d(reference);
		final PlaceDetailsResponse placeDetailsResponse = placeDetailsRetriever.retrieveDetails( reference );
		return placeDetailsResponse.getStatus().act(placeDetailsResponse);
	}
}
