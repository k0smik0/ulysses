/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesUiModule.java is part of 'Ulysses'.
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ulysses' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.ui._di._roboguice.module;

import net.iubris.ulysses.ui._di.progressdialog.search.annotations.ProgressDialogForSearchPlaces;
import net.iubris.ulysses.ui._di.progressdialog.search.providers.ProgressDialogForSearchPlacesProvider;
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
		bind(String.class).annotatedWith(ProgressDialogForSearchPlaces.class).toProvider(net.iubris.voyager._roboguice.providers.ProgressDialogForSearchPlacesStringProvider.class)
	};
	
	protected abstract Drawable provideDefaultDrawable();
	
	/*@Provides @ListRowResource @Inject
	protected int providesListRowResource(Resources resources) {
		return resources.getInteger(R.layout.list_row);
	}*/
}
