/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ProgressDialogForGetPlaceProvider.java is part of 'Ulysses'
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ulysses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Ulysses' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses._roboguice.progressdialog.providers;


import net.iubris.ulysses._roboguice.progressdialog.annotations.ProgressDialogSearchString;

import com.google.inject.Inject;
import com.google.inject.Provider;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogForGetPlaceProvider
implements Provider<ProgressDialog> {
//extends ProgressDialogProvider 
	
	//private Resources resources;
	
	//@InjectResource(name="net.iubris.ulysses:string/searching") private String searchingString;
	//@InjectResource(name="searching") private String searchingString;
	//@InjectResource(R.string.searching) 

	private final Context context;
	private final String searchingString;	 
	
	@Inject
	public ProgressDialogForGetPlaceProvider(Context context, @ProgressDialogSearchString String searchingString) {
		this.context = context;
		this.searchingString = searchingString;
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
