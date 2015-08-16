/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ProgressDialogForSearchPlacesStringProvider.java is part of 'Ulysses'.
 ******************************************************************************/
package net.iubris.voyager._roboguice.providers;


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
