package net.iubris.ulysses.controller;

import java.util.ArrayList;
import java.util.List;

import net.iubris.diane.searcher.Searcher;
import net.iubris.diane.searcher.locationaware.exceptions.location.LocationNotNewerStateException;
import net.iubris.diane.searcher.locationaware.exceptions.location.LocationNullException;
import net.iubris.diane.searcher.networkaware.exceptions.network.NoNetworkException;
import net.iubris.kusor.updater.LocationUpdater;
import net.iubris.ulysses.controller.delegates.cacheaware.IUlyssesCacheSearcherDelegate;
import net.iubris.ulysses.controller.delegates.locationaware.IUlyssesLocationSearcherDelegate;
import net.iubris.ulysses.controller.delegates.networkaware.IUlyssesNetworkSearcherDelegate;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.model.PlaceHere;
import android.location.Location;

import com.google.inject.Inject;

public class UlyssesSearcher 
//implements LocationNetworkAwareSearcher<Void, List<PlaceHere>, Boolean, Boolean> {
implements LocationUpdater, Searcher<Void, List<PlaceHere>> {
		
	private final IUlyssesLocationSearcherDelegate locationSearcherDelegate;
	private final IUlyssesNetworkSearcherDelegate networkSearcherDelegate;
	private final IUlyssesCacheSearcherDelegate cacheSearcherDelegate;
	
	private List<PlaceHere> result = new ArrayList<PlaceHere>();
	private final Object lock = new Object();
	private Location location;
		
	/*
	public UlyssesSearcher(UlyssesLocationSearcherDelegate ulyssesLocationSearcherDelegate,
			UlyssesNetworkSearcherDelegate ulyssesNetworkSearcherDelegate) {
		this.ulyssesLocationSearcherDelegate = ulyssesLocationSearcherDelegate;
		this.ulyssesNetworkSearcherDelegate = ulyssesNetworkSearcherDelegate;
	}
	*/	
	
	@Inject
	public UlyssesSearcher(IUlyssesLocationSearcherDelegate locationSearcherDelegate,
			IUlyssesNetworkSearcherDelegate networkSearcherDelegate,
			IUlyssesCacheSearcherDelegate cacheSearcherDelegate) {
		this.locationSearcherDelegate = locationSearcherDelegate;
		this.networkSearcherDelegate = networkSearcherDelegate;
		this.cacheSearcherDelegate = cacheSearcherDelegate;
	}

	@Override
	public List<PlaceHere> getSearchResult() {
		synchronized(lock) {
			return result;
		}
	}
	
	@Override
	public Void search() throws LocationNullException, LocationNotNewerStateException, NoNetworkException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException {
		synchronized(lock) {
			try { // newLocation=1, network=1
				locationSearcherDelegate.isInNewerLocation();
				location = locationSearcherDelegate.getLocation();
				//isInNewerLocation();
				searchByNetwork();
			} catch (LocationNotNewerStateException locationNotNewerStateException) { // newLocation=0, network=1
				if (isResultEmpty()) {
					try {
						searchByNetwork();
					} catch (NoNetworkException noNetworkException ) { // newLocation=0, network=0
						searchByCache();
						throw noNetworkException ;
					}
					throw locationNotNewerStateException;
				}
			} catch (NoNetworkException noNetworkException ) { // newLocation=1, network=0  
				searchByCache();
				throw noNetworkException ;
			}		
			return null;
		}
	}
	
	private void searchByNetwork() throws LocationNullException, NoNetworkException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException {
		networkSearcherDelegate.search( location );
		//ulyssesNetworkSearcherDelegate.search( getLocation() );
		//search(getLocation());
		result = networkSearcherDelegate.getSearchResult();
	}
	private void searchByCache() throws LocationNullException {
		cacheSearcherDelegate.search( location );
		result = cacheSearcherDelegate.getSearchResult();
		//result.clear();
	}

	private boolean isResultEmpty() {
		synchronized(lock) {
			return result.isEmpty();
		}
	}
/*
	@Override
	public Location getLocation() {
		return ulyssesLocationSearcherDelegate.getLocation();
	}

	@Override
	public Boolean isInNewerLocation() throws LocationNotNewerStateException {
		return ulyssesLocationSearcherDelegate.isInNewerLocation();
	}
*/

	@Override
	public void startLocationUpdates() {
		locationSearcherDelegate.startLocationUpdates();		
	}

	@Override
	public void stopLocationUpdates() {
		locationSearcherDelegate.stopLocationUpdates();		
	}
	
/*
	@Override
	public Boolean isConnected() throws NetworkStateException {
		return ulyssesNetworkSearcherDelegate.isConnected();		
	}

	@Override
	public Void search(Location location) throws NullLocationException, NoNetworkException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException {
		ulyssesNetworkSearcherDelegate.search(location);
		return null;
	}*/
}
