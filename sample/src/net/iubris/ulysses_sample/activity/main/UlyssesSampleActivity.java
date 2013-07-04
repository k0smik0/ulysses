package net.iubris.ulysses_sample.activity.main;

import javax.inject.Inject;

import net.iubris.diane_library__test_utils.injector.MockGpsLocationsInjector;
import net.iubris.polaris.locator.updater.LocationUpdater;
import net.iubris.ulysses_sample.R;
import net.iubris.ulysses_sample.activity.list.UlyssesSampleListActivity;
import net.iubris.ulysses_sample.activity.main.task.UsingUlyssesAsyncTask;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@ContentView(R.layout.sample_activity)
public class UlyssesSampleActivity extends RoboActivity {
	
	@Inject private MockGpsLocationsInjector locationsInjector;
	@Inject private LocationUpdater locationUpdater;
	
//	@Inject private LocationProvider locationProvider;
	
	@InjectView(R.id.button_search) Button buttonSearch;
	@InjectView(R.id.button_list) Button buttonList;
	@InjectView(R.id.button_updates) Button buttonInject;
	@Inject UsingUlyssesAsyncTask usingUlyssesAsyncTask;
//	@Inject UsingSimpleRoboAsyncTask usingUlyssesAsyncTask;
	
	@InjectView(R.id.text_field_result) TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		locationsInjector.setLocationInjectionInterval(5);
//		locationsInjector.startLocationsTest();
		
		textView.setMovementMethod(new ScrollingMovementMethod());
		
		usingUlyssesAsyncTask.setButtonToHandler(buttonList);
		usingUlyssesAsyncTask.setTextViewToHandler(textView);
		
		locationUpdater.startLocationUpdates();
	};
	
	public void onClickSearch(View v) {
		usingUlyssesAsyncTask.execute();
	};
	public void onClickList(View v) {
		locationsInjector.stopLocationsTest();
		Intent intent = new Intent(UlyssesSampleActivity.this,UlyssesSampleListActivity.class);
		startActivity(intent);
	}
	public void onClickFakeLocations(View v) {
		if (locationsInjector.isRunning()) {
			locationsInjector.stopLocationsTest();
			buttonInject.setText("Start Fake Locations");
		} else {
			locationsInjector.startLocationsTest();
			buttonInject.setText("Stop Fake Locations");
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		if (buttonList.isClickable()) buttonList.setClickable(false);
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
