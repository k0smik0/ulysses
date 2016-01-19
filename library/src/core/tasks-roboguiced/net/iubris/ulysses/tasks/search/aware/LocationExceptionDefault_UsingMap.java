package net.iubris.ulysses.tasks.search.aware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import net.iubris.diane.aware.location.exceptions.LocationStateException;
import net.iubris.diane.searcher.aware.location.exceptions.LocationAwareSearchException;
import net.iubris.ulysses.model.Place;
import android.location.Location;

public class LocationExceptionDefault_UsingMap implements LocationExceptionState {

	private final SearchAwareTaskLocationStateable searchAwareTask;
//	private final UIMessageForLocationStateDefaultHandler uiMessageHandler;
	
	private final Map<Class<? extends LocationStateException>, UIMessageForLocationStateDefaultHandler> uiMessageForLocationStateDefaultHandlersMap = new HashMap<>() ;
	
	@Inject
	public LocationExceptionDefault_UsingMap(SearchAwareTaskLocationStateable searchAwareTask, 
//			UIMessageForLocationStateDefaultHandler uiMessageHandler, 
			Map<Class<? extends LocationStateException>, UIMessageForLocationStateDefaultHandler> uiMessageForLocationStateDefaultHandlersMap) {
		this.searchAwareTask = searchAwareTask;
//		this.uiMessageHandler = uiMessageHandler;
		this.uiMessageForLocationStateDefaultHandlersMap.putAll(uiMessageForLocationStateDefaultHandlersMap);
	}
	@Override
	public void tryToSearch(Location location, Class<? extends LocationAwareSearchException> extendingLocationAwareSearchExceptionClass) {
//		searchAwareTask.eventuallyShowErrorMessageOnLocationTooNearException(); // 6
//		uiMessageHandler.eventuallyShowErrorMessage();
		UIMessageForLocationStateDefaultHandler uiMessageForLocationStateDefaultHandler = uiMessageForLocationStateDefaultHandlersMap.get(extendingLocationAwareSearchExceptionClass);
		if (uiMessageForLocationStateDefaultHandler!=null) {
			uiMessageForLocationStateDefaultHandler.eventuallyShowErrorMessage();
		}
		Set<Place> r = new HashSet<Place>();
		r.addAll(searchAwareTask.getSearcher().getResult());
		r.addAll(searchAwareTask.getLocalizedSearcher().getResult());
		ArrayList<Place> result = new ArrayList<Place>(r);
		searchAwareTask.doOnSuccess(result);
	}
	@Override
	public void completeExecution(Class<? extends LocationAwareSearchException> locationAwareSearchExceptionClass) {/*nothing to complete*/ }
}