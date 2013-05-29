package net.iubris.ulysses_sample.activity.list;

import net.iubris.ulysses.fragment.list.adapter.PlacesHereListAdapter;
import net.iubris.ulysses.fragment.list.adapter.asynctask.AdapterPopulaterAsyncTask;
import net.iubris.ulysses.model.comparators.PlaceComparatorByAscendingDistance;
import net.iubris.ulysses.model.comparators.PlaceComparatorByDiscendingRating;
import net.iubris.ulysses_sample.R;
import roboguice.activity.RoboListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.widget.ListView;
import android.widget.Toast;

public class UlyssesSampleListActivity extends RoboListActivity {
	
	private PlacesHereListAdapter placesAdapter;
	private AdapterPopulaterAsyncTask adapterPopulaterAsyncTask;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//Ln.d("onCreate");
		super.onCreate(savedInstanceState);
		
//		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.list);
		ListView listView = getListView();
//		listView.setTextFilterEnabled(true);
		
//		locationsInjector.stopLocationsTest();
				
		placesAdapter = new PlacesHereListAdapter(this, R.layout.list_row);
		listView.setAdapter(placesAdapter);
		
		placesAdapter.setNotifyOnChange(true);
		adapterPopulaterAsyncTask = new UlyssesSampleAdapterPopulaterAsyncTask(this, placesAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
		adapterPopulaterAsyncTask.execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//Ln.d("creating menu");
		//printItems();
		super.onCreateOptionsMenu(menu);
		
		menu.add(R.string.actionbar_refresh).setOnMenuItemClickListener( new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				adapterPopulaterAsyncTask.execute();
				return false;
			}} );
		
		final SubMenu sortSubMenu = menu.addSubMenu(R.string.list_menu_sort);
		sortSubMenu.add(R.string.list_submenu_order_distance).setOnMenuItemClickListener( new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem item) {				
				placesAdapter.sort( new PlaceComparatorByAscendingDistance());
//				getListView().invalidateViews();
				return false;
			}
		});
		sortSubMenu.add(R.string.list_submenu_order_rating).setOnMenuItemClickListener( new OnMenuItemClickListener() {			
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
