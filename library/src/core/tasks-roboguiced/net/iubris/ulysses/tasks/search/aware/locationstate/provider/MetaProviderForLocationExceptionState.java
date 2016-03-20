package net.iubris.ulysses.tasks.search.aware.locationstate.provider;

import javax.inject.Provider;

import net.iubris.ulysses.tasks.search.aware.SearchAwareTaskLocationStateable;
import net.iubris.ulysses.tasks.search.aware.locationstate.LocationExceptionState;

public interface MetaProviderForLocationExceptionState extends Provider<LocationExceptionState> {
	MetaProviderForLocationExceptionState eventuallyInit(SearchAwareTaskLocationStateable searchAwareTaskLocationStateable);
	void setLocationExceptionState(LocationExceptionState locationExceptionState);
	void setAfterFirstResult();
}
