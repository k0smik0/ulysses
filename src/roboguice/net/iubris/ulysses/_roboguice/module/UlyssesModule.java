package net.iubris.ulysses._roboguice.module;
import net.iubris.ulysses._roboguice.progressdialog.annotations.ProgressDialogForGetPlace;
import net.iubris.ulysses._roboguice.progressdialog.providers.ProgressDialogProviderGetPlace;
import net.iubris.ulysses.controller.delegates.cacheaware.IUlyssesCacheSearcherDelegate;
import net.iubris.ulysses.controller.delegates.cacheaware.UlyssesCacheSearcherDelegate;
import net.iubris.ulysses.controller.delegates.locationaware.IUlyssesLocationSearcherDelegate;
import net.iubris.ulysses.controller.delegates.locationaware.UlyssesLocationSearcherDelegate;
import net.iubris.ulysses.controller.delegates.locationaware.annotations.UlyssesDistanceMinimumThreshold;
import net.iubris.ulysses.controller.delegates.locationaware.annotations.UlyssesTimeMinimumThreshold;
import net.iubris.ulysses.controller.delegates.networkaware.IUlyssesNetworkSearcherDelegate;
import net.iubris.ulysses.controller.delegates.networkaware.UlyssesNetworkSearcherDelegate;

import android.app.ProgressDialog;

import com.google.inject.AbstractModule;


public class UlyssesModule extends AbstractModule {
	
	private final int distanceMinimumThreshold;
	private final long timeMinimumThreshold;
	
	public UlyssesModule(int distanceMinimumThreshold, long timeMinimumThreshold) {
		this.distanceMinimumThreshold = distanceMinimumThreshold;
		this.timeMinimumThreshold = timeMinimumThreshold;
	}

	public UlyssesModule() {
		distanceMinimumThreshold = 0;
		timeMinimumThreshold = 0;
	}
	

	@Override
	protected void configure() {		
		//bind(ProgressDialogProvider.class).annotatedWith(ProgressDialogProviderForGetPlace.class).to(ProgressDialogProviderGetPlace.class);
		bind(ProgressDialog.class).annotatedWith(ProgressDialogForGetPlace.class).toProvider(ProgressDialogProviderGetPlace.class);
		
		bindConstant().annotatedWith(UlyssesDistanceMinimumThreshold.class).to(distanceMinimumThreshold); // 1500 meters		
		bindConstant().annotatedWith(UlyssesTimeMinimumThreshold.class).to(timeMinimumThreshold); // 15 min
		
		
		//default implementation
		bind(IUlyssesLocationSearcherDelegate.class).to(UlyssesLocationSearcherDelegate.class);
		bind(IUlyssesNetworkSearcherDelegate.class).to(UlyssesNetworkSearcherDelegate.class);
		bind(IUlyssesCacheSearcherDelegate.class).to(UlyssesCacheSearcherDelegate.class);
	}

}
