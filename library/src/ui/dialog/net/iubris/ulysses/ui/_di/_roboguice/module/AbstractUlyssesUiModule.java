package net.iubris.ulysses.ui._di._roboguice.module;

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

}
