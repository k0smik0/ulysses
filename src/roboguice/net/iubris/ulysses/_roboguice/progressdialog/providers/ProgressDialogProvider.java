package net.iubris.ulysses._roboguice.progressdialog.providers;

import roboguice.inject.ContextScopedProvider;

import android.app.ProgressDialog;

public abstract class ProgressDialogProvider extends ContextScopedProvider<ProgressDialog> {	
		
	/*
	@Override
	public ProgressDialog get(Context context) {
		final ProgressDialog progressDialog = new ProgressDialog(context);
		initProgressDialog(progressDialog,context);
		return progressDialog;
	}*/
		
	//abstract protected void initProgressDialog(ProgressDialog progressDialog, Context context);
}

