/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * ImageAdapter.java is part of 'Ulysses'.
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
