package com.mucahittoktas.stock_project.Util;

public class Config {

    public static final String DATABASE_NAME = "stock-db";

    //column names of stock table
    public static final String TABLE_STOCK = "stock";
    public static final String COLUMN_STOCK_ID = "_id";
    public static final String COLUMN_STOCK_NAME = "name";
    public static final String COLUMN_STOCK_BARCODE= "barcode";
    public static final String COLUMN_STOCK_QUANTITY = "quantity";
    public static final String COLUMN_STOCK_PURCHASE_PRICE = "purchase_price";
    public static final String COLUMN_STOCK_SELL_STOCK = "sell_price";

    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_STOCK = "create_stock";
    public static final String UPDATE_STOCK = "update_stock";
}
