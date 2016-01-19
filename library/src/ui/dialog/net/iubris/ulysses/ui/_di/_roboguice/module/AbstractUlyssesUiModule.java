package net.iubris.ulysses.ui._di._roboguice.module;

import java.util.List;

import net.iubris.ulysses.engine.searcher.location.aware.cache.DefaultUlyssesLocalizedSearcherCacheAware.CacheSearchExceptions;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.tasks.search.aware.locationstate.annotations.UIMessageForLocationStateHandlerAfterFirstResultForLocationNotSoUsefulException;
import net.iubris.ulysses.tasks.search.aware.locationstate.annotations.UIMessageForLocationStateHandlerAfterFirstResultForLocationTooNearException;
import net.iubris.ulysses.tasks.search.aware.locationstate.annotations.UIMessageForLocationStateHandlerBeforeFirstResultForLocationNotSoUsefulException;
import net.iubris.ulysses.tasks.search.aware.locationstate.annotations.UIMessageForLocationStateHandlerBeforeFirstResultForLocationTooNearException;
import net.iubris.ulysses.tasks.search.aware.locationstate.provider.DefaultMetaProviderForLocationExceptionStateForLocationNotSoUsefulException;
import net.iubris.ulysses.tasks.search.aware.locationstate.provider.DefaultMetaProviderForLocationExceptionStateForLocationTooNearException;
import net.iubris.ulysses.tasks.search.aware.locationstate.provider.MetaProviderForLocationExceptionStateForLocationNotSoUsefulException;
import net.iubris.ulysses.tasks.search.aware.locationstate.provider.MetaProviderForLocationExceptionStateForLocationTooNearException;
import net.iubris.ulysses.tasks.search.aware.locationstate.uimessage.UIMessageForLocationStateHandlerAfterFirstResult;
import net.iubris.ulysses.tasks.search.aware.locationstate.uimessage.UIMessageForLocationStateHandlerBeforeFirstResult;
import net.iubris.ulysses.ui._di.progressdialog.search.annotations.ProgressDialogForSearchPlaces;
import net.iubris.ulysses.ui._di.progressdialog.search.annotations.ProgressDialogForSearchPlacesString;
import net.iubris.ulysses.ui._di.progressdialog.search.providers.ProgressDialogForSearchPlacesProvider;
import net.iubris.ulysses.ui_di.progressdialog.providers.search.ProgressDialogForSearchPlacesStringProvider;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;

import com.google.inject.AbstractModule;

public abstract class AbstractUlyssesUiModule extends AbstractModule {

	@Override
	protected void configure() {		
		bindDialogSearch();
//		bind(Integer.class).annotatedWith(ListRowResource.class).toProvider(ListRowResourceProvider.class);
//		bindPlaceTypeActionsMap();
		provideDefaultDrawable();
		bindUIMessageForLocationStateHandlerBeforeFirstResultForLocationTooNearException();
		bindUIMessageForLocationStateHandlerAfterFirstResultForLocationTooNearException();
		bindUIMessageForLocationStateHandlerBeforeFirstResultForLocationNotSoUsefulException();
		bindUIMessageForLocationStateHandlerAfterFirstResultForLocationNotSoUsefulException();
		
		
		bindMetaProviderForLocationExceptionStateForLocationNotSoUsefulException();
		bindMetaProviderForLocationExceptionStateForLocationTooNearException();
	}
	
	/**
	 * bind here the "Map<PlaceType,PlaceTypeAction<Void>" used in 
	 * 
	 * bind(new TypeLiteral<Map<PlaceType,PlaceTypeAction<Void>>>(){}).annotatedWith(RatafiaPlaceTypeActions.class).toProvider(RatafiaPlaceTypeActionsMapProvider.class);
	 */
//	private void bindPlaceTypeActionsMap() {}

	protected void bindDialogSearch() {
//		bind(String.class).annotatedWith(ProgressDialogForSearchPlacesString.class).toProvider(ProgressDialogForSearchPlacesStringProvider.class);
		bind_String_AnnotatedWith_ProgressDialogForSearchPlacesString();
		bind(ProgressDialog.class).annotatedWith(ProgressDialogForSearchPlaces.class).toProvider(ProgressDialogForSearchPlacesProvider.class);
	};
	
	protected void bind_String_AnnotatedWith_ProgressDialogForSearchPlacesString() {
		bind(String.class)
		.annotatedWith(ProgressDialogForSearchPlacesString.class)
		.toProvider(ProgressDialogForSearchPlacesStringProvider.class);// toInstance("...searching places...");
	};
	
	protected abstract Drawable provideDefaultDrawable();
	
	/*@Provides @ListRowResource @Inject
	protected int providesListRowResource(Resources resources) {
		return resources.getInteger(R.layout.list_row);
	}*/
	
	protected void bindUIMessageForLocationStateHandlerAfterFirstResultForLocationTooNearException() {
		bind(UIMessageForLocationStateHandlerAfterFirstResult.class)
		.annotatedWith(UIMessageForLocationStateHandlerAfterFirstResultForLocationTooNearException.class)
		.toInstance( new UIMessageForLocationStateHandlerAfterFirstResult() {
				@Override
				public void eventuallyShowErrorMessage() {}
			} 
		);
	}
	protected void bindUIMessageForLocationStateHandlerBeforeFirstResultForLocationNotSoUsefulException() {
		bind(UIMessageForLocationStateHandlerBeforeFirstResult.class)
		.annotatedWith(UIMessageForLocationStateHandlerBeforeFirstResultForLocationNotSoUsefulException.class)
		.toInstance( new UIMessageForLocationStateHandlerBeforeFirstResult() {
				@Override
				public void eventuallyHandleInUIPreSearching() {}
				@Override
				public void eventuallyHandleInUISearchingFailedByCache(CacheSearchExceptions valueOf) {}
				@Override
				public void eventuallyHandleInUISearchingFailedByCacheWithoutSpecificReason() {}
				@Override
				public void eventuallyHandleInUISearchResultUsingCache(List<Place> result) {}
				@Override
				public void eventuallyHandleInUISearchResultUsingNetwork(List<Place> result) {}
			}
		);
	}
	
	protected void bindUIMessageForLocationStateHandlerAfterFirstResultForLocationNotSoUsefulException() {
		bind(UIMessageForLocationStateHandlerAfterFirstResult.class)
		.annotatedWith(UIMessageForLocationStateHandlerAfterFirstResultForLocationNotSoUsefulException.class)
		.toInstance( new UIMessageForLocationStateHandlerAfterFirstResult() {
				@Override
				public void eventuallyShowErrorMessage() {}
			} 
		);
	}
	protected void bindUIMessageForLocationStateHandlerBeforeFirstResultForLocationTooNearException() {
		bind(UIMessageForLocationStateHandlerBeforeFirstResult.class)
		.annotatedWith(UIMessageForLocationStateHandlerBeforeFirstResultForLocationTooNearException.class)
		.toInstance( new UIMessageForLocationStateHandlerBeforeFirstResult() {
				@Override
				public void eventuallyHandleInUIPreSearching() {}
				@Override
				public void eventuallyHandleInUISearchingFailedByCache(CacheSearchExceptions valueOf) {}
				@Override
				public void eventuallyHandleInUISearchingFailedByCacheWithoutSpecificReason() {}
				@Override
				public void eventuallyHandleInUISearchResultUsingCache(List<Place> result) {}
				@Override
				public void eventuallyHandleInUISearchResultUsingNetwork(List<Place> result) {}
			}
		);
	}
	
	private void bindMetaProviderForLocationExceptionStateForLocationNotSoUsefulException() {
		bind(MetaProviderForLocationExceptionStateForLocationNotSoUsefulException.class).to(DefaultMetaProviderForLocationExceptionStateForLocationNotSoUsefulException.class);
	}
	private void bindMetaProviderForLocationExceptionStateForLocationTooNearException() {
		bind(MetaProviderForLocationExceptionStateForLocationTooNearException.class).to(DefaultMetaProviderForLocationExceptionStateForLocationTooNearException.class);
	}
}
