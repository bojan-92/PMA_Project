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
 * Created by Bojan on 1/6/2017.
 */

public class CategoryDataSource extends SQLiteOpenHelper {

    MySQLHelperCategories dbhelper;
    SQLiteDatabase db;
    //String[] cols = {MySQLHelperCategories.TITLE};
    String[] cols = {MySQLHelperCategories.ID_COL,MySQLHelperCategories.TITLE};

    public CategoryDataSource(Context context) {
        super(context,MySQLHelperCategories.DB_NAME,null,MySQLHelperCategories.DB_VERSION);
        //dbhelper = new MySQLHelperCategories(context);
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

    public void addCategory(Category category) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLHelperCategories.TITLE, category.getTitle());
        db.insert(MySQLHelperCategories.TABLE_NAME, null, contentValues);
        db.close();
    }

    public ArrayList<Category> getAllCategories() {
        db = this.getReadableDatabase();
        Cursor cursor = db.query(MySQLHelperCategories.TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Category> categories = new ArrayList<Category>();
        Category category;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                category = new Category();
                category.setId(cursor.getString(0));
                category.setTitle(cursor.getString(1));

                categories.add(category);
            }
        }
        cursor.close();
        db.close();

        return categories;
    }

    public void deleteCategory(Category category){
        db = this.getReadableDatabase();
        db.delete(MySQLHelperCategories.TABLE_NAME,MySQLHelperCategories.ID_COL + " = ?",new String[]{category.getId()});
        db.close();
    }

    private Category cursorToCategory(Cursor cursor) {
        Category c = new Category();
        c.setTitle(cursor.getString(0));

        return c;
    }

    public void updateCategory(Category category){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLHelperCategories.TITLE,category.getTitle());
        db.update(MySQLHelperCategories.TABLE_NAME,contentValues,MySQLHelperCategories.ID_COL + " = ?"
                ,new String[]{category.getId()});
        db.close();
    }


}
