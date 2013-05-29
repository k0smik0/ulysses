package net.iubris.ulysses_sample.activity.main;


import javax.inject.Inject;

import net.iubris.diane_library__test_utils.injector.MockGpsLocationsInjector;
import net.iubris.polaris.locator.updater.LocationUpdater;
import net.iubris.ulysses_sample.R;
import net.iubris.ulysses_sample.activity.list.UlyssesSampleListActivity;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UlyssesSampleActivity extends RoboActivity {

	
	@Inject private MockGpsLocationsInjector locationsInjector;
	@Inject private LocationUpdater locationUpdater;
	
//	@Inject private LocationProvider locationProvider;
	
	@InjectView(R.id.button_search) Button buttonSearch;
	@InjectView(R.id.button_list) Button buttonList;
	@InjectView(R.id.button_updates) Button buttonInject;
	@Inject UsingUlyssesAsyncTask usingUlyssesAsyncTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_activity);
		
		locationsInjector.setLocationInjectionInterval(1);
		locationsInjector.startLocationsTest();

		buttonSearch.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				usingUlyssesAsyncTask.execute();
			};
		});
		buttonList.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				locationsInjector.stopLocationsTest();
				Intent intent = new Intent(UlyssesSampleActivity.this,UlyssesSampleListActivity.class);
				startActivity(intent);
			}
		});
		buttonInject.setOnClickListener( new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (locationsInjector.isRunning()) {
					locationsInjector.stopLocationsTest();
					buttonInject.setText("Start Fake Locations");
				} else {
					locationsInjector.startLocationsTest();
					buttonInject.setText("Stop Fake Locations");
				}
			}
		});
		
		locationUpdater.startLocationUpdates();
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		usingUlyssesAsyncTask.execute();
	}
	
	
	
	@Override
	protected void onStop() {
		try {
		locationUpdater.stopLocationUpdates();
		} catch (IllegalArgumentException e) {
			Log.d(this.getClass().getSimpleName()+":70",e.getMessage());
		}
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		locationsInjector.stopLocationsTest();
		super.onDestroy();
	}
	
}
