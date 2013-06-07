package net.iubris.ulysses.ui.icons.filter;

import net.iubris.socrates.model.http.response.data.search.Place;
import android.graphics.Bitmap;

public interface Filter {
	boolean matchOn(Place place);
	Bitmap getBitmap();
}
