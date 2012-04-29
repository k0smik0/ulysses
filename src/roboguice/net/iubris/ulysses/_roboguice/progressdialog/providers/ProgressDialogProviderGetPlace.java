package net.iubris.ulysses._roboguice.progressdialog.providers;


import net.iubris.ulysses.R;

import com.google.inject.Inject;
import com.google.inject.Provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

public class ProgressDialogProviderGetPlace
implements Provider<ProgressDialog> {
//extends ProgressDialogProvider 
	
	//private Resources resources;
	
	//@InjectResource(name="net.iubris.ulysses:string/searching") private String searchingString;
	//@InjectResource(name="searching") private String searchingString;
	//@InjectResource(R.string.searching) 
	private final String searchingString;
	

	private final Context context; 
	
	@Inject
	public ProgressDialogProviderGetPlace(Context context, Resources resources) {
		//this.resources = resources;
		searchingString = resources.getString(R.string.searching);
		this.context = context;
//Ln.d(resources);
	}

	//@Override
	public ProgressDialog get(Context context) {
		final ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setMessage( searchingString );
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		return progressDialog;
	}

	@Override
	public ProgressDialog get() {
		return get(context);
		
	}
	
	/*@Override
	public void initProgressDialog(ProgressDialog progressDialog, Context context) {		
		//progressDialog.setTitle("Retrieving...");
		progressDialog.setMessage( context.getResources().getString( R.string.searching) );
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
	}*/
}
