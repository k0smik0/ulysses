package net.iubris.ulysses_sample._roboguice.module;

import net.iubris.diane_library__test_utils._roboguice.module.DianeTestUtilModule;
import net.iubris.socrates.config.ConfigMandatory;
import net.iubris.socrates.config.ConfigOptional;
import net.iubris.socrates.engines.search.url.annotation.Config;
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
		install( new UlyssesUiModule() );
		
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
			protected void bindConfigOptional() {
				bind(ConfigOptional.class).annotatedWith(Config.class).to(UlyssesSampleConfigOptional.class);
			}
		};
	}
	
	
	// we bind, but really don't use
	/*protected KusorModule getKusorModule() {
		return new KusorModule("net.iubris.ulysses_sample.ACTIVE_LOCATION_UPDATE_ACTION", 
				5*60*1000, 50);
	}*/

}
