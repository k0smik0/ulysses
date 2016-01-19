package net.iubris.ulysses.tasks.search.aware.locationstate;

import net.iubris.diane.searcher.aware.location.exceptions.LocationAwareSearchException;
import net.iubris.ulysses.tasks.search.aware.SearchAwareTaskLocationStateable;


public abstract class AbstractLocationExceptionState implements LocationExceptionState {

	protected final SearchAwareTaskLocationStateable searchAwareTask;
	protected final Class<? extends LocationAwareSearchException> extendingLocationAwareSearchExceptionClass;
	
	protected AbstractLocationExceptionState(SearchAwareTaskLocationStateable searchAwareTask,
			Class<? extends LocationAwareSearchException> extendingLocationAwareSearchExceptionClass) {
		this.searchAwareTask = searchAwareTask;
		this.extendingLocationAwareSearchExceptionClass = extendingLocationAwareSearchExceptionClass;
	}	
	
	@Override
	public Class<? extends LocationAwareSearchException> getHandlingLocationAwareSearchExceptionClass() {
		return extendingLocationAwareSearchExceptionClass;
	}
}
