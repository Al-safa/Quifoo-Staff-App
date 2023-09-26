package com.example.quifoostaffapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;



public class DatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "Food.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "dishes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DISH_NAME= "dish_name";
    private static final String COLUMN_DISH_PRICE= "dish_price";
    private static final String COLUMN_DISH_IMAGE = "dish_image";
    private static final String COLUMN_DISH_CATEGORY = "dish_category";


     DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         String query = "CREATE TABLE " + TABLE_NAME +
                 " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 COLUMN_DISH_NAME + " TEXT, " +
                 COLUMN_DISH_PRICE + " INTEGER, " +
                 COLUMN_DISH_CATEGORY + " TEXT, " +
                 COLUMN_DISH_IMAGE + " TEXT);";
         db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);

    }

    void addDish(String name, int price, String category){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues cv = new ContentValues();

         cv.put(COLUMN_DISH_NAME, name);
         cv.put(COLUMN_DISH_PRICE, price);
         cv.put(COLUMN_DISH_CATEGORY, category);
         long result = db.insert(TABLE_NAME, null, cv);
         if(result == -1){
             Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
         }else {
             Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
         }

    }

    Cursor readAllData()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String name, String price, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DISH_NAME, name);
        cv.put(COLUMN_DISH_PRICE, price);
        cv.put(COLUMN_DISH_CATEGORY, category);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1)
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
            
    }

    void deleteOneRow(String row_id){
         SQLiteDatabase db = this.getWritableDatabase();
         long result = db.delete(TABLE_NAME, " _id=?", new String[]{row_id});
        if(result == -1)
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
    }
}
