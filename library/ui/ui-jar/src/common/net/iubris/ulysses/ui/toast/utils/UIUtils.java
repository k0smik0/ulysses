/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UIUtils.java is part of Ulysses.
 * 
 * hermes is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * hermes is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with hermes ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.ui.toast.utils;

import android.content.Context;
import android.widget.Toast;

public class UIUtils {
	/** 
	 * Short way to build a short toast
	 * 
	 * @param context
	 * @param message
	 * @return Toast
	 */
	public static Toast buildShortToast(Context context, String message) {
		return Toast.makeText(context, message, Toast.LENGTH_SHORT);
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
