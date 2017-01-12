package com.example.bojan.projekatpma.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.bojan.projekatpma.model.Category;
import com.example.bojan.projekatpma.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bojan on 1/6/2017.
 */

public class CategoryDataSource {

    MySQLHelperCategories dbhelper;
    SQLiteDatabase db;
    String[] cols = {MySQLHelperCategories.TITLE};

    public CategoryDataSource(Context context) {
        dbhelper = new MySQLHelperCategories(context);
    }

    public void open() throws SQLException {
        db = dbhelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void addCategory(Category category) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLHelperCategories.TITLE, category.getTitle());

        db.insert(MySQLHelperCategories.TABLE_NAME, null, contentValues);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        Cursor cursor = db.query(MySQLHelperCategories.TABLE_NAME, cols, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category c = cursorToCategory(cursor);
            categories.add(c);
            cursor.moveToNext();
        }
        cursor.close();
        return categories;
    }

    public void deleteCategory(Category category){
        db.delete(MySQLHelperCategories.TABLE_NAME, MySQLHelperCategories.TITLE + category.getTitle() + "'",null);
    }

    private Category cursorToCategory(Cursor cursor) {
        Category c = new Category();
        c.setTitle(cursor.getString(0));

        return c;
    }
}
