package net.iubris.ulysses.engine.searcher.location.aware.network.delegate;

import java.util.List;

import net.iubris.socrates.engines.details.exception.DetailsRetrieverException;
import net.iubris.socrates.engines.search.exception.PlacesSearcherException;
import net.iubris.socrates.model.http.response.data.details.Details;
import net.iubris.socrates.model.http.response.data.search.GooglePlace;
import net.iubris.socrates.model.http.response.exceptions.InvalidRequestException;
import net.iubris.socrates.model.http.response.exceptions.NotFoundException;
import net.iubris.socrates.model.http.response.exceptions.OverQuotaException;
import net.iubris.socrates.model.http.response.exceptions.RequestDeniedException;
import net.iubris.socrates.model.http.response.exceptions.UnknowErrorException;
import net.iubris.socrates.model.http.response.exceptions.ZeroResultException;
import net.iubris.ulysses.model.Place;
import android.location.Location;

public interface SocratesDelegate {
	List<Place> searchGooglePlacesWithDetailsHere(final Location locationHere) throws /*LocationNullException,*/ PlacesSearcherException, DetailsRetrieverException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException, NotFoundException, UnknowErrorException;
	List<GooglePlace> getGooglePlaces(Location location) throws PlacesSearcherException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException, UnknowErrorException;
	Details getDetails(String placeId) throws DetailsRetrieverException, ZeroResultException, OverQuotaException, RequestDeniedException, InvalidRequestException, NotFoundException, UnknowErrorException;
}
