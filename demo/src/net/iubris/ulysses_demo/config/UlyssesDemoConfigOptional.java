/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * UlyssesSampleConfigOptional.java is part of 'Ulysses'.
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
package net.iubris.ulysses_demo.config;


import java.util.Set;

import javax.inject.Singleton;

import net.iubris.socrates.model.http.response.data.search.PlaceType;
import net.iubris.ulysses.searcher.location.aware.network.delegate.config.DefaultSearchOptions;

@Singleton
public final class UlyssesDemoConfigOptional extends DefaultSearchOptions {
	
	@Override
	public Set<PlaceType> getTypes() {
		types.add(PlaceType.bakery);
		return types;
	}
	
	/*@Override
	public RankBy getRankBy() {
		return RankBy.distance;
	}*/
	@Override
	public Integer getRadius() {
		return 5000;
	}
}
