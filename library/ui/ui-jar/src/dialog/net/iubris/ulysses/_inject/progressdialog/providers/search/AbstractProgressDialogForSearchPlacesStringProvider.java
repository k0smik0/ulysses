/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ProgressDialogSearchStringProvider.java is part of 'Ulysses'.
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
package net.iubris.ulysses._inject.progressdialog.providers.search;

import javax.inject.Inject;
import javax.inject.Provider;

import roboguice.inject.ContextSingleton;
import android.content.res.Resources;

@ContextSingleton
public abstract class AbstractProgressDialogForSearchPlacesStringProvider implements Provider<String> {

	protected final Resources resources;
	
	@Inject
	public AbstractProgressDialogForSearchPlacesStringProvider(Resources resources) {
		this.resources = resources;
	}
	
}
