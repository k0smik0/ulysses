package net.iubris.ulysses.tasks.search.aware.locationstate;

import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.location.exceptions.LocationAwareSearchException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import android.location.Location;

public interface LocationExceptionState {
	void tryToSearch(Location location/*, Class<? extends LocationAwareSearchException> locationAwareSearchExceptionClass*/) throws NoNetworkException, NetworkAwareSearchException;
	void completeExecution(/*Class<? extends LocationAwareSearchException> locationAwareSearchExceptionClass*/);
	Class<? extends LocationAwareSearchException> getHandlingLocationAwareSearchExceptionClass();
}