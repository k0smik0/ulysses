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