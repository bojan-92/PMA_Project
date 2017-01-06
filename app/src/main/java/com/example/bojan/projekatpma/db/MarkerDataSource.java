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

    MySQLHelperLocations dbhelper;
    SQLiteDatabase db;
    String[] cols = {MySQLHelperLocations.TITLE, MySQLHelperLocations.DESCRIPTION, MySQLHelperLocations.POSITION};

    public MarkerDataSource(Context context) {
        dbhelper = new MySQLHelperLocations(context);
    }

    public void open() throws SQLException {
        db = dbhelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void addMarker(Marker marker) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLHelperLocations.TITLE, marker.getTitle());
        contentValues.put(MySQLHelperLocations.DESCRIPTION, marker.getDescription());
        contentValues.put(MySQLHelperLocations.POSITION, marker.getPosition());

        db.insert(MySQLHelperLocations.TABLE_NAME, null, contentValues);
    }

    public List<Marker> getAllMarkers() {
        List<Marker> markers = new ArrayList<>();
        Cursor cursor = db.query(MySQLHelperLocations.TABLE_NAME, cols, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Marker m = cursorToMarker(cursor);
            markers.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return markers;
    }

    public void deleteMarker(Marker marker){
        db.delete(MySQLHelperLocations.TABLE_NAME, MySQLHelperLocations.POSITION + " = '" + marker.getPosition() + "'",null);
    }

    private Marker cursorToMarker(Cursor cursor) {
        Marker m = new Marker();
        m.setTitle(cursor.getString(0));
        m.setDescription(cursor.getString(1));
        m.setPosition(cursor.getString(2));

        return m;
    }
}
