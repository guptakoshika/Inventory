package com.example.android.mygrocerystore;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Koshika Gupta on 31-03-2018.
 */

public class InventoryAdapter extends CursorAdapter {
    public InventoryAdapter(Context context, Cursor c) {
        super(context, c,0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.name);
        TextView summaryTextView =  view.findViewById(R.id.price);
        TextView QuantityTextView = view.findViewById(R.id.quantity);
     //   ImageView image = (ImageView) view.findViewById(R.id.image_view1);

        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_NAME);
        int breedColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_QUANTITY);

       // image.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_IMAGE))));

        // Read the pet attributes from the Cursor for the current pet
        String itemName = cursor.getString(nameColumnIndex);
        String itemPrice = cursor.getString(breedColumnIndex);
        String itemQuantity = cursor.getString(quantityColumnIndex);


        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(itemName);
        summaryTextView.setText(itemPrice);
        QuantityTextView.setText(itemQuantity);
        final long id = cursor.getLong(cursor.getColumnIndex(Inventorycontract.newItem._ID));

       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.clickOnViewItem(id);
            }
        });
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.clickOnSale(id,
                        QuantityTextView);
            }
        });*/
    }
}
