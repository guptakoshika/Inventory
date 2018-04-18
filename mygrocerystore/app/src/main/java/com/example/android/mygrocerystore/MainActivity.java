package com.example.android.mygrocerystore;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    InventoryAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        ListView inventoryListView = findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        inventoryListView.setEmptyView(emptyView);

        mCursorAdapter = new InventoryAdapter(this, null);
        inventoryListView.setAdapter(mCursorAdapter);


        // Setup the item click listener
        inventoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                Uri currentPetUri = ContentUris.withAppendedId(Inventorycontract.newItem.CONTENT_URI, id);

                intent.setData(currentPetUri);

                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(0, null, this);
    }


    private void insertdata() {

        ContentValues values = new ContentValues();
        values.put(Inventorycontract.newItem.COLUMN_ITEM_NAME, "MILK");
        values.put(Inventorycontract.newItem.COLUMN_ITEM_PRICE,"Rs.20");
        values.put(Inventorycontract.newItem.COLUMN_ITEM_QUANTITY, "100");
        values.put(Inventorycontract.newItem.COLUMN_SUPPLIERS_NAME, "VERKA");
        values.put(Inventorycontract.newItem.COLUMN_SUPPLIERS_INFO, "0123456789");
        values.put(Inventorycontract.newItem.COLUMN_IMAGE,"android.resource://com.example.android.mygrocerystore/drawable/milk");
        Uri newUri = getContentResolver().insert(Inventorycontract.newItem.CONTENT_URI, values);


        ContentValues juice = new ContentValues();
        juice.put(Inventorycontract.newItem.COLUMN_ITEM_NAME, "Apple Juice");
        juice.put(Inventorycontract.newItem.COLUMN_ITEM_PRICE, "Rs.20");
        juice.put(Inventorycontract.newItem.COLUMN_ITEM_QUANTITY, "50");
        juice.put(Inventorycontract.newItem.COLUMN_SUPPLIERS_NAME, "Tropicana");
        juice.put(Inventorycontract.newItem.COLUMN_SUPPLIERS_INFO, "0123456789");
        juice.put(Inventorycontract.newItem.COLUMN_IMAGE,"android.resource://com.example.android.mygrocerystore/drawable/apple_juice");
        Uri juiceUri = getContentResolver().insert(Inventorycontract.newItem.CONTENT_URI, juice);

        ContentValues chips = new ContentValues();
        chips.put(Inventorycontract.newItem.COLUMN_ITEM_NAME, "Chips");
        chips.put(Inventorycontract.newItem.COLUMN_ITEM_PRICE, "RS.10");
        chips.put(Inventorycontract.newItem.COLUMN_ITEM_QUANTITY, "100");
        chips.put(Inventorycontract.newItem.COLUMN_SUPPLIERS_NAME, "Lays");
        chips.put(Inventorycontract.newItem.COLUMN_SUPPLIERS_INFO, "0123456789");
        chips.put(Inventorycontract.newItem.COLUMN_IMAGE,"android.resource://com.example.android.mygrocerystore/drawable/chips");
        Uri chipsUri = getContentResolver().insert(Inventorycontract.newItem.CONTENT_URI, chips);

        ContentValues cookies = new ContentValues();
        cookies.put(Inventorycontract.newItem.COLUMN_ITEM_NAME, "Cookies");
        cookies.put(Inventorycontract.newItem.COLUMN_ITEM_PRICE, "Rs.20");
        cookies.put(Inventorycontract.newItem.COLUMN_ITEM_QUANTITY, "100");
        cookies.put(Inventorycontract.newItem.COLUMN_SUPPLIERS_NAME, "Britania cookies co.");
        cookies.put(Inventorycontract.newItem.COLUMN_SUPPLIERS_INFO, "0123456789");
        cookies.put(Inventorycontract.newItem.COLUMN_IMAGE,"android.resource://com.example.android.mygrocerystore/drawable/cookies");
        Uri cookiesuri = getContentResolver().insert(Inventorycontract.newItem.CONTENT_URI, cookies);
    }

    private void deleteAllitems() {
        int rowsDeleted = getContentResolver().delete(Inventorycontract.newItem.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.dummy_data:
                insertdata();
                return true;
            case R.id.del_all:
                deleteAllitems();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                Inventorycontract.newItem._ID,
                Inventorycontract.newItem.COLUMN_ITEM_NAME,
                Inventorycontract.newItem.COLUMN_ITEM_PRICE,
                Inventorycontract.newItem.COLUMN_ITEM_QUANTITY,
                Inventorycontract.newItem.COLUMN_SUPPLIERS_NAME,
                Inventorycontract.newItem.COLUMN_SUPPLIERS_INFO,
                Inventorycontract.newItem.COLUMN_IMAGE
        };

        return new CursorLoader(this,
                Inventorycontract.newItem.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
