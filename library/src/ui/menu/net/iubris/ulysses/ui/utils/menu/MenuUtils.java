/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * MenuUtilsLegacy.java is part of 'Ratafia'.
 * 
 * 'Ratafia' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ratafia' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with 'Ratafia'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses.ui.utils.menu;


import net.iubris.apollus2.ui.activity._base.Refreshable;
import net.iubris.apollus2.ui.activity._base.Searchable;
import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Location;
import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.model.comparators.PlaceComparatorByAscendingAlphabetic;
import net.iubris.ulysses.model.comparators.PlaceComparatorByAscendingDistance;
import net.iubris.ulysses.model.comparators.PlaceComparatorByDiscendingRating;
import net.iubris.ulysses.ui.activity.details.StreetViewPanoramaActivity;
import net.iubris.ulysses.ui.fragments.details.DetailsFragmentGallery;
import net.iubris.ulysses.ui.intentable.IntentUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;


public class MenuUtils {
	
	public static <SA extends Activity & Searchable> MenuItem addSearchNew(Menu menu, final SA searchableActivity, String query) {
//		Inflate the menu; this adds items to the action bar if it is present.
		searchableActivity.getMenuInflater().inflate(R.menu.search, menu);

		MenuItem searchMenuItem = menu.findItem(R.id.action_search);
		searchMenuItem.expandActionView();

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) searchableActivity.getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager.getSearchableInfo(searchableActivity.getComponentName()));
		// searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

		searchView.setQuery(query, false);

		// Evito che compaia la tastiera quando vengono visualizzati i risultati della ricerca
		searchView.clearFocus();

		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String arg0) {
				// Nel momento in cui viene eseguita una nuova ricerca, i filtri vengono resettati
//				filterActivated = false;
//				for (int i = 0; i < categoryFilters.size(); i++) {
//					categoryFilters.get(i).resetFilter();
//				}
				// Evito che compaia la tastiera quando viene eseguita una nuova ricerca
				searchView.clearFocus();
				return false;
			}
			@Override
			public boolean onQueryTextChange(String arg0) {
				// filterActivated = false;
				return false;
			}
		});

		MenuItemCompat.setOnActionExpandListener(searchMenuItem, new android.support.v4.view.MenuItemCompat.OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem arg0) {
				return true; // true if the item should expand
			}
			@Override
			public boolean onMenuItemActionCollapse(MenuItem arg0) {
				searchableActivity.finish();
				return false; // false if collapsing should be suppressed
			}
		});
		return searchMenuItem;
	}
	
	public static MenuItem addSearchAction(Menu menu, Activity activity) {
		activity.getMenuInflater().inflate(R.menu.main, menu);
        
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) 
//        		menu.findItem(R.id.action_search).getActionView();
//        		(SearchView)searchMenuItem.getActionView();
        		MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String arg0) {
				if (searchMenuItem != null)
	                searchMenuItem.collapseActionView();
				// false to let the SearchView perform the default action
				return false;
			}
			@Override
			public boolean onQueryTextChange(String arg0) {
				// false if the SearchView should perform the default action
				// of showing any suggestions if available
				return false;
			}
		});
        
        return searchMenuItem;
	}
	
	@SuppressLint("NewApi")
	public static <SA extends Activity & Searchable> MenuItem addSearch(Menu menu, final SA searchableActivity/*, final Activity activity*/) {
		/*
//		 old semi-working
//		MenuItem menuItem = menu.add(R.string.menu__search)
//				.setIcon(R.drawable.ic_action_search_white);
//		if (Build.VERSION.SDK_INT > 10)
//			menuItem
//				.setActionView(R.layout.collapsible_edittext)
//				.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT| MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		

		MenuItem menuItem = menu.add(R.string.menu__search);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			SearchView searchView = new SearchView(searchableActivity);
//			changeSearchTextColor(searchView);
			
			menuItem
				.setIcon(R.drawable.ic_action_search_white)
	//			.setActionView(R.layout.collapsible_edittext)
				.setActionView(searchView)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT| MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			
			SearchManager searchManager = (SearchManager) searchableActivity.getSystemService(Context.SEARCH_SERVICE);
//			SearchView searchView = (SearchView) menuItem.getActionView();
			searchView.setSearchableInfo(searchManager.getSearchableInfo(searchableActivity.getComponentName()));
			searchView.setIconifiedByDefault(false);
			searchView.setSubmitButtonEnabled(true);
						
			searchView.setOnQueryTextListener( new OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					searchableActivity.search(query);
//					new Intent(Intent.ACTION_SEARCH).
					searchableActivity.onSearchRequested();
					return false;
				}
				@Override
				public boolean onQueryTextChange(String newText) {
					return false;
				}
			});
		} else {
			menuItem
				.setIcon(android.R.drawable.ic_menu_search)
				.setOnMenuItemClickListener( new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
	//Log.d("MenuUtilsLegacy:64","onMenuItemClick");
						searchableActivity.onSearchRequested();
						return false;
					}
				});
		}
		return menuItem;*/
		return net.iubris.apollus2.ui.fragments.tabspager.activity.MenuUtils.addSearch(menu, searchableActivity);
	}
	/*
	@SuppressLint("NewApi")
	private static void changeSearchTextColor(SearchView searchView) {
		
//		searchView.setQueryHint("Type something...");
		Resources resources = searchView.getContext().getResources();
        int searchPlateId = resources.getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate!=null) {
//            searchPlate.setBackgroundColor(Color.DKGRAY);
            int searchTextId = resources.getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
//            	int color = resources.getString(R.string.searchview_text_color);
//	            searchText.setTextColor(color);
//	            searchText.setHintTextColor(Color.WHITE);
            }
        }
	}*/

	
	@SuppressLint({ "NewApi", "InlinedApi" })
	public static <SA extends Activity & Searchable> MenuItem addSimpleSearch(Menu menu, final SA searchableActivity) {
		MenuItem menuItem = menu.add(R.string.menu__search)	.setIcon(R.drawable.ic_action_search_white);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			menuItem
				.setActionView(R.layout.collapsible_edittext)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT| MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}
		menuItem.setOnMenuItemClickListener( new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				return false;
			}
		});
		menuItem.setOnActionExpandListener( new OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				return false;
			}
			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				return false;
			}
		});
		/*searchView.setOnQueryTextListener( new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				searchableActivity.search(query);
//				new Intent(Intent.ACTION_SEARCH).
//				searchableActivity.onSearchRequested();
				return false;
			}
			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		}*/
		return menuItem;
		
	}
	
	/*public static class Refresh {
		public static class Activity {
			@SuppressLint("NewApi")
			static <RA extends Activity & Refreshable> void addRefresh(Menu menu, final RA refreshableActivity) {
				_addRefresh(menu, refreshableActivity);
			}
		}
		public static class Fragment {
			@SuppressLint("NewApi")
			static <RF extends Fragment & Refreshable> void addRefresh(Menu menu, final RF refreshableFragment) {
				_addRefresh(menu, refreshableFragment);
			}
		}
	}*/
		
	@SuppressLint("NewApi")
	public static void addRefresh(Menu menu, final Refreshable refreshable) {
		MenuItem menuItem = menu.add(R.string.menu__refresh);
		
		menuItem
			.setOnMenuItemClickListener( new OnMenuItemClickListener() {			
				@Override
				public boolean onMenuItemClick(MenuItem item) {				
					refreshable.refresh();				
					return false;
				}
			});
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			menuItem
				.setIcon(R.drawable.ic_action_refresh_white)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		else
			menuItem
				.setIcon(android.R.drawable.ic_menu_rotate);
	}
	
	@SuppressLint("NewApi")
	public static void addPanorama(Menu menu, final Location location, final Activity activity) {
		MenuItem menuItem = menu.add("Panorama");
		menuItem.setOnMenuItemClickListener( new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				Intent intent = new Intent();
		    	intent.putExtra(DetailsFragmentGallery.EXTRA_ULYSSES_LOCATION, location);
		    	intent.setClass(activity, StreetViewPanoramaActivity.class);
		    	activity.startActivity(intent);
		    	activity.overridePendingTransition(R.anim.flip_from, R.anim.flip_to);
				
				return false;
			}
		});
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}
		
//		menuItem.setIcon(R.drawable.ic_action_maps_pin_drop);
		menuItem.setIcon(R.mipmap.ic_camera360);
	}
	
	@SuppressLint("NewApi")
	public static void addCall(Menu menu, final String internationalPhoneNumber, final Activity activity) {
		MenuItem menuItem = menu.add(R.string.menu__call).setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						IntentUtils.call(internationalPhoneNumber, activity);
						return false;
					}
				});
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			menuItem
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		menuItem.setIcon(R.drawable.ic_action_maps_local_phone);
	}
	
	@SuppressLint("NewApi")
	public static void addGPlus(Menu menu, final String gplusURi, final Activity activity) {
		MenuItem menuItem = menu.add(R.string.menu__gplus).setOnMenuItemClickListener(
				new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Intent i = new Intent(Intent.ACTION_VIEW);
						i.setData(Uri.parse(gplusURi));
						activity.startActivity(i);
						return false;
					}
				});
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			menuItem
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		menuItem.setIcon(R.drawable.common_signin_btn_icon_normal_dark);
	}

	
	
//	public static void addInfo(Menu menu) {
//		MenuItem menuItem = menu.add(R.string.menu__about);
//				menuItem.setIcon(android.R.drawable.ic_dialog_info)
//	}
	
	public static class List {
		/*public static class Activity {
			@SuppressLint("NewApi")
			public static <T, AscendingByDistanceComparator extends Comparator<T>, DiscendingByRatingComparator extends Comparator<T>> void addSort(Menu menu, Context context, final ArrayAdapter<T> placesAdapter, final AscendingByDistanceComparator ascendingByDistanceComparator, final DiscendingByRatingComparator discendingByRatingComparator) {}
		}
		public static class Fragment {
			@SuppressLint("NewApi")
			public static <T, AscendingByDistanceComparator extends Comparator<T>, DiscendingByRatingComparator extends Comparator<T>> void addSort(Menu menu, Context context, final ArrayAdapter<T> placesAdapter, final AscendingByDistanceComparator ascendingByDistanceComparator, final DiscendingByRatingComparator discendingByRatingComparator) {}
		}*/
		@SuppressLint("NewApi")
		public static /*<T, AscendingByDistanceComparator extends Comparator<T>, 
			DiscendingByRatingComparator extends Comparator<T>>*/ 
		void addSort(Menu menu, Context context, final ArrayAdapter<Place> placesAdapter, 
				final PlaceComparatorByAscendingDistance ascendingByDistanceComparator, 
				final PlaceComparatorByDiscendingRating discendingByRatingComparator) {			
			SubMenu sortSubMenu = menu.addSubMenu(0, Menu.NONE, 0, R.string.menu__list_sort);

			MenuItem sortMenuItem = sortSubMenu.getItem();
			
			sortMenuItem.setIcon(R.drawable.ic_action_sort_white);
			
			if (Build.VERSION.SDK_INT > 10) {
				sortMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			}
			
			sortSubMenu.add( context.getResources().getString(R.string.menu__list_sort_name) )
				.setOnMenuItemClickListener( new OnMenuItemClickListener() {
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							placesAdapter.sort( new PlaceComparatorByAscendingAlphabetic() );
							return false;
						}
				})
				.setIcon(android.R.drawable.ic_menu_sort_by_size);

			sortSubMenu.add( context.getResources().getString(R.string.menu__list_sort_distance) )
				.setOnMenuItemClickListener( new OnMenuItemClickListener() {
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							placesAdapter.sort( ascendingByDistanceComparator);
							return false;
						}
				})
				.setIcon(android.R.drawable.ic_menu_sort_by_size);
			
			if (discendingByRatingComparator.isExistingRating())
				sortSubMenu.add( context.getResources().getString(R.string.menu__list_sort_rating) )
					.setOnMenuItemClickListener( new OnMenuItemClickListener() {
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							placesAdapter.sort( discendingByRatingComparator );
							return false;
						}
					})
					.setIcon(android.R.drawable.star_off);
		}
	}
	
	public static class Map {
		
		/*@SuppressLint("NewApi")
		public static <A extends Activity, MLO extends MyLocationOverlay> void addHere(Menu menu, final A activity,
				final MLO myLocationOverlay,
				final MapView mapView) {
			MenuItem menuItem = menu.add(R.string.actionbar_here)
				.setIcon(R.drawable.ic_action_here_white)
				
				.setOnMenuItemClickListener( new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
Ln.d(myLocationOverlay.getLastFix());
					final GeoPoint center = myLocationOverlay.getMyLocation();
					if (center != null) {
						mapView.getController().animateTo(center);
						mapView.getController().setCenter( center );
						mapView.getController().setZoom(16);
					} else {
						Ln.d("center is null");
					}
					return false;
				}
			});
			if (Build.VERSION.SDK_INT > 10)
				menuItem
					.setActionView(R.layout.collapsible_edittext)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}*/
	}
	
}
