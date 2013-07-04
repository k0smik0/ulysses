package net.iubris.ulysses_sample.activity.main.task;

import android.content.Context;
import android.widget.Toast;

public class Utils {

	public static void showException(Exception exception, Context context) {
		String exceptionMessage = getExceptionMessage(exception);
		String m = context.getClass().getSimpleName()+": onException("+exception.getClass().getSimpleName()+"): "+exceptionMessage;
//		Log.d("Utils:11", m);
		Toast.makeText(context, exceptionMessage, Toast.LENGTH_SHORT).show();
	}
	/*private void showException(Exception exception,String suffix) {
		String exceptionMessage = getExceptionMessage(exception);
		Log.d("UsingUlyssesAsyncTask:91", "onException("+exception.getClass().getSimpleName()+"): "+exceptionMessage);
		Toast.makeText(context,exceptionMessage+"\n\n"+suffix, Toast.LENGTH_SHORT).show();
	}*/
	private static String getExceptionMessage(Exception e) {
		Throwable cause = e.getCause();
		String message = e.getMessage();
		if (cause!=null)
			message.concat("\ncaused from: "+cause.getMessage());
		return message;
	}
}
