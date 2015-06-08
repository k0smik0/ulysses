/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesSampleModule.java is part of 'Ulysses'.
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
package net.iubris.ulysses_demo._roboguice.module;

import net.iubris.diane_library__test_utils._roboguice.module.DianeTestUtilModule;
import net.iubris.socrates.config.ConfigMandatory;
import net.iubris.socrates.config.SearchOptions;
import net.iubris.ulysses._di._roboguice.module.AbstractUlyssesModule;
import net.iubris.ulysses._di._roboguice.module.SocratesToUlyssesModule;
import net.iubris.ulysses.engine.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses.ui._di._roboguice.module.AbstractUlyssesUiModule;
import net.iubris.ulysses.ui._di.progressdialog.search.annotations.ProgressDialogForSearchPlacesString;
import net.iubris.ulysses_demo.config.UlyssesDemoConfigMandatory;
import net.iubris.ulysses_demo.config.UlyssesDemoConfigOptional;
import android.graphics.drawable.Drawable;

public class UlyssesDemoModule extends AbstractUlyssesModule {
	
	
	@Override
	protected void configure() {
		super.configure();
		install( new AbstractUlyssesUiModule() {
			
			@Override
			protected Drawable provideDefaultDrawable() {
				return null;
			}
			
			@Override
			protected void bind_String_AnnotatedWith_ProgressDialogForSearchPlacesString() {
				bind(String.class).annotatedWith(ProgressDialogForSearchPlacesString.class).toInstance("Searching");
			}
		} );
		
		bind(UlyssesSearcher.class).asEagerSingleton();
	}
	
	@Override
	protected void bindLocationProvider() {
		install( new DianeTestUtilModule() );
	};
	
	/*@Override
	protected void bindThreeStateLocationAwareLocationSupplier() {
		bind(ThreeStateLocationAwareLocationSupplier.class).to(UlyssesSampleLocationSupplier.class);
	}*/
	
	@Override
	protected SocratesToUlyssesModule getSocratesToUlyssesModule() {
		return new SocratesToUlyssesModule() {
			@Override
			protected void bindConfigMandatory() {
//				bind(ConfigMandatory.class).toInstance(new UlyssesSampleConfigMandatory());
				bind(ConfigMandatory.class).to(UlyssesDemoConfigMandatory.class);
			}
			@Override
			protected void bindSearchOptions() {
				bind(SearchOptions.class)/*.annotatedWith(Config.class)*/.to(UlyssesDemoConfigOptional.class);
			}
		};
	}
	
	
	// we bind, but really don't use
	/*protected KusorModule getKusorModule() {
		return new KusorModule("net.iubris.ulysses_sample.ACTIVE_LOCATION_UPDATE_ACTION", 
				5*60*1000, 50);
	}*/

}
