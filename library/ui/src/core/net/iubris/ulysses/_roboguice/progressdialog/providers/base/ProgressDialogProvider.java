/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ProgressDialogProvider.java is part of 'Ulysses'.
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
package net.iubris.ulysses._roboguice.progressdialog.providers.base;


import android.app.ProgressDialog;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Provider;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class ProgressDialogProvider implements /*ContextScoped*/Provider<ProgressDialog> {

	private Context context;
	
	@Inject
	public ProgressDialogProvider(Context context) {
		this.context = context;
	}

	@Override
	public ProgressDialog get() {
		return new ProgressDialog(context);
	}	
		
	/*
	@Override
	public ProgressDialog get(Context context) {
		final ProgressDialog progressDialog = new ProgressDialog(context);
		initProgressDialog(progressDialog,context);
		return progressDialog;
	}*/
		
	//abstract protected void initProgressDialog(ProgressDialog progressDialog, Context context);
}

