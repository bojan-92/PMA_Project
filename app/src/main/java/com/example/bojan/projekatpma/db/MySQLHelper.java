package com.example.bojan.projekatpma.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bojan on 12/11/2016.
 */

public class MySQLHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "locations";
    public static final String ID_COL = "location_id";
    public static final String TITLE = "location_title";
    public static final String SNIPPET = "location_snippet";
    public static final String POSITION = "location_position";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "markerlocations.db";
    private static final String DB_CREATE =
            "create table " + TABLE_NAME + "("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TITLE + " TEXT, "
                    + SNIPPET + " TEXT, "
                    + POSITION + " TEXT);";

    public MySQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
}
