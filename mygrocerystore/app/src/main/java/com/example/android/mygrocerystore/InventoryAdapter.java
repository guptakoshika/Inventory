package com.example.android.mygrocerystore;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
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
        View view= LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view,context);
        view.setTag(viewHolder);
        return view;
    }
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int nameColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_NAME);
        int priceColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_ITEM_QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(Inventorycontract.newItem.COLUMN_IMAGE);
        
        // Read the item attributes from the Cursor for the current item
        String itemName = cursor.getString(nameColumnIndex);
        String itemPrice = cursor.getString(priceColumnIndex);
        String itemQuantity = cursor.getString(quantityColumnIndex);
        String imageView = cursor.getString(imageColumnIndex);
        viewHolder.quantity1 = itemQuantity;

        // Update the TextViews with the attributes for the current item
        viewHolder.nameTextView.setText(itemName);
        viewHolder.summaryTextView.setText(itemPrice);
        viewHolder.QuantityTextView.setText(itemQuantity);
        viewHolder.image.setImageURI(Uri.parse(imageView));
    }

    static class ViewHolder implements View.OnClickListener{
        TextView nameTextView;
        TextView summaryTextView;
        TextView QuantityTextView;
        ImageView image,orderImage;
        Context context;
        String quantity1;
        ViewHolder(View view,Context c){
            nameTextView = view.findViewById(R.id.name_textview);
            summaryTextView =  view.findViewById(R.id.price_textview);
            QuantityTextView = view.findViewById(R.id.quantity_textview);
            image = (ImageView) view.findViewById(R.id.image_view1);
            orderImage=(ImageView) view.findViewById(R.id.order_imageView);
            context=c;
            orderImage.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int Quantity = Integer.parseInt(quantity1);
             Quantity--;
             if(Quantity<0)
             {
                 Quantity=0;
             }
             QuantityTextView.setText(Quantity+"");
        }
    }
}
