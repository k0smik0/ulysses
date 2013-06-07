package net.iubris.ulysses._inject.ui.icons.providers;

import javax.inject.Inject;
import javax.inject.Provider;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

abstract public class AbstractMapBitmapProvider implements Provider<Bitmap> {
	
	@Inject protected Resources resources;

	protected Bitmap getBitmap(int resID) {
		return BitmapFactory.decodeResource(resources,resID);
	}
}
