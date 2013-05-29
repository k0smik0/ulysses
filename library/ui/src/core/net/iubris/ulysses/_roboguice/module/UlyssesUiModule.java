/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesModule.java is part of 'Ulysses'.
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
 * along with 'Ulysses' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses._roboguice.module;

import net.iubris.ulysses._roboguice.progressdialog.annotations.search.ProgressDialogForSearchPlaces;
import net.iubris.ulysses._roboguice.progressdialog.annotations.search.ProgressDialogForSearchPlacesString;
import net.iubris.ulysses._roboguice.progressdialog.providers.search.ProgressDialogForSearchPlacesProvider;
import net.iubris.ulysses._roboguice.progressdialog.providers.search.ProgressDialogForSearchPlacesStringProvider;
import android.app.ProgressDialog;

import com.google.inject.AbstractModule;


public class UlyssesUiModule extends AbstractModule {
	
	@Override
	protected void configure() {		
		//bind(ProgressDialogProvider.class).annotatedWith(ProgressDialogProviderForGetPlace.class).to(ProgressDialogProviderGetPlace.class);
		bindDialogSearch();			
	}
	
	protected void bindDialogSearch() {
		bind(String.class).annotatedWith(ProgressDialogForSearchPlacesString.class).toProvider(ProgressDialogForSearchPlacesStringProvider.class);
		bind(ProgressDialog.class).annotatedWith(ProgressDialogForSearchPlaces.class).toProvider(ProgressDialogForSearchPlacesProvider.class);
	};
}
