package com.example.multitech.auto_estoque.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DB_NAME = "auto_stock";
    public static final String PRODUCT = "product";


    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + PRODUCT
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT  NOT NULL, " + " stock INTEGER NOT NULL, " + " value DOUBLE NOT NULL); ";

        try{
            db.execSQL(sql);
        }catch(Exception err){
            Log.e("AUTO STOCK", err.getMessage() );
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
