package com.example.bojan.projekatpma.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bojan.projekatpma.model.Category;
import com.example.bojan.projekatpma.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bojan on 12/11/2016.
 */

public class MarkerDataSource extends SQLiteOpenHelper {

    MySQLHelperLocations dbhelper;
    SQLiteDatabase db;
    String[] cols = {MySQLHelperLocations.ID_COL,MySQLHelperLocations.TITLE, MySQLHelperLocations.DESCRIPTION,
            MySQLHelperLocations.POSITION,MySQLHelperLocations.CATEGORY};

    public MarkerDataSource(Context context) {
        super(context, MySQLHelperLocations.DB_NAME, null, MySQLHelperLocations.DB_VERSION);
    }

    public void open() throws SQLException {
        db = dbhelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addMarker(Marker marker) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLHelperLocations.TITLE, marker.getTitle());
        contentValues.put(MySQLHelperLocations.DESCRIPTION, marker.getDescription());
        contentValues.put(MySQLHelperLocations.POSITION, marker.getPosition());
        contentValues.put(MySQLHelperLocations.CATEGORY,marker.getCategory());

        db.insert(MySQLHelperLocations.TABLE_NAME, null, contentValues);
        db.close();
    }

    public List<Marker> getAllMarkers() {
        db = this.getReadableDatabase();
        Cursor cursor = db.query(MySQLHelperLocations.TABLE_NAME, null, null, null, null, null, null);
        List<Marker> markers = new ArrayList<>();
        Marker marker;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                marker = new Marker();
                marker.setId(cursor.getString(0));
                marker.setTitle(cursor.getString(1));
                marker.setDescription(cursor.getString(2));
                marker.setPosition(cursor.getString(3));
                /*marker.setCategory(cursor.getString(4));*/
                markers.add(marker);
            }
        }
        cursor.close();
        db.close();

        return markers;
    }

    public void deleteMarker(Marker marker){
       /* db = this.getReadableDatabase();
        db.delete(MySQLHelperLocations.TABLE_NAME,MySQLHelperLocations.ID_COL + " = ?",new String[]{marker.getId()});
        db.close()*/;
        db = this.getReadableDatabase();
        db.delete(MySQLHelperLocations.TABLE_NAME, MySQLHelperLocations.POSITION + " = '" + marker.getPosition() + "'",null);
        db.close();
    }

    private Marker cursorToMarker(Cursor cursor) {
        Marker m = new Marker();
        m.setTitle(cursor.getString(0));
        m.setDescription(cursor.getString(1));
        m.setPosition(cursor.getString(2));
        m.setCategory(cursor.getString(3));
        return m;
    }

    public void updateMarker(Marker marker) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLHelperLocations.TITLE, marker.getTitle());
        contentValues.put(MySQLHelperLocations.DESCRIPTION, marker.getDescription());
        contentValues.put(MySQLHelperLocations.POSITION, marker.getPosition());
        contentValues.put(MySQLHelperLocations.CATEGORY,marker.getCategory());
        db.update(MySQLHelperLocations.TABLE_NAME,contentValues,MySQLHelperLocations.POSITION + " = '" + marker.getPosition() + "'",null);
        db.close();
    }

}
