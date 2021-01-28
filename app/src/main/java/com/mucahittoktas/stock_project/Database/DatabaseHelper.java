package com.mucahittoktas.stock_project.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mucahittoktas.stock_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if(databaseHelper==null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_STOCK_TABLE = "CREATE TABLE " + Config.TABLE_STOCK + "("
                + Config.COLUMN_STOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_STOCK_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_STOCK_BARCODE + " INTEGER NOT NULL UNIQUE, "
                + Config.COLUMN_STOCK_QUANTITY + " TEXT NOT NULL, " //nullable
                + Config.COLUMN_STOCK_PURCHASE_PRICE + " TEXT, " //nullable
                + Config.COLUMN_STOCK_SELL_STOCK + " TEXT "
                + ")";

        Logger.d("Table create SQL: " + CREATE_STOCK_TABLE);

        db.execSQL(CREATE_STOCK_TABLE);

        Logger.d("DB created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_STOCK);

        // Create tables again
        onCreate(db);
    }

}
