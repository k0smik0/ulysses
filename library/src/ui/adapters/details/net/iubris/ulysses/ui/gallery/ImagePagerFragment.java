package net.iubris.ulysses.ui.gallery;

import java.util.List;

import net.iubris.ulysses.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ImagePagerFragment extends Fragment {
	
	public static final int INDEX = 2;
	private List<String> imagesUrl;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_details_gallery_street_view, container, false);
		ViewPager pager = (ViewPager) rootView.findViewById(R.id.gallery_pager);
		pager.setAdapter( new ImagePagerAdapter( getActivity(), imagesUrl ) );
		pager.setCurrentItem(0);
		return rootView;
	}
	
	public void setImagesUrl(List<String> imagesUrl) {
		this.imagesUrl = imagesUrl;
	}
}