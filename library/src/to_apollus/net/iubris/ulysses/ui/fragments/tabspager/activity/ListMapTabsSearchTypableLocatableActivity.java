package net.iubris.ulysses.ui.fragments.tabspager.activity;

import net.iubris.diane.searcher.exceptions.SearchException;
import net.iubris.ulysses.R;
import net.iubris.ulysses.search.activity.intentable.SearchTypable;
import net.iubris.ulysses.tasks.search.localized.SearchLocalizedTask;
import net.iubris.ulysses.ui.activity._base.Locatable;
import net.iubris.ulysses.ui.activity._base.Searchable;
import net.iubris.ulysses.ui.fragments._base.Updatable;
import net.iubris.ulysses.ui.fragments.list._base.Markerable;
import net.iubris.ulysses.ui.fragments.map._base.MarkerShowable;
import net.iubris.ulysses.ui.fragments.tabspager.TabsPagerAdapter;
import net.iubris.ulysses.ui.fragments.tabspager.selectable.FragmentSelectableViewPager;
import net.iubris.ulysses.ui.fragments.tabspager.sliding.SlidingTabLayout;
import roboguice.activity.RoboFragmentActivity;
import roboguice.util.Ln;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;


public abstract class ListMapTabsSearchTypableLocatableActivity
<ListFragmentMarkerable extends ListFragment & Markerable & Updatable, 
MarkerShowableMapFragment extends SupportMapFragment & MarkerShowable & Updatable> 
extends RoboFragmentActivity
implements SearchTypable, Locatable, Searchable {
	
	private FragmentSelectableViewPager fragmentSelectableViewPager;
	private SlidingTabLayout slidingTabLayout;
	private TabsPagerAdapter tabsPageAdapter;
	
	protected ListFragmentMarkerable listFragment;
	protected MarkerShowableMapFragment mapFragment;
	
	private String query;
	private boolean savedSearch;
	
	protected static int LIST_FRAGMENT_INDEX = 0;
	protected static int MAP_FRAGMENT_INDEX = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(/*getLayoutResourceId()*/ R.layout.activity__search_results);
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

//	protected abstract int getLayoutResourceId();
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
	
	@Override 
	protected void onNewIntent(Intent intent) {
		super.setIntent(intent);
		handleIntent(intent);
	}
	
	private void handleIntent(Intent intent) {
		// old
//		if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
//			this.query = intent.getStringExtra(SearchManager.QUERY);
//			search(query);
//		}
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			if(savedSearch == false) {	
				this.query = intent.getStringExtra(SearchManager.QUERY);
			} else {
				this.savedSearch = false;
			}	      
	      // Avvio l'operazione di ricerca in background
	      search(this.query);
	    }
	}
	
	@Override
	public void search(String query) {
		try {
//			searchByAddressTask.execute(query);
			getSearchLocalizedTask().execute(query);
		} catch (SearchException e) {
			Toast.makeText(this, "search error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
//		setSearchType(SearchType.LOCALIZED);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);

		MenuItem searchMenuItem = menu.findItem(R.id.action_search);
		searchMenuItem.expandActionView();

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		// searchView.setIconifiedByDefault(false); // Do not iconify the
		// widget; expand it by default

		searchView.setQuery(this.query, false);

		// Evito che compaia la tastiera quando vengono visualizzati i risultati della ricerca
		searchView.clearFocus();

		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String arg0) {
				// Evito che compaia la tastiera quando viene eseguita una nuova ricerca
				searchView.clearFocus();
				return false;
			}
			@Override
			public boolean onQueryTextChange(String arg0) {
				return false;
			}
		});

		MenuItemCompat.setOnActionExpandListener(searchMenuItem, new OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem arg0) {
				return true; // true if the item should expand
			}
			@Override
			public boolean onMenuItemActionCollapse(MenuItem arg0) {
				finish();
				return false; // false if collapsing should be suppressed
			}
		});
		return true;
	}
	
	protected abstract ListFragmentMarkerable getListFragmentMarkerable(FragmentManager fragmentManager);
	protected abstract MarkerShowableMapFragment getMarkerShowableMapFragment(FragmentManager fragmentManager);
	protected abstract SearchLocalizedTask getSearchLocalizedTask();
	
	@Override
	public abstract Location getLocation();
}
