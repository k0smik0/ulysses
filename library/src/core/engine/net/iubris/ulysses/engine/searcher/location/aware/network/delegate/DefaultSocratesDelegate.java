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

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.socrates.config.ConfigMandatory;
import net.iubris.socrates.engines.details.DetailsRetriever;
import net.iubris.socrates.engines.details.exception.DetailsRetrieverException;
import net.iubris.socrates.engines.search.Searcher;
import net.iubris.socrates.engines.search.exception.PlacesSearcherException;
import net.iubris.socrates.model.http.response.common.Photo;
import net.iubris.socrates.model.http.response.common.Status;
import net.iubris.socrates.model.http.response.data.details.Details;
import net.iubris.socrates.model.http.response.data.details.review.Review;
import net.iubris.socrates.model.http.response.data.search.GooglePlace;
import net.iubris.socrates.model.http.response.data.search.GooglePlaceType;
import net.iubris.socrates.model.http.response.details.DetailsResponse;
import net.iubris.socrates.model.http.response.exceptions.InvalidRequestException;
import net.iubris.socrates.model.http.response.exceptions.NotFoundException;
import net.iubris.socrates.model.http.response.exceptions.OverQuotaException;
import net.iubris.socrates.model.http.response.exceptions.RequestDeniedException;
import net.iubris.socrates.model.http.response.exceptions.UnknowErrorException;
import net.iubris.socrates.model.http.response.exceptions.ZeroResultException;
import net.iubris.socrates.model.http.response.search.SearchResponse;
import net.iubris.ulysses.model.Place;
import roboguice.util.Ln;
import android.location.Location;

@Singleton
public class DefaultSocratesDelegate implements SocratesDelegate {
	
	private static final int MAX_PHOTO_WIDTH = 256;
	private final Searcher searcher;
	private final DetailsRetriever placeDetailsRetriever;
	private final String googlePlacesBrowserKey;
		
	@Inject
	public DefaultSocratesDelegate(Searcher searcher, DetailsRetriever detailsRetriever, ConfigMandatory configMandatory) {
		this.searcher = searcher;
		this.placeDetailsRetriever = detailsRetriever;
		this.googlePlacesBrowserKey = configMandatory.getKey();
	}
	
	/*private Set<Place> searchPlacesHere(Location here) throws PlacesSearcherException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException, UnknowErrorException {
		final Set<GooglePlace> googlePlaces = getGooglePlaces(here);
		final Set<Place> placesEnhancedHere = new HashSet<Place>( googlePlaces.size() );
		for (GooglePlace googlePlace: googlePlaces) {
//			placesHere.add( new PlaceEnhanced(place, here) );
			
			Place place = new Place(googlePlace.getName(), googlePlace.getPlaceId(), 
					buildLocation(googlePlace.getGeometry().getLocation()), 
					googlePlace.getIcon(), googlePlace.getRating(), 
					buildTypesAsString(googlePlace.getTypes()), googlePlace.getVicinity(), 
					buildPhotosUrls(googlePlace.getPhotos()), 
					googlePlace.isPermanentlyClosed());
			placesEnhancedHere.add(place);
		}
		return placesEnhancedHere;		
	}*/

	@Override
	public List<Place> searchGooglePlacesWithDetailsHere(final Location locationHere) throws /*LocationNullException,*/ PlacesSearcherException, DetailsRetrieverException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException, NotFoundException, UnknowErrorException {
		final List<GooglePlace> googlePlaces = getGooglePlaces(locationHere);
//		Ln.d("googlePlaces: "+googlePlaces.size());
		final List<Place> places = new CopyOnWriteArrayList<Place>();
		
//		return detailsSequential(places, placesHere, locationHere);
		return detailsParallel(googlePlaces, places, locationHere);
	}
	/*private List<PlaceEnhanced> detailsSequential(List<Place> places, List<PlaceEnhanced> placesHere, Location locationHere) throws DetailsRetrieverException, ZeroResultException, OverQuotaException, RequestDeniedException, InvalidRequestException, NotFoundException, UnknowErrorException {
		Log.d("SocratesDelegate","using sequential");
		for (final Place place: places) {
			Details details = getDetails( place.getPlaceId() );
			placesHere.add( new PlaceEnhanced(place, locationHere, details) );
		}
		return placesHere;
	}*/
	private List<Place> detailsParallel(List<GooglePlace> googlePlaces, final List<Place> places, final Location locationHere) throws DetailsRetrieverException, ZeroResultException, OverQuotaException, RequestDeniedException, InvalidRequestException, NotFoundException, UnknowErrorException {
//		Ln.d("using parallel");
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(16);
		List<Callable<Void>> drs = new ArrayList<Callable<Void>>();
//		final AtomicInteger counter = new AtomicInteger();
//		final CountDownLatch latch = new CountDownLatch(googlePlaces.size());
		for (final GooglePlace googlePlace: googlePlaces) {
			
			Callable<Void> detailsCallable = new Callable<Void>() {
				@Override
				public Void call() {
//					Ln.d("details for: "+googlePlace.getName()+" ("+counter.incrementAndGet()+") ...");
//					int c = counter.get();
					try {
					Details details = getDetails( googlePlace.getPlaceId() );
					
					List<Review> googleReviews = details.getReviews();
					
					net.iubris.ulysses.model.Location location = buildLocation(googlePlace.getGeometry().getLocation());
					String icon = uriToString(googlePlace.getIcon());
					Set<String> typesAsString = googleTypesAsString(googlePlace.getTypes());
					List<String> photosUrls = buildPhotosUrls(
//							googlePlace.getPhotos()
							details.getPhotos()
							);
//					List<String> photosComments = details.getPhotos().g
//					Ln.d("added "+photosUrls.size()+" photos for "+googlePlace.getName());
					
					Place place = new Place(googlePlace.getName(), googlePlace.getPlaceId(), 
							location,
							icon, googlePlace.getRating(), 
							typesAsString, googlePlace.getVicinity(), 
							photosUrls,
							googlePlace.isPermanentlyClosed(),
							
							details.getFormattedAddress(),
							details.getInternationalPhoneNumber(),
							uriToString(details.getUri()),
							uriToString(details.getWebsite()) );
					
					if (googleReviews!=null && googleReviews.size()>0) {
//						place.setReviewsCount(reviews.size());
						place.setReviews(new ArrayList<net.iubris.ulysses.model.Place.Review>());
						
						for (Review googleReview : googleReviews) {
							net.iubris.ulysses.model.Place.Review review = 
								new net.iubris.ulysses.model.Place.Review(googleReview.getAuthorName(),
										googleReview.getAuthorUrl(),
										googleReview.getTime(),
										googleReview.getText());
							place.addReview(review);
						}
						
						
					}
//					placesHere.add( new PlaceEnhanced(place, locationHere, details) );
//					Ln.d("details for: "+place.getName()+"("+c+") ok.");
//					Ln.d("places contains "+place.getName()+" ? "+places.contains(place));
//					boolean added = 
							places.add( place );
//					Ln.d("details for: "+place.getPlaceName()+" added: " +added);
					
//					latch.countDown();
					} catch( MalformedURLException e) {
						Ln.d("MalformedURLException: "+e.getMessage());
					} catch (DetailsRetrieverException | ZeroResultException | OverQuotaException | RequestDeniedException | InvalidRequestException | NotFoundException | UnknowErrorException e) {
						Ln.d("A Socrates exception: "+e.getMessage());
					} catch (NullPointerException e) {
						Ln.d("NullPointerException: "+e.getMessage());
//						e.printStackTrace();
					}  
					return null;
				}
			};
//			fixedThreadPool.submit(detailsCallable);
			drs.add(detailsCallable);
		}
		try {
			fixedThreadPool.invokeAll(drs);
//			fixedThreadPool.shutdown();
//			latch.await(10, TimeUnit.SECONDS);
//			while (!fixedThreadPool.isTerminated()) {
//				Thread.sleep(100);
//				Ln.d("waiting for fixedThreadPool finish");
//			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		Ln.d("places: "+places.size());
		return places;
	}
	/*interface DetailsRunnable {
		public void run() throws Exception;
	}*/
	
	@Override
	public List<GooglePlace> getGooglePlaces(Location location) throws PlacesSearcherException, OverQuotaException, ZeroResultException, RequestDeniedException, InvalidRequestException, UnknowErrorException {
//		if (location==null) throw new LocationNullException("null location?!");
//		Ln.d( searcher.getRequestUrl().toString() );
		final SearchResponse searchResponse = searcher.search( location.getLatitude(), location.getLongitude() );
		final List<GooglePlace> googlePlaces = searchResponse.getStatus().handleStatusAndGetData(searchResponse);
		
//		Ln.d("google places: "+googlePlaces.size());
		
//		return new Arr<GooglePlace>( googlePlaces );
		return googlePlaces;
	}
	
	@Override
	public Details getDetails(String placeId) throws DetailsRetrieverException, ZeroResultException, OverQuotaException, RequestDeniedException, InvalidRequestException, NotFoundException, UnknowErrorException {
		final DetailsResponse placeDetailsResponse = placeDetailsRetriever.retrieve( placeId );
		Status status = placeDetailsResponse.getStatus();
//		Ln.d(placeId+" "+status);
		return status.handleStatusAndGetData( placeDetailsResponse );		
	}
	
	private net.iubris.ulysses.model.Location buildLocation(net.iubris.socrates.model.http.response.data.geocoding.Location location) {
		return new net.iubris.ulysses.model.Location(location.getLatitude(), location.getLongitude());
	}
	
	private List<String> buildPhotosUrls(List<Photo> photos) {
		List<String> photosUrls = new ArrayList<String>();
		if (photos!=null) {
			for (Photo photo : photos) {
				String photoReference = photo.getPhotoReference();
				String url = buildPhotoUrl(photoReference);
//				Ln.d("DetailsFragmentGalleryStreetView - "+url);
				photosUrls.add( url );					
			}
		}
		return photosUrls;
	}
	private String buildPhotoUrl(String photoReference) {
		String url = "https://maps.googleapis.com/maps/api/place/photo?"
			+"photoreference="+photoReference
			+"&maxwidth="+MAX_PHOTO_WIDTH
			+"&key="+googlePlacesBrowserKey;
		return url;
	}
	
	private Set<String> googleTypesAsString(Set<GooglePlaceType> types) {
		Set<String> typesAsString = new HashSet<String>();
		for (GooglePlaceType placeType : types) {
			typesAsString.add(placeType.name());
		}
		return typesAsString;
	}
	private String uriToString(URI uri) throws MalformedURLException {
		if (uri!=null) {
//			Ln.d(uri.toURL());
			return uri.toURL().toString();
		}
//		Ln.d("uri null!");
		return "";
	}
}
