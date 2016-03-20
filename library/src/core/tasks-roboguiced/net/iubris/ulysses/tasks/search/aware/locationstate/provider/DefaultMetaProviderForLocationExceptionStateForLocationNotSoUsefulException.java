package net.iubris.ulysses.tasks.search.aware.locationstate.provider;

import javax.inject.Inject;

import net.iubris.diane.searcher.aware.location.exceptions.base.LocationNotSoUsefulException;
import net.iubris.ulysses.tasks.search.aware.SearchAwareTaskLocationStateable;
import net.iubris.ulysses.tasks.search.aware.locationstate.LocationExceptionAfterFirstResult;
import net.iubris.ulysses.tasks.search.aware.locationstate.LocationExceptionStateBeforeFirstResult;
import net.iubris.ulysses.tasks.search.aware.locationstate.annotations.UIMessageForLocationStateHandlerAfterFirstResultForLocationNotSoUsefulException;
import net.iubris.ulysses.tasks.search.aware.locationstate.annotations.UIMessageForLocationStateHandlerBeforeFirstResultForLocationNotSoUsefulException;
import net.iubris.ulysses.tasks.search.aware.locationstate.uimessage.UIMessageForLocationStateHandlerAfterFirstResult;
import net.iubris.ulysses.tasks.search.aware.locationstate.uimessage.UIMessageForLocationStateHandlerBeforeFirstResult;
import roboguice.util.Ln;

public class DefaultMetaProviderForLocationExceptionStateForLocationNotSoUsefulException
	extends AbstractMetaProviderForLocationExceptionState
	implements MetaProviderForLocationExceptionStateForLocationNotSoUsefulException {

	private LocationExceptionAfterFirstResult locationExceptionDefault;

	@Inject
	public DefaultMetaProviderForLocationExceptionStateForLocationNotSoUsefulException(
			@UIMessageForLocationStateHandlerBeforeFirstResultForLocationNotSoUsefulException UIMessageForLocationStateHandlerBeforeFirstResult uiMessageForLocationStateNeverHappenedBeforeHandler, 
			@UIMessageForLocationStateHandlerAfterFirstResultForLocationNotSoUsefulException UIMessageForLocationStateHandlerAfterFirstResult uiMessageForLocationStateDefaultHandler) {
		super(uiMessageForLocationStateNeverHappenedBeforeHandler, uiMessageForLocationStateDefaultHandler);	
	}

	@Override
	public MetaProviderForLocationExceptionState eventuallyInit(SearchAwareTaskLocationStateable searchAwareTaskLocationStateable) {
		if (locationExceptionState==null) {
			Ln.d("Init DefaultMetaProviderForLocationExceptionStateForLocationNotSoUsefulException");
			locationExceptionState = new LocationExceptionStateBeforeFirstResult(searchAwareTaskLocationStateable, LocationNotSoUsefulException.class, uiMessageForLocationStateHandlerBeforeFirstResult, locationExceptionDefault);
			locationExceptionDefault = new LocationExceptionAfterFirstResult(searchAwareTaskLocationStateable, LocationNotSoUsefulException.class, uiMessageForLocationStateHandlerAfterFirstResult);
		}
		return this;
	}
	
	@Override
	public void setAfterFirstResult() {
		locationExceptionState = locationExceptionDefault;
		Ln.d("locationExceptionState is set to LocationExceptionAfterFirstResult");
	}
}
