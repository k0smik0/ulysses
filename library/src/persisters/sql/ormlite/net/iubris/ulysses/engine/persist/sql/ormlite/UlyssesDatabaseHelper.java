package net.iubris.ulysses.engine.persist.sql.ormlite;

import java.sql.SQLException;

import net.iubris.ulysses.R;
import net.iubris.ulysses.model.Place;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your database. 
 * This class also usually provides the DAOs used by the other classes.
 * @author Original taken from the ormlite.com
 */
public class UlyssesDatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "ormlite.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the SimpleData table
	private RuntimeExceptionDao<Place, Integer> placeDao = null;

	public UlyssesDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, 
				R.raw.ormlite_config
//				0
				);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable 
	 * statements here to create the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(UlyssesDatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Place.class);
		} catch (SQLException e) {
			Log.e(UlyssesDatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		throw new RuntimeException("No versions; upgrade code not written yet");
	}

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. 
	 * Actually returns a RuntimeExceptionDao which maps Exceptions to RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Place, Integer> getPlaceDao() {
		if (placeDao == null) {
			placeDao = getRuntimeExceptionDao(Place.class);
		}
		return placeDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		placeDao = null;
	}
}
