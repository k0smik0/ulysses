package net.iubris.ulysses.ui.gallery;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context context;
	private Bitmap[] pics;

	public ImageAdapter(Context context) {
		this.context = context;
	}
	
	public void setPics(Bitmap[] pics) {
		this.pics = pics;
	}

	@Override
	public int getCount() {
		return pics.length;
	}
	
	@Override
	public Object getItem(int arg0) {
	return pics[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		ImageView iView = new ImageView(context);
		iView.setImageBitmap( pics[position] );
//         iView.setImageResource(pics[arg0]);
		iView.setScaleType(ImageView.ScaleType.FIT_XY);
		iView.setLayoutParams(new Gallery.LayoutParams(150, 150));
		return iView;
	}
}