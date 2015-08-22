package net.iubris.ulysses.ui.fragments.tabspager.activity;

import net.iubris.ulysses.search.intentable.SearchTypable;
import net.iubris.ulysses.ui.activity._base.Locatable;
import net.iubris.ulysses.ui.fragments._base.Updatable;
import net.iubris.ulysses.ui.fragments.list.Markerable;
import net.iubris.ulysses.ui.fragments.map.MarkerShowable;
import net.iubris.ulysses.ui.fragments.tabspager.TabsPagerAdapter;
import net.iubris.ulysses.ui.fragments.tabspager.selectable.FragmentSelectableViewPager;
import net.iubris.ulysses.ui.fragments.tabspager.sliding.SlidingTabLayout;
import roboguice.activity.RoboFragmentActivity;
import roboguice.util.Ln;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;

import com.google.android.gms.maps.SupportMapFragment;


public abstract class ListMapTabsSearchTypableLocatableActivity
<ListFragmentMarkerable extends ListFragment & Markerable & Updatable, 
MarkerShowableMapFragment extends SupportMapFragment & MarkerShowable & Updatable> 
extends RoboFragmentActivity
implements SearchTypable, Locatable {

	private FragmentSelectableViewPager fragmentSelectableViewPager;
	private SlidingTabLayout slidingTabLayout;
	private TabsPagerAdapter tabsPageAdapter;
	
	protected ListFragmentMarkerable listFragment;
	protected MarkerShowableMapFragment mapFragment;
	
	protected static int LIST_FRAGMENT_INDEX = 0;
	protected static int MAP_FRAGMENT_INDEX = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResourceId()/* R.layout.activity_results*/);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		Ln.d("onStart");

		FragmentManager fm = getSupportFragmentManager();
		
		mapFragment = getMarkerShowableMapFragment(fm);
		doSomethingWithMapFragment(mapFragment);
		// TODO RESTORE
//        mapFragment.setTitle( resources.getString(R.string.fragment_map_title) );
//        mapFragment.setProgressBarToolbar(progressBarToolbar);
        
		fragmentSelectableViewPager = (FragmentSelectableViewPager) findViewById(getViewPagerResourceId()/*R.id.viewpager*/);
		listFragment = getListFragmentMarkerable(fm);
//		TODO RESTORE
//        listFragment.setTitle( resources.getString(R.string.fragment_list_title) );
        listFragment.setMarkerShowable(mapFragment, fragmentSelectableViewPager);
        doSomethingWithListFragment(listFragment);
        
        if (tabsPageAdapter==null) {
        	tabsPageAdapter = new TabsPagerAdapter( getSupportFragmentManager(), listFragment, mapFragment );
        }
        
        fragmentSelectableViewPager.setAdapter(tabsPageAdapter);
        
        slidingTabLayout = (SlidingTabLayout) findViewById(getSlidingTabsResourceId()/*R.id.sliding_tabs*/);
        slidingTabLayout.setViewPager(fragmentSelectableViewPager);
	}
	
	private void doSomethingWithListFragment(ListFragmentMarkerable listFragment) {}

	protected void doSomethingWithMapFragment(MarkerShowableMapFragment mapFragment) {}

	protected abstract int getLayoutResourceId();
	protected abstract int getViewPagerResourceId();
	protected abstract int getSlidingTabsResourceId();
	
	@SuppressWarnings("unchecked")
	public void refreshResults() {
		if(listFragment == null) {
			listFragment = (ListFragmentMarkerable) tabsPageAdapter.getItem( LIST_FRAGMENT_INDEX );
		} 
		listFragment.updateData();
		
		if(mapFragment == null) {
			mapFragment = (MarkerShowableMapFragment) tabsPageAdapter.getItem( MAP_FRAGMENT_INDEX );
		}
		mapFragment.updateData();
	}
	
	protected abstract ListFragmentMarkerable getListFragmentMarkerable(FragmentManager fragmentManager);
	protected abstract MarkerShowableMapFragment getMarkerShowableMapFragment(FragmentManager fragmentManager);
	
	@Override
	public abstract Location getLocation();
}
