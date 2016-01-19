package net.iubris.ulysses.tasks.search.aware.locationstate.provider;

import net.iubris.ulysses.tasks.search.aware.locationstate.LocationExceptionState;
import net.iubris.ulysses.tasks.search.aware.locationstate.uimessage.UIMessageForLocationStateHandlerAfterFirstResult;
import net.iubris.ulysses.tasks.search.aware.locationstate.uimessage.UIMessageForLocationStateHandlerBeforeFirstResult;

public abstract class AbstractMetaProviderForLocationExceptionState implements
		MetaProviderForLocationExceptionState {

	protected LocationExceptionState locationExceptionState;
	
	protected final UIMessageForLocationStateHandlerBeforeFirstResult uiMessageForLocationStateHandlerBeforeFirstResult;
	protected final UIMessageForLocationStateHandlerAfterFirstResult uiMessageForLocationStateHandlerAfterFirstResult;
	
	public AbstractMetaProviderForLocationExceptionState(UIMessageForLocationStateHandlerBeforeFirstResult uiMessageForLocationStateHandlerBeforeFirstResult, 
			UIMessageForLocationStateHandlerAfterFirstResult uiMessageForLocationStateHandlerAfterFirstResult) {
		this.uiMessageForLocationStateHandlerBeforeFirstResult = uiMessageForLocationStateHandlerBeforeFirstResult;
		this.uiMessageForLocationStateHandlerAfterFirstResult = uiMessageForLocationStateHandlerAfterFirstResult;	
	}

	@Override
	public void setLocationExceptionState(LocationExceptionState locationExceptionState) {
		this.locationExceptionState = locationExceptionState;
	}

	@Override
	public LocationExceptionState get() {
		return locationExceptionState;
	}

}
