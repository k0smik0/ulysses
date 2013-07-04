/*******************************************************************************
 * Copyleft 2013 Massimiliano Leone - massimiliano.leone@iubris.net .
 * 
 * DefaultSearchOptions.java is part of 'Ulysses'.
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
package net.iubris.ulysses.searcher.location.aware.network.delegate.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.iubris.socrates.config.SearchOptions;
import net.iubris.socrates.model.http.request.url.language.Language;
import net.iubris.socrates.model.http.request.url.parameters.optional.search.values.RankBy;
import net.iubris.socrates.model.http.response.data.search.PlaceType;

public class DefaultSearchOptions implements SearchOptions {
	protected final HashSet<PlaceType> types;
	
	public DefaultSearchOptions() {
//		System.out.println("ConfigOptionalImpl: "+this.hashCode());
		types = new HashSet<PlaceType>();
	}

	/**
	 * @return an empty HashSet, which you have to populate implementing abstract addTypes() method
	 */
	@Override
	public Set<PlaceType> getTypes() {
//		hashSet.add(PlaceType.bar);
//		hashSet.add(PlaceType.cafe);
//		addTypes();
		return types;
	}
//	protected abstract void addTypes();

	/**
	 * @return Collections.emptyList()
	 */
	@Override
	public List<String> getNames() {				
		return Collections.emptyList();
	}

	/**
	 * @return 5000 (meters)
	 */
	@Override
	public Integer getRadius() {
		// 5000 meters
		return 5000;
	}
	
	/**
	 * @return RankBy.prominence
	 */
	@Override
	public RankBy getRankBy() {
		//return RankBy.distance;
		return RankBy.prominence;
	}
	@Override
	public String getKeyword() {
		return "";
	}
	/**
	 * @return Language.italian
	 */
	@Override
	public Language getLanguage() {
		return /*Language.italian*/ null;
	}
}
