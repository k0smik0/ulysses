/*******************************************************************************
 * Copyleft 2012 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesModule.java is part of 'Ulysses'.
 * 
 * 'Ulysses' is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * 'Ulysses' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with 'Ulysses' ; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses._roboguice.module;
import net.iubris.diane.searcher.locationaware.locator.Locator;
import net.iubris.kusor4diane.KLocator4Diane;
import net.iubris.ulysses.controller.delegates.cacheaware.IUlyssesCacheSearcherDelegate;
import net.iubris.ulysses.controller.delegates.cacheaware.RoboUlyssesCacheSearcherDelegate;
import net.iubris.ulysses.controller.delegates.locationaware.IUlyssesLocationSearcherDelegate;
import net.iubris.ulysses.controller.delegates.locationaware.RoboUlyssesLocationSearcherDelegate;
import net.iubris.ulysses.controller.delegates.locationaware.annotations.UlyssesDistanceMinimumThreshold;
import net.iubris.ulysses.controller.delegates.locationaware.annotations.UlyssesTimeMinimumThreshold;
import net.iubris.ulysses.controller.delegates.networkaware.IUlyssesNetworkSearcherDelegate;
import net.iubris.ulysses.controller.delegates.networkaware.RoboUlyssesNetworkSearcherDelegate;
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
		bindConstant().annotatedWith(UlyssesDistanceMinimumThreshold.class).to(distanceMinimumThreshold); // 1500 meters		
		bindConstant().annotatedWith(UlyssesTimeMinimumThreshold.class).to(timeMinimumThreshold); // 15 min
		
		//default implementation
		bind(Locator.class).to(KLocator4Diane.class);		
		bind(IUlyssesLocationSearcherDelegate.class).to(RoboUlyssesLocationSearcherDelegate.class);
		bind(IUlyssesNetworkSearcherDelegate.class).to(RoboUlyssesNetworkSearcherDelegate.class);
		bind(IUlyssesCacheSearcherDelegate.class).to(RoboUlyssesCacheSearcherDelegate.class);
	}
}
