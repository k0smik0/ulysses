/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ExceptionUtils.java is part of 'Ratafia'.
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
package net.iubris.voyager.utils.exceptions;

import net.iubris.ulysses.ui.toast.utils.UIUtils;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ExceptionUtils {
	
	/**
	 * Log.d, show exception message with shortly toast and print stacktrace
	 * @param exception
	 * @param context
	 */
	public static void showShortlyException(Exception exception, Context context) {
		String exceptionMessage = getExceptionMessage(exception);
//		Log.d("ExceptionUtils:10", "onException("+exception.getClass().getSimpleName()+"): "+exceptionMessage);
//		Toast.makeText(context, exceptionMessage, Toast.LENGTH_SHORT).show();
		UIUtils.showShortToast(exceptionMessage, context);
		exception.printStackTrace();
	}
	/**
	 * Log.d, show exception message with shortly toast and print stacktrace
	 * @param exception
	 * @param context
	 * @param prefixMessage
	 */
	public static void showShortlyException(Exception exception, String prefixMessage, Context context) {
		String exceptionMessage = getExceptionMessage(exception);
Log.d("ExceptionUtils:10",prefixMessage+"\n"+"onException("+exception.getClass().getSimpleName()+"): "+exceptionMessage);
		Toast.makeText(context, prefixMessage+"\n"+exceptionMessage, Toast.LENGTH_SHORT).show();
		exception.printStackTrace();
	}
	
	/*private void showException(Exception exception,String suffix) {
		String exceptionMessage = getExceptionMessage(exception);
		Log.d("UsingUlyssesAsyncTask:91", "onException("+exception.getClass().getSimpleName()+"): "+exceptionMessage);
		Toast.makeText(context,exceptionMessage+"\n\n"+suffix, Toast.LENGTH_SHORT).show();
	}*/
	private static String getExceptionMessage(Exception e) {
		String message = e.getMessage();
		Throwable cause = e.getCause();
		if (cause!=null)
			message.concat("\ncaused from: "+cause.getMessage());
		return message;
	}
}
