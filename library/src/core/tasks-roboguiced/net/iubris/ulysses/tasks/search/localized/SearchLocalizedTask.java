/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UIyssesSearchAsyncTask.java is part of 'Ulysses'.
 ******************************************************************************/
package net.iubris.ulysses.tasks.search.localized;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.util.Ln;
import net.iubris.diane.aware.cache.exceptions.base.CacheEmptyException;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.diane.tasks.search.RoboSearchLocalizedAsyncTask;
import net.iubris.ulysses.engine.searcher.aware.full.DefaultUlyssesSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.full.DefaultUlyssesLocalizedSearcher;
import net.iubris.ulysses.engine.searcher.location.aware.full.UlyssesLocalizedSearcher;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.tasks.search.localized.exception.AddressNullException;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

/**
 * for searching task not location aware (provides only network/cache awarenesss)
 */
@Singleton
public class SearchLocalizedTask extends RoboSearchLocalizedAsyncTask<DefaultUlyssesSearcher,Void,Void,List<Place>> {

	@Inject
	protected UlyssesLocalizedSearcher ulyssesLocalizedSearcher;
	
	private String address;

//	private Location location;
	
	@Inject
	public SearchLocalizedTask(Context context) {
		super(context);
	}
	
	@SuppressWarnings("deprecation")
	public void execute(String address) throws SearchException {
		if (address==null || address=="") {
			throw new SearchException("search address can not be null");
		}
		this.address = address;
		super.execute();
	}
	/*public void execute(Location location) throws SearchException {
		if (location==null)
			throw new SearchException("location can not be null");
		this.location = location;
		super.execute();
	}*/
	
	/**
	 * don't use this; instead, use {@link #execute(String address)}
	 */
	@Deprecated
	@Override
	public final void execute() {
		super.execute();
	}
	
	/**
	 * default:<br/>
	 * 1. Location[] locations = {@link #buildLocations() }<br/> 
	 * 2. invoke {@link #callLocalizedSearcher(Location[]) } passing 'locations' to it;
	 * @throws AddressNullException 
	 * @throws NetworkAwareSearchException 
	 * @throws NoNetworkException 
	 * @throws IOException 
	 */
	@Override
	public List<Place> call() throws AddressNullException, NoNetworkException, NetworkAwareSearchException, 
	CacheEmptyException, /*NoNetworkAndCacheEmptyException,*/ IOException {
		/*if (this.location!=null) {
			List<Place> result = callLocalizedSearcher(new Location[]{ location });
			this.location = null;
			return result;
		}*/
		if (address==null || address=="")
			throw new AddressNullException();
		Location[] dummyLocations = buildLocations();
		return callLocalizedSearcher(dummyLocations);
	}
	protected Location[] buildLocations() throws IOException {
		List<Address> addresses = new Geocoder(context).getFromLocationName(address, 5);
		Address address0 = addresses.get(0);
		Location[] ls = new Location[1];
		ls[0] = new Location("dummy");
		ls[0].setLatitude(address0.getLatitude());
		ls[0].setLongitude(address0.getLongitude());
		return ls;
	}
	/**
	 * default: calls {@link DefaultUlyssesLocalizedSearcher#tryToSearch(locations)} to retrieve results
	 * @param {@link Location}
	 * @return {@link Place}
	 * @throws NetworkAwareSearchException 
	 * @throws NoNetworkException 
	 */
	protected List<Place> callLocalizedSearcher(Location[] locations) throws NoNetworkException, 
	NetworkAwareSearchException, CacheEmptyException/*, NoNetworkAndCacheEmptyException*/ {
		try {
			ulyssesLocalizedSearcher.search(locations);
		} catch (CacheAwareSearchException e) {
			Ln.d("CacheAwareSearchException: "+e);
		}
		return ulyssesLocalizedSearcher.getResult(); 
	}
	
	/**
	 * default implementation executes {@link Exception#printStackTrace()}
	 */
	@Override
	protected void onException(NullPointerException e) throws RuntimeException {
		e.printStackTrace();
	}
}