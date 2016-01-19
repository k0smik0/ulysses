package net.iubris.ulysses.tasks.search.aware;

import net.iubris.diane.aware.network.exceptions.base.NoNetworkException;
import net.iubris.diane.searcher.aware.location.exceptions.LocationAwareSearchException;
import net.iubris.diane.searcher.aware.network.exceptions.NetworkAwareSearchException;
import android.location.Location;

interface CopyOfLocationExceptionState {
	void tryToSearch(Location location, Class<? extends LocationAwareSearchException> locationAwareSearchExceptionClass) throws NoNetworkException, NetworkAwareSearchException;
	void completeExecution(Class<? extends LocationAwareSearchException> locationAwareSearchExceptionClass);
}