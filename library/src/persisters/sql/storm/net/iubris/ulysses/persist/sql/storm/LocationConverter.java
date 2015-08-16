package net.iubris.ulysses.persist.sql.storm;

import javax.persistence.Converter;

import net.iubris.ulysses.model.Location;

@Converter(autoApply=true)
public class LocationConverter extends BaseAttributeConverter<Location> {

	@Override
	protected Location destringify(String locationAsString) {
		String[] split = locationAsString.split(SEPARATOR);
		return new Location(Double.parseDouble( split[0] ), Double.parseDouble( split[1] ));
	}
	@Override
	protected String stringify(Location location) {
		return location.getLatitude()+SEPARATOR+location.getLongitude();
	}

}
