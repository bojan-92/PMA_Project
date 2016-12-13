package com.example.bojan.projekatpma.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.bojan.projekatpma.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bojan on 12/11/2016.
 */

public class MarkerDataSource {

    MySQLHelper dbhelper;
    SQLiteDatabase db;
    String[] cols = {MySQLHelper.TITLE, MySQLHelper.DESCRIPTION, MySQLHelper.POSITION};

    public MarkerDataSource(Context context) {
        dbhelper = new MySQLHelper(context);
    }

    public void open() throws SQLException {
        db = dbhelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void addMarker(Marker marker) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLHelper.TITLE, marker.getTitle());
        contentValues.put(MySQLHelper.DESCRIPTION, marker.getDescription());
        contentValues.put(MySQLHelper.POSITION, marker.getPosition());

        db.insert(MySQLHelper.TABLE_NAME, null, contentValues);
    }

    public List<Marker> getAllMarkers() {
        List<Marker> markers = new ArrayList<>();
        Cursor cursor = db.query(MySQLHelper.TABLE_NAME, cols, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Marker m = cursorToMarker(cursor);
            markers.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return markers;
    }

    private Marker cursorToMarker(Cursor cursor) {
        Marker m = new Marker();
        m.setTitle(cursor.getString(0));
        m.setDescription(cursor.getString(1));
        m.setPosition(cursor.getString(2));

        return m;
    }
}
