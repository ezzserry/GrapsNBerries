package awstreams.serry.serry.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Serry on 7/8/2017.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "AppDatabase"; // we will add the .db extension

    public static final int VERSION = 1;
}
