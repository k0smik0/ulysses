package net.iubris.ulysses.persist.sql.storm;

import android.content.Context;

import com.turbomanage.storm.DatabaseHelper;
import com.turbomanage.storm.api.Database;
import com.turbomanage.storm.api.DatabaseFactory;

/**
 * Created by galex on 11/06/14.
 */

@Database(name = UlyssesDatabaseHelper.DB_NAME, version = UlyssesDatabaseHelper.DB_VERSION)
public class UlyssesDatabaseHelper extends DatabaseHelper{

    public final static String DB_NAME = "ulysses_child";
    public final static int DB_VERSION = 1;

    public UlyssesDatabaseHelper(Context ctx, DatabaseFactory dbFactory) {
        super(ctx, dbFactory);
    }

    @Override
    public UpgradeStrategy getUpgradeStrategy() {
        return UpgradeStrategy.BACKUP_RESTORE;
    }
}
