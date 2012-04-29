Ulysses is a location/network-aware Place Search Framework
[Place refers to GooglePlaces]

Why "Ulysses" ? Because Ulysses is latin name of Homer's Odyssey protagonist. 
A famous navigator, explorer, curious man.
He always knews his position, seeing stars and so on, so we could says he is "location-aware".
And he always was aware about his means of transport, its ships: so this is "network-aware" property.
And when he had land to any coast, in forced way or not, he always wanted to explore, searching for anything.  

And so Ulysses component, an android component for curious people, but (always?) awareness ;D 

usage (requires roboguice):

UlyssesSearcher us = Roboguice.getInjector(context).getInstance(UlyssesSearcher.class);
[or: @Inject UlyssesSearcher us;]

us.search() 
 
List<PlaceHere> placesHere = us.getSearchResult();

where PlaceHere is class storing search result and distance from geolocation used:

private PlaceHere {
	Place place; // google places 
	float distance; // distance from location used for search
	PlaceDetails placeDetails; // google place place details 
}

UlyssesSearcher implements various interfaces:
- LocationUpdater (for a continuos location up2date)
- Searcher<StatusSearch,ResultSearch> (from diane framework http://github.com/k0smik0/diane )

Then, UlyssesSearcher uses various delegates in order to retrieve best results belongs various "conditions":
conditions can be "no network" and/or "old location" and/or "i have cache?" and so on:
IUlyssesLocationSearcherDelegate obtains best location (or try to)
IUlyssesNetworkSearcherDelegate retrieves results using networks (and of course best location, from IUlyssesLocationSearcherDelegate ) 
IUlyssesCacheSearcherDelegate retrieves results from cache, if we don't have network connection

for every interface there are concrete classes for end-user, simply called UlyssesNetworkSearcherDelegate or similar;
really, UlyssesSearcher uses these 3 delegates for its stuffs.

a watch inside implementation:

every delegates interface extends others interfaces, combining all them together in order to provide a focused interface for particular job:
this super-interfaces comes from another framework, diane (http://github.com/k0smik0/diane ), which provides a set of interfaces and abstract 
classes for location and network searching.
Diane is result-agnostic (all framework is generics based) VS ulysses which is focused on googleplaces
So, Diane "only" provides algorithms for searching using last recent location (and to retrieve it) and/or using network
finally: SocratesDelegate (from http://github.com/k0smik0/socrates) is responsible to query googleplaces and retrieves result. 
It is network-not-aware, so it's called by UlyssesNetworkSearcherDelegate,
which takes care of know if network are (not) available. 


In addition to "client" UlyssesSearcher and various delegates, there is also a ui part:
PlacesHereListAdapter, which extends ArrayAdapter, and provides an adapter for listactivity or listfragment.
Its layout (list_row.xml) shows place name, icon (an image "place-type" retrieved from googleplaces, using aquery [ http://code.google.com/p/android-query/ ]), 
distance from actual location ("here"), and rating (also from googleplaces result)

