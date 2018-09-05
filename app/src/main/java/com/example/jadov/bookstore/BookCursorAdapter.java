package com.example.jadov.bookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jadov.bookstore.data.BookContract.BookEntry;

public class BookCursorAdapter extends CursorAdapter {
    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
    }

    /**
     * This method binds the book data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current book can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView bookName = view.findViewById(R.id.list_book_name);
        TextView bookIsbn = view.findViewById(R.id.list_book_isbn);
        TextView bookPrice = view.findViewById(R.id.list_book_price);
        TextView bookQuantity = view.findViewById(R.id.list_book_quantity);
        Button buyBtn = view.findViewById(R.id.buy_btn);
        // Extract priorities from cursor
        final String NAME = cursor.getString(cursor.getColumnIndexOrThrow(BookEntry.COLUMN_PRODUCT_NAME));
        final String ISBN = cursor.getString(cursor.getColumnIndexOrThrow(BookEntry.COLUMN_ISBN));
        final String PRICE = cursor.getString(cursor.getColumnIndexOrThrow(BookEntry.COLUMN_PRICE));
        final String QUANTITY = cursor.getString(cursor.getColumnIndexOrThrow(BookEntry.COLUMN_QUANTITY));

        // Populate fields with extracted properties
        bookName.setText(NAME);
        bookIsbn.setText(ISBN);
        bookPrice.setText(PRICE);
        bookQuantity.setText(QUANTITY);

        final int QUANTITY_INDEX = cursor.getColumnIndexOrThrow(BookEntry.COLUMN_QUANTITY);
        final int CURRENT_QUANTITY = Integer.valueOf(cursor.getString(QUANTITY_INDEX));
        final int BOOK_ID = cursor.getInt(cursor.getColumnIndexOrThrow(BookEntry._ID));
        final int SALE_QUANTITY = 1;

        final int BOOK_INDEX = cursor.getColumnIndexOrThrow(BookEntry.COLUMN_PRODUCT_NAME);
        final String BOOK_TITLE = cursor.getString(BOOK_INDEX);
        final String SOLD = context.getString(R.string.book_sold);
        final String OUT_OF_STOCK = context.getString(R.string.out_of_stock);

        buyBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (CURRENT_QUANTITY > 0) {
                    final int UPDATED_QUANTITY = CURRENT_QUANTITY - SALE_QUANTITY;
                    Uri saleUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, BOOK_ID);

                    ContentValues values = new ContentValues();
                    values.put(BookEntry.COLUMN_QUANTITY, UPDATED_QUANTITY);
                    context.getContentResolver().update(saleUri, values, null, null);
                    Toast.makeText(context, SALE_QUANTITY + " \"" + BOOK_TITLE + "\" "
                            + SOLD, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, " \"" + BOOK_TITLE + "\" " + OUT_OF_STOCK,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
