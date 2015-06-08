/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ExceptionUtils.java is part of Diane.
 * 
 * Diane is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Diane is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Diane; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses_demo.activity.main.task;

import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ExceptionUtils {
	
	public static void showException(Exception exception, TextView textView) {
		String exceptionMessage = getExceptionMessage(exception);
		Log.d("DianeRoboSampleSearchAsyncTask:77", "onException("+exception.getClass().getSimpleName()+"): "+exceptionMessage);
//		Toast.makeText(context, exceptionMessage, Toast.LENGTH_SHORT).show();
		textView.setText( textView.getText()
				+exceptionMessage+"\n\n");
	}
	public static void showException(Exception exception, String suffix, TextView textView) {
		String exceptionMessage = getExceptionMessage(exception);
		Log.d("DianeRoboSampleSearchAsyncTask:82", "onException("+exception.getClass().getSimpleName()+"): "+exceptionMessage);
//		Toast.makeText(context,exceptionMessage+"\n\n"+suffix, Toast.LENGTH_SHORT).show();
		textView.setText( textView.getText()
				+exceptionMessage+"\n"+suffix+"\n\n");
	}
	public static void showException(Exception exception, TextView textView, UlyssesSearcher searcher) {
		showException(exception, textView);
		searcher.resetSearchState();
	}
	public static void showException(Exception exception, String suffix, TextView textView, UlyssesSearcher searcher) {
		showException(exception, suffix, textView);
		searcher.resetSearchState();
	}
	
	public static void showException(Exception exception, Context context) {
		String exceptionMessage = getExceptionMessage(exception);
		String m = context.getClass().getSimpleName()+": onException("+exception.getClass().getSimpleName()+"): "+exceptionMessage;
		Log.d("ExceptionUtils:56", m);
//		searcher.resetSearchState();
		Toast.makeText(context, exceptionMessage, Toast.LENGTH_SHORT).show();
	}	
	
	public static String getExceptionMessage(Exception e) {
		Throwable cause = e.getCause();
		String message = e.getMessage();
		if (cause!=null)
			message.concat("\ncaused from: "+cause.getMessage());
		return message;
	}
}
