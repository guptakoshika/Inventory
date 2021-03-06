package com.example.android.mygrocerystore;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private Uri mCurrentUri;

    private EditText mNameEditText;

    private EditText mPriceEditText;

    private EditText mSuppliernameEditText;

    private EditText mSupplierinfoEditText;
    public int quantity;

    private EditText mquantity;
    private boolean mChanged = false;
    private Button mbrowseimage;
    private ImageView image;
    private static final int PERMISSIONS_EXTERNAL_STORAGE = 1;
    private static final int IMAGE_REQUEST = 0;
    Uri actualUri;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        mCurrentUri = intent.getData();
        if (mCurrentUri == null) {

            setTitle("Add a item");
            invalidateOptionsMenu();
        } else {

            setTitle("Edit a Item");
            invalidateOptionsMenu();
            getLoaderManager().initLoader(0, null, this);
        }
        mNameEditText = findViewById(R.id.item_name);
        mPriceEditText = findViewById(R.id.item_price);
        mSupplierinfoEditText = findViewById(R.id.suppliers_add_info);
        mSuppliernameEditText = findViewById(R.id.supplier_name);
        mbrowseimage = findViewById(R.id.browser);
        image = findViewById(R.id.image_view1);
        mbrowseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Image();
            }
        });
        mquantity = findViewById(R.id.quantity);
    }
    private boolean setValue(EditText text) {
        if (TextUtils.isEmpty(text.getText())) {
            text.setError("product is missing!! recheck it!");
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            text.setError(null);
            return true;
        }
    }

    public void saveItem() {
        boolean check = true;
        if (!setValue(mNameEditText )) {
            check = false;
        }
        if (!setValue(mPriceEditText)) {
            check = false;
        }
        if (!setValue(mquantity)) {
            check = false;
        }
        if (!setValue(mSuppliernameEditText)) {
            check = false;
        }
        if (!setValue(mSupplierinfoEditText)) {
            check = false;
        }
        if (actualUri == null) {
            check = false;
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        } else {
            String nameString = mNameEditText.getText().toString().trim();
            String breedString = mPriceEditText.getText().toString().trim();
            int quantityString = quantity;
            String suppilernameString = mSuppliernameEditText.getText().toString().trim();
            String suppliersinfoString = mSupplierinfoEditText.getText().toString().trim();
            String imagestring = actualUri.toString();

            ContentValues values = new ContentValues();
            values.put(Inventorycontract.newItem.COLUMN_ITEM_NAME, nameString);
            values.put(Inventorycontract.newItem.COLUMN_ITEM_PRICE, breedString);
            values.put(Inventorycontract.newItem.COLUMN_ITEM_QUANTITY, quantityString);
            values.put(Inventorycontract.newItem.COLUMN_SUPPLIERS_NAME, suppilernameString);
            values.put(Inventorycontract.newItem.COLUMN_SUPPLIERS_INFO, suppliersinfoString);
            values.put(Inventorycontract.newItem.COLUMN_IMAGE, imagestring);

            if (mCurrentUri == null) {

                Uri newUri = getContentResolver().insert(Inventorycontract.newItem.CONTENT_URI, values);

                // Show a toast message depending on whether or not the insertion was successful.
                if (newUri == null) {
                    // If the new content URI is null, then there was an error with insertion.
                    Toast.makeText(this, "Item editing Failed!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the insertion was successful and we can display a toast.
                    Toast.makeText(this, "Item Edited successfully",
                            Toast.LENGTH_SHORT).show();
                }
            } else {

                int rowsAffected = getContentResolver().update(mCurrentUri, values, null, null);

                if (rowsAffected == 0) {

                    Toast.makeText(this, "Item update failed!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Item Updated Successfully",
                            Toast.LENGTH_SHORT).show();
                }
            }

            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save:
                saveItem();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;

            case R.id.orderm:
                String suppliersinfoString = mSupplierinfoEditText.getText().toString().trim();
                dialPhoneNumber(suppliersinfoString);
        }

        return super.onOptionsItemSelected(item);
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
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
                mCurrentUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_NAME);
            int priceColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_SUPPLIERS_NAME);
            int supplierInfoColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_SUPPLIERS_INFO);
            int imageColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_IMAGE);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            String price = cursor.getString(priceColumnIndex);
            String sname = cursor.getString(supplierNameColumnIndex);
            String sinfo = cursor.getString(supplierInfoColumnIndex);
            String quantityc = cursor.getString(quantityColumnIndex);
            String imagec = cursor.getString(imageColumnIndex);
            // Update the views on the screen with the values from the database
            mNameEditText.setText(name);
            mPriceEditText.setText(price);
            mSuppliernameEditText.setText(sname);
            mSupplierinfoEditText.setText(sinfo);
            mquantity.setText(quantityc);
            image.setImageURI(Uri.parse(imagec));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mNameEditText.setText("");
        mPriceEditText.setText("");
        quantity = 0;
        mSuppliernameEditText.setText("");
        mSupplierinfoEditText.setText("");
        mquantity.setText("");
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");
        builder.setPositiveButton("Discard", discardButtonClickListener);
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteitem();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void plus(View view) {
        quantity++;
        display(quantity);
    }

    public void loose(View view) {
        quantity--;
        if (quantity <= 0) {
            quantity = 0;
        }
        display(quantity);
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity);
        quantityTextView.setText("" + number);
    }

    public void Image() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_EXTERNAL_STORAGE);
            return;
        }
        openImageSelector();
    }

    private void openImageSelector() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openImageSelector();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                actualUri = resultData.getData();
                image.setImageURI(actualUri);
                image.invalidate();
            }
        }
    }

    private void deleteitem() {
        if (mCurrentUri != null) {

            int rowsDeleted = getContentResolver().delete(mCurrentUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, "Error while deleting the item",
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Item deleted Successfully!!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}

