/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesSampleListActivity.java is part of 'Ulysses'.
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ulysses' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses_demo.activity.list;



import net.iubris.ulysses.model.Place;
import net.iubris.ulysses.model.comparators.PlaceComparatorByAscendingDistance;
import net.iubris.ulysses.model.comparators.PlaceComparatorByDiscendingRating;
import net.iubris.ulysses.search.utils.Buffer;
import net.iubris.ulysses.ui.fragments.map.MarkerShowable;
import net.iubris.ulysses.ui.fragments.tabspager.selectable.FragmentSelectable;
import net.iubris.ulysses.ui.list.adapter.PlacesListAdapter;
import net.iubris.ulysses.ui.toast.utils.UIUtils;
import net.iubris.ulysses_demo.R;
import net.iubris.ulysses_demo.activity.list.task.PopulateOnResumeAsyncTask;
import net.iubris.ulysses_demo.activity.list.task.UlyssesDemoSearchAndAdaptePopulaterAsyncTask;
import net.iubris.ulysses_demo.activity.main.UlyssesDemoActivity;
import roboguice.activity.RoboListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class UlyssesDemoListActivity extends RoboListActivity {
	
	private PlacesListAdapter placesAdapter;
	private UlyssesDemoSearchAndAdaptePopulaterAsyncTask searchAndAdapterPopulateAsyncTask;
	private PopulateOnResumeAsyncTask populateOnResumeAsyncTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//Ln.d("onCreate");
		super.onCreate(savedInstanceState);
		
//		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.list);
		final ListView listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setTextFilterEnabled(true);
		
//		locationsInjector.stopLocationsTest();
				
		placesAdapter = new PlacesListAdapter(this, R.layout.list_row, 
				new MarkerShowable() {
					@Override
					public void showMarker(String arg0) {}
				}, 
				new FragmentSelectable() {
					@Override
					public void setCurrentItem(int arg0, boolean arg1) {}
				},
				UlyssesDemoActivity.class, 
				new Buffer()
				
				)/* {
			
			@Override
			public View getView(final int position, View convertView, ViewGroup parent) {
				
				View view = super.getView(position, convertView, parent);
				
				OnClickListener ocl = new OnClickListener() {
					@Override
					public void onClick(View view) {
						PlaceHere ph = (PlaceHere) listView.getItemAtPosition(position);
						UIUtils.showShortToast( ph.getPlace().getName(), UlyssesSampleListActivity.this);
					}
				};
				
				view.setOnClickListener(ocl);
				return view;
			}
			@Override
			public boolean areAllItemsEnabled() {
				return true;
			}
			@Override
			public boolean isEnabled(int arg0) {
				return true;
			}
		}*/;
		listView.setAdapter(placesAdapter);
		placesAdapter.setNotifyOnChange(true);
		
		listView.setClickable(false);
		listView.setOnItemClickListener( new OnItemClickListener() {			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
				
//				UIUtils.showShortToast( position+" "+listView.isItemChecked(position), UlyssesSampleListActivity.this);

				Place ph = (Place) listView.getItemAtPosition(position);
				UIUtils.showShortToast( ph.getPlaceName(), UlyssesDemoListActivity.this);
				
//				view.setSelected(false);
			}			
		});

		searchAndAdapterPopulateAsyncTask = new UlyssesDemoSearchAndAdaptePopulaterAsyncTask(this, placesAdapter);
		populateOnResumeAsyncTask = new PopulateOnResumeAsyncTask(this, placesAdapter);
	}
	
	/*
	@Override
	protected void onListItemClick(ListView listView, View v, int position, long id) {
		
		UIUtils.showShortToast( position+" "+listView.isItemChecked(position), UlyssesSampleListActivity.this);
		
		PlaceHere ph = (PlaceHere) listView.getItemAtPosition(position);
		UIUtils.showShortToast( ph.getPlace().getName(), UlyssesSampleListActivity.this);
	}*/
	
	@Override
	protected void onResume() {
		super.onResume();
//		Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
		populateOnResumeAsyncTask.execute();
//		searchAndAdapterPopulateAsyncTask.execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add("REFRESH").setOnMenuItemClickListener( new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				searchAndAdapterPopulateAsyncTask.execute();
				return false;
			}} );
		
		final SubMenu sortSubMenu = menu.addSubMenu(R.string.menu__list_sort);
		sortSubMenu.add(R.string.menu__list_sort_distance).setOnMenuItemClickListener( new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem item) {				
				placesAdapter.sort( new PlaceComparatorByAscendingDistance());
//				getListView().invalidateViews();
				return false;
			}
		});
		sortSubMenu.add(R.string.menu__list_sort_rating).setOnMenuItemClickListener( new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem item) {				
				placesAdapter.sort( new PlaceComparatorByDiscendingRating() );
	//			getListView().invalidateViews();
				return false;
			}
		});
		return true;
	}
}
