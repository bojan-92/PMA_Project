package com.example.bojan.projekatpma.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bojan on 1/6/2017.
 */

public class MySQLHelperCategories extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "categories";
    public static final String ID_COL = "category_id";
    public static final String TITLE = "category_title";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "categories.db";
    private static final String DB_CREATE =
            "create table " + TABLE_NAME + "("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + TITLE + " TEXT); ";

    public MySQLHelperCategories(Context context) {
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
