package com.example.multitech.auto_estoque.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.multitech.auto_estoque.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final SQLiteDatabase write;
    private final SQLiteDatabase read;

    public ProductDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        this.write = dbHelper.getWritableDatabase();
        this.read = dbHelper.getReadableDatabase();
    }

    public void create(Product product){
        ContentValues cv = new ContentValues();
        cv.put("name", product.getName());
        cv.put("stock", product.getStock());
        cv.put("value", product.getValue());

        try{
            write.insert(DbHelper.PRODUCT, null, cv);
            //write.close();
        }catch (Exception err){
            Log.e("AUTO STOCK", err.getMessage());
        }
    }

    public void update(Product product){
        ContentValues cv = new ContentValues();
        cv.put("name", product.getName());
        cv.put("stock", product.getStock());
        cv.put("value", product.getValue());

        try{
            String where = "id=? ";
            String[] args = {String.valueOf(product.getId())};
            write.update(DbHelper.PRODUCT, cv, where, args);
            //write.close();
        }catch (Exception err){
            Log.e("AUTO STOCK", err.getMessage());
        }
    }

    public void delete(Product product){
        try{
            String[] args = {String.valueOf(product.getId())};
            String where = "id=?";
            write.delete(DbHelper.PRODUCT, where, args);
        }catch(Exception err){
            Log.e("AUTOSTOCK", err.getMessage() );
        }
    }

    public  List<Product> getAll(){
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM " + DbHelper.PRODUCT + " ;";
        Cursor cursor = read.rawQuery(sql, null);

        while(cursor.moveToNext()){
           @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
           @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
           @SuppressLint("Range") int stock = cursor.getInt(cursor.getColumnIndex("stock"));
           @SuppressLint("Range") double value = cursor.getDouble(cursor.getColumnIndex("value"));

           Product product  = new Product();
//           product.setId(id);
           product.setName(name);
           product.setStock(stock);
           product.setValue(value);

           products.add(product);

        }

        return products;
    }
}
