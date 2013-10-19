/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesSampleActivity.java is part of 'Ulysses'.
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
package net.iubris.ulysses_demo.activity.main;

import javax.inject.Inject;

import net.iubris.diane_library__test_utils.injector.MockGpsLocationsInjector;
import net.iubris.polaris.locator.updater.LocationUpdater;
import net.iubris.ulysses_demo.activity.list.UlyssesDemoListActivity;
import net.iubris.ulysses_demo.activity.main.task.UsingUlyssesAsyncTask;
import net.iubris.ulysses_demo.R;
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
public class UlyssesDemoActivity extends RoboActivity {
	
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
//Debug.startMethodTracing(Environment.getExternalStorageDirectory().getPath()+"/traces/ulysses__startup");
		super.onCreate(savedInstanceState);
		
		locationsInjector.setLocationInjectionInterval(5);
//		locationsInjector.startLocationsTest();
		
		textView.setMovementMethod(new ScrollingMovementMethod());
		
		usingUlyssesAsyncTask.setButtonToHandler(buttonList);
		usingUlyssesAsyncTask.setTextViewToHandler(textView);
		
		locationUpdater.startLocationUpdates();
	};
	@Override
	protected void onResume() {
		super.onResume();
//Debug.stopMethodTracing();
//		if (buttonList.isClickable()) buttonList.setClickable(false);
		usingUlyssesAsyncTask.execute();
	}
	
	public void onClickSearch(View v) {
		usingUlyssesAsyncTask.execute();
	};
	public void onClickList(View v) {
		stopLocationsTest();
		Intent intent = new Intent(UlyssesDemoActivity.this,UlyssesDemoListActivity.class);
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
	protected void onStop() {
		try {
			locationUpdater.stopLocationUpdates();
		} catch (IllegalArgumentException e) {
			Log.d(this.getClass().getSimpleName()+":104",e.getMessage());
		}
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		stopLocationsTest();
		super.onDestroy();
	}
	
	private void stopLocationsTest() {
		try {
			if (locationsInjector.isRunning())
				locationsInjector.stopLocationsTest();
		} catch (IllegalArgumentException e) {
			Log.d(this.getClass().getSimpleName()+":120",e.getMessage());
		}
	}
	
}
