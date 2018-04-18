package com.example.android.mygrocerystore;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        ImageView image = (ImageView) view.findViewById(R.id.image_view1);

        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_NAME);
        int priceColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_IMAGE);

        // Read the pet attributes from the Cursor for the current item
        String itemName = cursor.getString(nameColumnIndex);
        String itemPrice = cursor.getString(priceColumnIndex);
        String itemQuantity = cursor.getString(quantityColumnIndex);
        String imageView = cursor.getString(imageColumnIndex);

        // Update the TextViews with the attributes for the current item
        nameTextView.setText(itemName);
        summaryTextView.setText(itemPrice);
        QuantityTextView.setText(itemQuantity);
        image.setImageURI(Uri.parse(imageView));
       // image.setImageResource(imageView);
    }
}
