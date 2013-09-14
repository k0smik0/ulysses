#Ulysses 

Ulyssesis a location/network-aware [Google Place](https://developers.google.com/places/documentation/) Search


Why "Ulysses" ? Because Ulysses is latin name of Homer's Odyssey protagonist: 
a famous navigator, explorer, curious man.<br/>
He always knews his position, seeing stars and so on, so we could says he is "location-aware".<br/>
And he always was aware about his means of transport, its ships: so this is "network-aware" property.<br/>
And when he had land to any coast, in forced way or not, he always wanted to explore, searching for anything.<br/>  

And so Ulysses component, an android component for curious people, but (always?) awareness ;D  

---
####Technical:
Ulysses is an aggregation of [Diane](../../../diane) and [Socrates](../../../socrates):

 - from Diane inherits logics and engines for aware searching
 - from Socrates it uses Google Places search capability

So, Ulysses is just a particular instance of Diane, with (generics) parameter type as List&lt;PlaceHere&gt;:   
and "PlaceHere" is wrapper for Socrates's Place type, adding geolocation for that geoplace as its main field (this class structure is needed for efficient listactivity comparators)

---
See sample, it is really easy to understand.  
And this [video](http://www.youtube.com/watch?v=c05zFt-9Z90) show the sample at work.
--
and don't forget to change place api key with your own!

