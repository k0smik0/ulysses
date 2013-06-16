package net.iubris.ulysses.ui.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class ImageViewFactory implements ViewFactory{

	private final Context context;
	
	public ImageViewFactory(Context context) {
		this.context = context;
	}

	@Override
	public View makeView() {
		ImageView imageView = new ImageView(context);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		imageView.setBackgroundColor(0xFF000000);
		return imageView;
	}
}
