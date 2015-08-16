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
package net.iubris.voyager.activity.menu;




import java.util.Comparator;

import net.iubris.ulysses.R;
import net.iubris.ulysses.ui.intentable.Refreshable;
import net.iubris.ulysses.ui.intentable.Searchable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;


public class MenuUtilsLegacy {
	
	@SuppressLint("NewApi")
	public static MenuItem addSearch(Menu menu, final Searchable searchable, final Activity activity) {
		/* old semi-working
		MenuItem menuItem = menu.add(R.string.menu__search)
				.setIcon(R.drawable.ic_action_search_white);
		if (Build.VERSION.SDK_INT > 10)
			menuItem
				.setActionView(R.layout.collapsible_edittext)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT| MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		*/

		MenuItem menuItem = menu.add(R.string.menu__search);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			SearchView searchView = new SearchView(activity);
			menuItem
			.setIcon(R.drawable.ic_action_search_white)
//			.setActionView(R.layout.collapsible_edittext)
			.setActionView(searchView)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT| MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
			
			SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
//			SearchView searchView = (SearchView) menuItem.getActionView();
			searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));
			searchView.setIconifiedByDefault(false);
			searchView.setSubmitButtonEnabled(true);
						
			searchView.setOnQueryTextListener( new OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					searchable.search(query);
//					new Intent(Intent.ACTION_SEARCH).
					activity.onSearchRequested();
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
					
					activity.onSearchRequested();
					return false;
				}
			});
		}
		return menuItem;
	}
//	@SuppressLint("NewApi")
//	public static <RA extends Fragment & Searchable> MenuItem addSearch(Menu menu, Activity activity, RA fragment) {
//		
//	}
	
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
		public static <T, AscendingByDistanceComparator extends Comparator<T>, DiscendingByRatingComparator extends Comparator<T>> void addSort(Menu menu, Context context, final ArrayAdapter<T> placesAdapter, final AscendingByDistanceComparator ascendingByDistanceComparator, final DiscendingByRatingComparator discendingByRatingComparator) {			
			SubMenu sortSubMenu = menu.addSubMenu(0, Menu.NONE, 0, R.string.menu__list_sort);

			MenuItem sortMenuItem = sortSubMenu.getItem();
			
			sortMenuItem.setIcon(R.drawable.ic_action_sort_white);
			
			if (Build.VERSION.SDK_INT > 10) {
				sortMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			}

			sortSubMenu.add( context.getResources().getString(R.string.menu__list_sort_distance) )
				.setOnMenuItemClickListener( new OnMenuItemClickListener() {
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							placesAdapter.sort( ascendingByDistanceComparator);
							return false;
						}
				})
				.setIcon(android.R.drawable.ic_menu_sort_by_size);
			
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
