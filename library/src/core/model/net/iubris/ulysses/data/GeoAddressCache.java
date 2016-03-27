package net.iubris.ulysses.data;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.util.Ln;
import net.iubris.ulysses.model.GeoAddress;
import net.iubris.ulysses.model.Location;
import net.iubris.ulysses.persist.Persister;

@Singleton
public class GeoAddressCache {
	
	private final ConcurrentSkipListMap<Location, GeoAddress> cache = new ConcurrentSkipListMap<>();
	
	private final Persister persister;
	
	@Inject
	public GeoAddressCache(Persister persister) {
		this.persister = persister;
		
//		Ln.d("cache: "+cache.hashCode());
		
		init();
	}

	public void init() {
		if (cache.size()==0) {
			Collection<GeoAddress> geoAddresses = persister.getGeoAddresses();
			Ln.d("Populating cache with "+geoAddresses.size()+" geoAddresses");
			for (GeoAddress geoAddress : geoAddresses) {
				Ln.d("geoAddress:"+geoAddress);
			}
			for (GeoAddress geoAddress : geoAddresses) {
				cache.put(geoAddress.getLocation(), geoAddress);
			}
		}
	}

	// always overwritten
	public GeoAddress save(GeoAddress geoAddress) {
		Ln.d("putting: "+geoAddress);
		GeoAddress geoAddressMaybeExistant = cache.put(geoAddress.getLocation(), geoAddress);
		persister.saveGeoAddress(geoAddress);
		return geoAddressMaybeExistant;
	}
	public GeoAddress find(Location location) {
		Ln.d("finding by: "+location+" within:");
		for (Entry<Location, GeoAddress> entry: cache.entrySet()) {
			Ln.d("location:"+entry.getKey()+", geoAddress:"+entry.getValue());
		}
		GeoAddress geoAddress = cache.get(location);
		if (geoAddress!=null) {
			Ln.d("found geoAddress:"+geoAddress);
		}
		return geoAddress;
	}
}
