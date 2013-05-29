package net.iubris.ulysses_sample.controller;

import javax.inject.Inject;

import net.iubris.diane.aware.location.state.three.base.DefaultThreeStateLocationAwareLocationSupplier;
import net.iubris.diane.aware.location.state.three.base.annotation.DianeDistanceMaximumThreshold;
import net.iubris.polaris.locator.provider.LocationProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class UlyssesSampleLocationSupplier extends DefaultThreeStateLocationAwareLocationSupplier {

	private Context context;

	@Inject
	public UlyssesSampleLocationSupplier(Context context, LocationProvider arg0, @DianeDistanceMaximumThreshold Integer arg1) {
		super(arg0, arg1);
		this.context = context;
		registerUpdates(context);
	}
	
	@Override
	protected void finalize() throws Throwable {
		unregisterUpdates(context);
		super.finalize();
	}
	
	public void registerUpdates(Context context){
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("net.iubris.ulysses_sample.NEW_LOCATION");
		context.registerReceiver(locationMockReceiver, intentFilter);
	}
	
	public void unregisterUpdates(Context context) {
		context.unregisterReceiver(locationMockReceiver);
	}
	
	public BroadcastReceiver locationMockReceiver = new BroadcastReceiver() {		
		@Override
		public void onReceive(Context context, Intent intent) {
			/*
			Location location = new Location("dummy");
			location.setLatitude(44.494692);
			location.setLongitude(11.342728);
			*/
			Location location = null;
			String klc = LocationManager.KEY_LOCATION_CHANGED;
			if (intent.hasExtra(klc)) {
				location = (Location) intent.getExtras().get(klc);
				Log.d("locationMockReceiver", "new location: "+location);
				Toast.makeText(context, "locationMockReceiver - new location: "+location, Toast.LENGTH_SHORT).show();
				UlyssesSampleLocationSupplier.this.updateFreshLocation(location);
			}
		}
	};
}
