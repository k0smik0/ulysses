package net.iubris.ulysses_sample.config;


import java.util.Set;

import javax.inject.Singleton;

import net.iubris.socrates.model.http.response.data.search.PlaceType;
import net.iubris.ulysses.searcher.location.aware.network.delegate.config.DefaultSearchOptions;

@Singleton
public final class UlyssesSampleConfigOptional extends DefaultSearchOptions {
	
	@Override
	public Set<PlaceType> getTypes() {
		types.add(PlaceType.liquor_store);
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