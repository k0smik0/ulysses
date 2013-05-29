package net.iubris.ulysses_sample.config;


import javax.inject.Singleton;

import net.iubris.socrates.model.http.request.url.parameters.optional.search.values.RankBy;
import net.iubris.socrates.model.http.response.data.search.PlaceType;
import net.iubris.ulysses.searcher.location.aware.network.delegate.config.AbstractConfigOptional;

@Singleton
public final class UlyssesSampleConfigOptional extends AbstractConfigOptional {
	@Override
	protected void addTypes() {
		types.add(PlaceType.liquor_store);
	}
	@Override
	public RankBy getRankBy() {
		return RankBy.distance;
	}
}