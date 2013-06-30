package net.iubris.ulysses_sample._roboguice.module;

import android.graphics.drawable.Drawable;
import net.iubris.diane_library__test_utils._roboguice.module.DianeTestUtilModule;
import net.iubris.socrates.config.ConfigMandatory;
import net.iubris.socrates.config.SearchOptions;
import net.iubris.ulysses._inject.progressdialog.annotations.search.ProgressDialogForSearchPlacesString;
import net.iubris.ulysses._roboguice.module.AbstractUlyssesModule;
import net.iubris.ulysses._roboguice.module.Socrates4UlyssesModule;
import net.iubris.ulysses._roboguice.module.UlyssesUiModule;
import net.iubris.ulysses.searcher.aware.full.UlyssesSearcher;
import net.iubris.ulysses_sample.config.UlyssesSampleConfigMandatory;
import net.iubris.ulysses_sample.config.UlyssesSampleConfigOptional;

public class UlyssesSampleModule extends AbstractUlyssesModule {
	
	
	@Override
	protected void configure() {
		super.configure();
		install( new UlyssesUiModule() {
			
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
		/*bind(LocationProvider.class).to(MockLocator.class);
		bind(LocationUpdater.class).to(MockLocator.class);*/
		install( new DianeTestUtilModule() );
	};
	
	/*@Override
	protected void bindThreeStateLocationAwareLocationSupplier() {
		bind(ThreeStateLocationAwareLocationSupplier.class).to(UlyssesSampleLocationSupplier.class);
	}*/
	
	@Override
	protected Socrates4UlyssesModule getSocrates4UlyssesModule() {
		return new Socrates4UlyssesModule() {
			@Override
			protected void bindConfigMandatory() {
//				bind(ConfigMandatory.class).toInstance(new UlyssesSampleConfigMandatory());
				bind(ConfigMandatory.class).to(UlyssesSampleConfigMandatory.class);
			}
			@Override
			protected void bindSearchOptions() {
				bind(SearchOptions.class)/*.annotatedWith(Config.class)*/.to(UlyssesSampleConfigOptional.class);
			}
		};
	}
	
	
	// we bind, but really don't use
	/*protected KusorModule getKusorModule() {
		return new KusorModule("net.iubris.ulysses_sample.ACTIVE_LOCATION_UPDATE_ACTION", 
				5*60*1000, 50);
	}*/

}
