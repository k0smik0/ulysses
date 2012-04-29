package net.iubris.ulysses.controller.delegates.networkaware.socrates;

import java.util.ArrayList;
import java.util.List;

import net.iubris.diane.searcher.locationaware.exceptions.location.LocationNullException;
import net.iubris.socrates.detailer.PlaceDetailsRetriever;
import net.iubris.socrates.detailer.exception.PlaceDetailsRetrieverException;
import net.iubris.socrates.model.data.places.details.PlaceDetails;
import net.iubris.socrates.model.data.places.details.PlaceDetailsResponse;
import net.iubris.socrates.model.data.places.search.Place;
import net.iubris.socrates.model.data.places.search.PlacesSearchResponse;
import net.iubris.socrates.model.http.exceptions.PlacesInvalidRequestException;
import net.iubris.socrates.model.http.exceptions.PlacesOverQuotaException;
import net.iubris.socrates.model.http.exceptions.PlacesRequestDeniedException;
import net.iubris.socrates.model.http.exceptions.PlacesZeroResultException;
import net.iubris.socrates.searcher.PlacesSearcher;
import net.iubris.socrates.searcher.exception.PlacesSearcherException;
import net.iubris.ulysses.model.PlaceHere;
import android.location.Location;
import com.google.inject.Inject;

public class SocratesDelegate  {
	
	private final PlacesSearcher placesSearcher;
	private final PlaceDetailsRetriever placeDetailsRetriever;
		
	@Inject
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
