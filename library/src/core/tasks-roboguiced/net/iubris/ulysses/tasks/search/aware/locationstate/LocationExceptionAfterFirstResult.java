package net.iubris.ulysses.tasks.search.aware.locationstate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.iubris.diane.searcher.aware.location.exceptions.LocationAwareSearchException;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.tasks.search.aware.SearchAwareTaskLocationStateable;
import net.iubris.ulysses.tasks.search.aware.locationstate.uimessage.UIMessageForLocationStateHandlerAfterFirstResult;
import android.location.Location;

public class LocationExceptionAfterFirstResult extends AbstractLocationExceptionState {

	private final UIMessageForLocationStateHandlerAfterFirstResult uiMessageHandler;
//	private final Map<Class<? extends LocationStateException>, UIMessageForLocationStateDefaultHandler> uiMessageForLocationStateDefaultHandlersMap = new HashMap<>() ;
	
//	@Inject
	public LocationExceptionAfterFirstResult(SearchAwareTaskLocationStateable searchAwareTask,
			Class<? extends LocationAwareSearchException> extendingLocationAwareSearchExceptionClass
			, UIMessageForLocationStateHandlerAfterFirstResult uiMessageHandler 
//			,Map<Class<? extends LocationStateException>, UIMessageForLocationStateDefaultHandler> uiMessageForLocationStateDefaultHandlersMap
			) {
		super(searchAwareTask, extendingLocationAwareSearchExceptionClass);
		this.uiMessageHandler = uiMessageHandler;
	}
	@Override
	public void tryToSearch(Location location/*, Class<? extends LocationAwareSearchException> extendingLocationAwareSearchExceptionClass*/) {
//		searchAwareTask.eventuallyShowErrorMessageOnLocationTooNearException(); // 6
		uiMessageHandler.eventuallyShowErrorMessage();
		Set<Place> r = new HashSet<Place>();
		r.addAll(searchAwareTask.getSearcher().getResult());
		r.addAll(searchAwareTask.getLocalizedSearcher().getResult());
		ArrayList<Place> result = new ArrayList<Place>(r);
		searchAwareTask.doOnSuccess(result);
	}
	@Override
	public void completeExecution(/*Class<? extends LocationAwareSearchException> locationAwareSearchExceptionClass*/) {/*nothing to complete*/ }
}