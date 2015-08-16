package net.iubris.ulysses.ui_di.progressdialog.providers.search;
/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ProgressDialogForSearchPlacesStringProvider.java is part of 'Ratafia'.
 * 
 * 'Ratafia' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ratafia' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with 'Ratafia'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/


import javax.inject.Inject;

import net.iubris.ulysses.R;
import net.iubris.ulysses.ui._di.progressdialog.search.providers.AbstractProgressDialogForSearchPlacesStringProvider;
import android.content.res.Resources;

public class ProgressDialogForSearchPlacesStringProvider extends AbstractProgressDialogForSearchPlacesStringProvider {

	@Inject
	public ProgressDialogForSearchPlacesStringProvider(Resources resources) {
		super(resources);
	}

	@Override
	public String get() {
		return resources.getString(R.string.search__searching);
	}

}
