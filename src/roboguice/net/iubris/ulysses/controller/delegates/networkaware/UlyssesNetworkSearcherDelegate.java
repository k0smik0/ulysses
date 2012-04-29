package net.iubris.ulysses.controller.delegates.networkaware;

import java.util.List;

import com.google.inject.Inject;

import android.location.Location;
import android.net.ConnectivityManager;
import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.diane.searcher.locationaware.exceptions.location.LocationNullException;
import net.iubris.diane.searcher.networkaware.base.AbstractNetworkAwareSearcher;
import net.iubris.diane.searcher.networkaware.exceptions.network.NoNetworkException;
import net.iubris.diane.searcher.networkaware.exceptions.search.NetworkAwareSearchException;
import net.iubris.socrates.detailer.exception.PlaceDetailsRetrieverException;
import net.iubris.socrates.model.http.exceptions.PlacesInvalidRequestException;
import net.iubris.socrates.model.http.exceptions.PlacesOverQuotaException;
import net.iubris.socrates.model.http.exceptions.PlacesRequestDeniedException;
import net.iubris.socrates.model.http.exceptions.PlacesZeroResultException;
import net.iubris.socrates.searcher.exception.PlacesSearcherException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.SocratesDelegate;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.network.PlacesNoNetworkException;
import net.iubris.ulysses.model.PlaceHere;

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
