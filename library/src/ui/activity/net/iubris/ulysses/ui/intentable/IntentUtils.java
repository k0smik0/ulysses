/*******************************************************************************

 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ActionUtils.java is part of 'Ratafia'.
 ******************************************************************************/
package net.iubris.ulysses.ui.intentable;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {
	
	public static void call(String internationalPhoneNumber, Context context) {
		String uri = "tel:" + internationalPhoneNumber;
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse(uri));
		context.startActivity(intent);
	}
}
