package net.iubris.ulysses._roboguice.progressdialog.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

@BindingAnnotation
@Target(value={ElementType.ANNOTATION_TYPE,
		ElementType.FIELD,
		ElementType.LOCAL_VARIABLE,
		ElementType.METHOD, 
		ElementType.PARAMETER})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ProgressDialogSearchString {}
