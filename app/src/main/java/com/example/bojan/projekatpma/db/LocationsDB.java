package com.example.bojan.projekatpma.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bojan on 12/11/2016.
 */

public class LocationsDB extends SQLiteOpenHelper {

    /** Database name */
    private static final String DB_NAME = "markers.db";

    /** Version of database */
    private static final int VERSION = 1;

    /** id field */
    public static final String FIELD_MARKER_ID = "_id";

    /** latitude field */
    public static final String FIELD_MARKER_LATITUDE = "_latitude";

    /** longitude field */
    public static final String FIELD_MARKER_LONGITUDE = "_longitude";

    /** table name */
    public static final String DATABASE_TABLE = "locations";

    public static final String DB_CREATE = "CREATE TABLE " + DATABASE_TABLE + " ( " +
            FIELD_MARKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            FIELD_MARKER_LONGITUDE + " TEXT , " +
            FIELD_MARKER_LATITUDE + " TEXT , " +
            " ) ";

    public LocationsDB(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(sqLiteDatabase == null){
            sqLiteDatabase.execSQL(DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }
}
