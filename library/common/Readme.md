
### old readme - to update
Ulysses is a location/network-aware Place Search Framework
(Place refers to [GooglePlaces][1])

Why "Ulysses" ? Because Ulysses is latin name of Homer's Odyssey protagonist: 
a famous navigator, explorer, curious man.<br/>
He always knews his position, seeing stars and so on, so we could says he is "location-aware".<br/>
And he always was aware about his means of transport, its ships: so this is "network-aware" property.<br/>
And when he had land to any coast, in forced way or not, he always wanted to explore, searching for anything.<br/>  

And so Ulysses component, an android component for curious people, but (always?) awareness ;D 

usage (requires roboguice):
<pre>UlyssesSearcher us = Roboguice.getInjector(context).getInstance(UlyssesSearcher.class);
//or via: @Inject UlyssesSearcher us;

us.search(); 
 
List<PlaceHere> placesHere = us.getResult();</pre>

where PlaceHere is a class storing search result and distance from geolocation used:
<pre>
private PlaceHere {
	Place place; // google places 
	float distance; // distance from location used for search
	PlaceDetails placeDetails; // google place place details 
}</pre>
UlyssesSearcher implements various interfaces:  
1.    LocationUpdater (for a continuos location up2date)
2.    Searcher<StatusSearch,ResultSearch> (from [Diane framework](http://github.com/k0smik0/diane) )

Then, various delegates (implementing above interfaces) are used in order to retrieve best results belongs various "conditions";  
conditions can be "no network" and/or "old location" and/or "i have cache?" and so on:  
-    IUlyssesLocationSearcherDelegate obtains best location (or try to)  
-    IUlyssesNetworkSearcherDelegate retrieves results using networks (and of course best location, from IUlyssesLocationSearcherDelegate )   
-    IUlyssesCacheSearcherDelegate retrieves results from cache, if we don't have network connection

every interface have implementing concrete classes for end-user, simply called UlyssesNetworkSearcherDelegate or similar;  
really, UlyssesSearcher uses these 3 delegates for its stuffs.

a watch inside implementation:

every delegates interface extends others interfaces, combining all them together in order to provide a focused interface for particular job:  
this super-interfaces comes from another framework, [Diane](http://github.com/k0smik0/diane) which provides a set of interfaces and abstract 
classes for location and network searching.  
Diane is result-agnostic (all framework is generics based) VS Ulysses which is focused on GooglePlaces.  
So, Diane just provides algorithms for searching using last recent location (and to retrieve it) and/or using network.  
And finally: SocratesDelegate (a client using [Socrates](http://github.com/k0smik0/socrates)) is responsible to query googleplaces and retrieves result.   
It is network-not-aware, so it's called by UlyssesNetworkSearcherDelegate, which takes care of know if network are (not) available. 


In addition to backend client, there is also a ui part:  
PlacesHereListAdapter, which extends ArrayAdapter and provides an adapter for listactivity or listfragment.  
Its layout (list_row.xml) shows place name, icon (an image "place-type" retrieved from googleplaces, using [aquery](http://code.google.com/p/android-query/)),   
distance from actual location ("here"), and rating (also from googleplaces result)

[1]: https://developers.google.com/places/documentation/