package net.iubris.ulysses.controller;

import java.util.List;

import net.iubris.diane.searcher.Searcher;
import net.iubris.ulysses.model.PlaceHere;

public interface IUlyssesSearcher extends Searcher<Void, List<PlaceHere>> {}
