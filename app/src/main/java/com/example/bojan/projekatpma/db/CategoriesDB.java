package com.example.bojan.projekatpma.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bojan on 1/6/2017.
 */

public class CategoriesDB extends SQLiteOpenHelper {

    /**
     * Database name
     */
    private static final String DB_NAME = "categories.db";

    /**
     * Version of database
     */
    private static final int VERSION = 1;

    /**
     * id field
     */
    public static final String FIELD_CATEGORY_ID = "_id";

    /**
     * title field
     */
    public static final String FIELD_CATEGORY_TITLE = "_title";

    /**
     * table name
     */
    public static final String DATABASE_TABLE = "categories";

    public static final String DB_CREATE = "CREATE TABLE " + DATABASE_TABLE + " ( " +
            FIELD_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            FIELD_CATEGORY_TITLE + " TEXT , " +
            " ) ";

    public CategoriesDB(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (sqLiteDatabase == null) {
            sqLiteDatabase.execSQL(DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }
}
