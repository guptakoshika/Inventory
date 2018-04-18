package com.example.android.mygrocerystore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Koshika Gupta on 01-04-2018.
 */

public class InventoryDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = InventoryDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "store.db";

    private static final int DATABASE_VERSION = 1;

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + Inventorycontract.newItem.TABLE_NAME + " ("
                + Inventorycontract.newItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Inventorycontract.newItem.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + Inventorycontract.newItem.COLUMN_ITEM_PRICE + " INTEGER NOT NULL, "
                + Inventorycontract.newItem.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL, "
                + Inventorycontract.newItem.COLUMN_SUPPLIERS_NAME + " TEXT NOT NULL,"
                + Inventorycontract.newItem.COLUMN_SUPPLIERS_INFO + " TEXT NOT NULL,"
                + Inventorycontract.newItem.COLUMN_IMAGE + " TEXT NOT NULL );";

        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
