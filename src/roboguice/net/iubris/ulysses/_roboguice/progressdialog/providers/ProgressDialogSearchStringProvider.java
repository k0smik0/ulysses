package net.iubris.ulysses._roboguice.progressdialog.providers;

import net.iubris.ulysses.R;

import android.content.res.Resources;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ProgressDialogSearchStringProvider implements Provider<String> {

	private final Resources resources;
	
	@Inject
	public ProgressDialogSearchStringProvider(Resources resources) {
		this.resources = resources;
	}

	@Override
	public String get() {
		return resources.getString(R.string.searching);
	}
}
