package net.iubris.ulysses.searcher.location.aware.network;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import net.iubris.diane.aware.network.state.checker.CheckerStateNetworkAware;
import net.iubris.diane.searcher.location.aware.network.base.AbstractLocalizedSearcherNetworkAwareStrictChecking;
import net.iubris.socrates.engines.details.exception.DetailsRetrieverException;
import net.iubris.socrates.engines.search.exception.PlacesSearcherException;
import net.iubris.socrates.model.http.response.exceptions.InvalidRequestException;
import net.iubris.socrates.model.http.response.exceptions.OverQuotaException;
import net.iubris.socrates.model.http.response.exceptions.RequestDeniedException;
import net.iubris.socrates.model.http.response.exceptions.ZeroResultException;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.searcher.location.aware.network.delegate.SocratesDelegate;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import android.location.Location;
import android.util.Log;

public class UlyssesLocalizedSearcherNetworkAware extends AbstractLocalizedSearcherNetworkAwareStrictChecking<List<PlaceHere>> 
{

	private final SocratesDelegate socratesDelegate;
	private List<PlaceHere> result;
	
	@Inject
	public UlyssesLocalizedSearcherNetworkAware(CheckerStateNetworkAware checkerStateNetworkAware,SocratesDelegate socratesDelegate) {
		super(checkerStateNetworkAware);
		this.socratesDelegate = socratesDelegate;
		result = new ArrayList<PlaceHere>(0); // NullObject Pattern
	}

	@Override
	public List<PlaceHere> getResult() {
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
Log.d("UlyssesLocalizedSearcherNetworkAware:54","doSearch");
		try {
			result = socratesDelegate.searchPlacesWithDetailsHere(location);
		} catch (PlacesSearcherException e) {
			throw new PlacesRetrievingException(e);
		} catch (DetailsRetrieverException e) {
			throw new PlacesRetrievingException(e);
		} catch (ZeroResultException e) {
			throw new PlacesUnbelievableZeroResultStatusException(e);
		}  catch (OverQuotaException e) {
			throw new PlacesTyrannusStatusException(e);
		} catch (RequestDeniedException e) {
			throw new PlacesTyrannusStatusException(e);
		} catch (InvalidRequestException e) {
			throw new PlacesTyrannusStatusException(e);
		}
	}
}
