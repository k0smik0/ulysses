package net.iubris.ulysses.asynctask;

import java.util.List;

import android.app.Activity;

import net.iubris.diane.asynctask.DianeRoboAsyncTaskSearch;
import net.iubris.diane.searcher.locationaware.exceptions.location.LocationNotNewerStateException;
import net.iubris.diane.searcher.locationaware.exceptions.search.LocationAwareSearchException;
import net.iubris.diane.searcher.networkaware.exceptions.network.NoNetworkException;
import net.iubris.hermes.client.HermesClient;
import net.iubris.hermes.connector.exception.ControllerUnavailableException;
import net.iubris.hermes.ui.toast.utils.UIUtils;
import net.iubris.ulysses.R;
import net.iubris.ulysses.controller.UlyssesSearcher;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesTyrannusStatusException;
import net.iubris.ulysses.controller.delegates.networkaware.socrates.exceptions.google.PlacesUnbelievableZeroResultStatusException;
import net.iubris.ulysses.model.PlaceHere;

public abstract class UlyssesRoboAsyncTaskSearch
//<HC extends HermesClient<US,?>,US extends UlyssesSearcher> 
<HC extends Activity & HermesClient<US>,US extends UlyssesSearcher>
extends DianeRoboAsyncTaskSearch<List<PlaceHere>,HC,US> {
	
	protected UlyssesRoboAsyncTaskSearch(HC activity) {
		super(activity);		
	}
	
	@Override
	public Void call() throws ControllerUnavailableException, 
		LocationAwareSearchException, LocationNotNewerStateException, NoNetworkException, 
		PlacesUnbelievableZeroResultStatusException, PlacesTyrannusStatusException {
		return context.getController().search();
	}
	
	@Override
	protected void onException(LocationNotNewerStateException e) {
		UIUtils.showShortToast(R.string.exception_location_not_newer_state, e, context);
	}
	@Override
	protected void onException(LocationAwareSearchException e) {
		UIUtils.showShortToast(R.string.exception_location_aware_search, e, context);
	}
	@Override
	protected void onException(NoNetworkException e) {
		UIUtils.showShortToast(R.string.exception_no_network, e, context);
	}	
	protected void onException(PlacesUnbelievableZeroResultStatusException e) {
		UIUtils.showShortToast(R.string.exception_place_unbelievable_zero_result_status, e, context);		
	}
	protected void onException(PlacesTyrannusStatusException e) {
		UIUtils.showShortToast(R.string.exception_places_tyrannus_status, e, context);
	}
}
