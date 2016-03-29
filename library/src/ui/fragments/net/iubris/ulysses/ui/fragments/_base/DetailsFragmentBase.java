package net.iubris.ulysses.ui.fragments._base;

import net.iubris.ulysses.model.Place;
import android.support.v4.app.Fragment;

public class DetailsFragmentBase extends Fragment {

	private Place place;
	
	public void setPlace(Place place) {
		this.place = place;
	}
	
	public Place getPlace() {
		return place;
	}
}
