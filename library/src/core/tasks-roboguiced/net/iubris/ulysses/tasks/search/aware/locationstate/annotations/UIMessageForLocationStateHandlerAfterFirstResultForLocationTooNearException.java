package net.iubris.ulysses.tasks.search.aware.locationstate.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Qualifier
@Target(value={ElementType.ANNOTATION_TYPE,
		ElementType.FIELD,
		ElementType.LOCAL_VARIABLE,
		ElementType.METHOD, 
		ElementType.PARAMETER})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface UIMessageForLocationStateHandlerAfterFirstResultForLocationTooNearException {

}
