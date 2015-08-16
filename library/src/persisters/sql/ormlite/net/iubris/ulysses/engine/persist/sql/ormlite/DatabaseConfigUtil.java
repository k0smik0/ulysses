package net.iubris.ulysses.engine.persist.sql.ormlite;

import java.io.IOException;
import java.sql.SQLException;

import net.iubris.ulysses.model.Place;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Database helper class used to manage the creation and upgrading of your database. 
 * This class also usually provides the DAOs used by the other classes.
 * @author Original taken from the ormlite.com
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	public static final Class<?>[] CLASSES = new Class[]{
        Place.class
	};
	
	public static void main(String[] args) throws SQLException, IOException {
		writeConfigFile("ormlite_config.txt", CLASSES);
	}
}
