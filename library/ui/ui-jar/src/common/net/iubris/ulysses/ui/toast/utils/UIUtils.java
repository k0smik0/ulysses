/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UIUtils.java is part of 'Ulysses'.
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
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.ui.toast.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class UIUtils {
	/** 
	 * Short way to build a short toast
	 * 
	 * @param context
	 * @param message
	 * @return Toast
	 */
	@SuppressLint("ShowToast")
	public static Toast buildShortToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		TextView v = (TextView)toast.getView().findViewById(android.R.id.message);
		if( v != null) v.setGravity(Gravity.CENTER);
//		return Toast.makeText(context, message, Toast.LENGTH_SHORT);
		return toast;
	}
	
	/**
	 * Short way to show a short toast
	 * 
	 * @param message
	 * @param context
	 */
	public static void showShortToast(String message, Context context) {
		UIUtils.buildShortToast(context, message).show();
	}
	/**
	 * Short way to show a short toast
	 * 
	 * @param resourceId
	 * @param context
	 */
	public static void showShortToast(int resourceId, Context context) {
		UIUtils.buildShortToast(context, UIUtils.getResourceString(resourceId,context)).show();
	}
	/**
	 * Short way to show a short toast, adding exception message to toast message
	 * 
	 * @param resourceId
	 * @param e
	 * @param context
	 */
	/*public static void showShortToast(int resourceId, Exception e, Context context) {		
		UIUtils.buildShortToast(context, UIUtils.getResourceString(resourceId,context) +"\n\n"+e.getMessage()).show();
	}*/
	
	/** 
	 * Short way to build a short toast
	 * 
	 * @param context
	 * @param message
	 * @return Toast
	 */
	@SuppressLint("ShowToast")
	public static Toast buildLongToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		TextView v = (TextView)toast.getView().findViewById(android.R.id.message);
		if( v != null) v.setGravity(Gravity.CENTER);
//		return Toast.makeText(context, message, Toast.LENGTH_LONG);
		return toast;
	}
	
	/**
	 * Short way to show a short toast
	 * 
	 * @param message
	 * @param context
	 */
	public static void showLongToast(String message, Context context) {
		UIUtils.buildLongToast(context, message).show();
	}
	/**
	 * Short way to show a short toast
	 * 
	 * @param resourceId
	 * @param context
	 */
	public static void showLongToast(int resourceId, Context context) {
		UIUtils.buildLongToast(context, UIUtils.getResourceString(resourceId,context)).show();
	}
	
	
	
	
	/**
	 * Short way to get a string resource
	 * 
	 * @param resourceId
	 * @param context
	 * @return
	 */
	public static String getResourceString(int resourceId, Context context) {
		return context.getResources().getString(resourceId);
	}
	
	
}
