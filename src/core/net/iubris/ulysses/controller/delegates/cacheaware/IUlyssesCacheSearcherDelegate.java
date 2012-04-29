package net.iubris.ulysses.controller.delegates.cacheaware;

import java.util.List;

import net.iubris.diane.searcher.cacheaware.CacheAwareByLocationSearcher;
import net.iubris.ulysses.model.PlaceHere;

public interface IUlyssesCacheSearcherDelegate extends CacheAwareByLocationSearcher<Void, List<PlaceHere>>{}
