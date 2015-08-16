/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * Socrates4UlyssesModule.java is part of 'Ulysses'.
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
 * along with 'Ulysses'; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 ******************************************************************************/
package net.iubris.ulysses._di._roboguice.module;

import net.iubris.socrates._di._roboguice.modules.AbstractSocratesBaseModule;
import net.iubris.ulysses.engine.searcher.location.aware.network.delegate.DefaultSocratesDelegate;
import net.iubris.ulysses.engine.searcher.location.aware.network.delegate.SocratesDelegate;

import com.google.inject.AbstractModule;


public abstract class SocratesToUlyssesModule extends AbstractModule {

	
//private AbstractSocratesBaseModule abstractSocratesBaseModule;

//	@Override
//	protected void bindConfigs() {
//		bind(ConfigMandatory.class).toInstance(new ConfigMandatoryImpl());
//		bind(ConfigMandatoryImpl.class).asEagerSingleton();
//		bindConfigMandatory();

//		bind(ConfigOptional.class).annotatedWith(Config.class).toInstance(new ConfigOptionalImpl());
//		bind(ConfigOptionalImpl.class).asEagerSingleton();
//		bindConfigOptional();
//	}
	
//	protected abstract void bindConfigMandatory();
//	protected abstract void bindConfigOptional();
	
//	public SocratesToUlyssesModule(AbstractSocratesBaseModule abstractSocratesBaseModule) {
//		this.abstractSocratesBaseModule = abstractSocratesBaseModule;
//	}
	
	@Override
	protected void configure() {
		install( getSocratesBaseModule() );
//		install( abstractSocratesBaseModule);
		bindSocratesDelegate();
	}
	
	protected abstract AbstractSocratesBaseModule getSocratesBaseModule();

	protected void bindSocratesDelegate() {
		bind(SocratesDelegate.class).to(DefaultSocratesDelegate.class);
	}

}
