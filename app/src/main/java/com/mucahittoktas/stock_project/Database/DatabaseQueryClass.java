package com.mucahittoktas.stock_project.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.mucahittoktas.stock_project.Features.CreateStock.Stock;
import com.mucahittoktas.stock_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseQueryClass {

    private Context context;

    public DatabaseQueryClass(Context context){
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertStock(Stock stock){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STOCK_NAME, stock.getName());
        contentValues.put(Config.COLUMN_STOCK_BARCODE, stock.getBarcode());
        contentValues.put(Config.COLUMN_STOCK_QUANTITY, stock.getQuantity());
        contentValues.put(Config.COLUMN_STOCK_PURCHASE_PRICE, stock.getPurchase_price());
        contentValues.put(Config.COLUMN_STOCK_SELL_STOCK, stock.getSell_price());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_STOCK, null, contentValues);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Stock> getAllStock(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_STOCK, null, null, null, null, null, null, null);



            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Stock> stockList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STOCK_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_NAME));
                        long registrationNumber = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_STOCK_BARCODE));
                        String quantity = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_QUANTITY));
                        String purchase_price = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_PURCHASE_PRICE));
                        String sell_price = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_SELL_STOCK));

                        stockList.add(new Stock(id, name, registrationNumber, quantity, purchase_price, sell_price));
                    }   while (cursor.moveToNext());

                    return stockList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public Stock getStockByRegNum(long barcodeNum){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Stock stock = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_STOCK, null,
                    Config.COLUMN_STOCK_BARCODE + " = ? ", new String[]{String.valueOf(barcodeNum)},
                    null, null, null);



            if(cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STOCK_ID));
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_NAME));
                long barcode = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_STOCK_BARCODE));
                String quantity = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_QUANTITY));
                String purchase_price = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_PURCHASE_PRICE));
                String sell_price = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STOCK_SELL_STOCK));

                stock = new Stock(id, name, barcode, quantity, purchase_price, sell_price);
            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return stock;
    }

    public long updateStockInfo(Stock stock){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STOCK_NAME, stock.getName());
        contentValues.put(Config.COLUMN_STOCK_BARCODE, stock.getBarcode());
        contentValues.put(Config.COLUMN_STOCK_QUANTITY, stock.getQuantity());
        contentValues.put(Config.COLUMN_STOCK_PURCHASE_PRICE, stock.getPurchase_price());
        contentValues.put(Config.COLUMN_STOCK_SELL_STOCK, stock.getSell_price());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_STOCK, contentValues,
                    Config.COLUMN_STOCK_ID + " = ? ",
                    new String[] {String.valueOf(stock.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteStockByRegNum(long barcodeNum) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_STOCK,
                                    Config.COLUMN_STOCK_BARCODE + " = ? ",
                                    new String[]{ String.valueOf(barcodeNum)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public boolean deleteAllStock(){
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            //for "1" delete() method returns number of deleted rows
            //if you don't want row count just use delete(TABLE_NAME, null, null)
            sqLiteDatabase.delete(Config.TABLE_STOCK, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_STOCK);

            if(count==0)
                deleteStatus = true;

        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }

}