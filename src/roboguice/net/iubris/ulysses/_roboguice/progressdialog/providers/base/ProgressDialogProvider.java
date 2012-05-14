/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ProgressDialogProvider.java is part of ulysses.
 * 
 * ulysses is free software; you can redistribute it and/or modify
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
 * along with ulysses ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses._roboguice.progressdialog.providers.base;

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

