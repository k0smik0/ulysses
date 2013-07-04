package net.iubris.ulysses_sample.activity.list;



import net.iubris.ulysses.list.adapter.PlacesHereListAdapter;
import net.iubris.ulysses.model.comparators.PlaceComparatorByAscendingDistance;
import net.iubris.ulysses.model.comparators.PlaceComparatorByDiscendingRating;
import net.iubris.ulysses_sample.R;
import net.iubris.ulysses_sample.activity.list.task.PopulateOnResumeAsyncTask;
import net.iubris.ulysses_sample.activity.list.task.UlyssesSampleSearchAndAdaptePopulaterAsyncTask;
import roboguice.activity.RoboListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.widget.ListView;

public class UlyssesSampleListActivity extends RoboListActivity {
	
	private PlacesHereListAdapter placesAdapter;
	private UlyssesSampleSearchAndAdaptePopulaterAsyncTask searchAndAdapterPopulateAsyncTask;
	private PopulateOnResumeAsyncTask populateOnResumeAsyncTask;

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
		searchAndAdapterPopulateAsyncTask = new UlyssesSampleSearchAndAdaptePopulaterAsyncTask(this, placesAdapter);
		populateOnResumeAsyncTask = new PopulateOnResumeAsyncTask(this, placesAdapter);
	}
	
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
