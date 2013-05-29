package net.iubris.ulysses.searcher.aware.full;

import java.util.List;

import javax.inject.Inject;

import net.iubris.diane.aware.cache.exceptions.base.CacheTooOldException;
import net.iubris.diane.aware.location.state.three.ThreeStateLocationAwareLocationSupplier;
import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.cache.exceptions.CacheAwareSearchException;
import net.iubris.diane.searcher.aware.full.base.DefaultFullAwareSearcher;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.diane.searcher.aware.location.exceptions.base.LocationTooNearException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import net.iubris.diane.searcher.location.aware.full.LocalizedSearcherCacheNetworkAwareStrictChecking;
import net.iubris.ulysses.model.PlaceHere;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesRetrievingException;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.searcher.location.aware.network.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import android.util.Log;

public class UlyssesSearcher extends DefaultFullAwareSearcher<List<PlaceHere>> {

	@Inject
	public UlyssesSearcher(ThreeStateLocationAwareLocationSupplier locationAwareSupplier,
			LocalizedSearcherCacheNetworkAwareStrictChecking<List<PlaceHere>> localizedSearcher) {
		super(locationAwareSupplier, localizedSearcher);
	}
	
	@Override
	public Void search(Void... v) throws 
		LocationTooNearException,
		LocationNotSoUsefulException, NoNetworkException,
		PlacesRetrievingException, PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException
		,NetworkAwareSearchException {
		try {
			return super.search();
			// exceptions below are never throwed here, so we get away from method signature
		} catch (CacheTooOldException e) {
			Log.d("UlyssesSearcher:39",e.getMessage());
		} catch (CacheAwareSearchException e) {
			Log.d("UlyssesSearcher:41",e.getMessage());
		} 
		/*catch (NetworkAwareSearchException e) {
			Log.d("UlyssesSearcher:42",e.getMessage());
//			e.printStackTrace();
		}*/
		return null;
	}
}
