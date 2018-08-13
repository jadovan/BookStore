package com.example.jadov.bookstore;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jadov.bookstore.data.BookContract.BookEntry;
import com.example.jadov.bookstore.data.BookDbHelper;

import java.util.Locale;

public class BookActivity extends AppCompatActivity {

    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // Setup FAB to open DataEntryActivity
        FloatingActionButton fab = findViewById(R.id.book_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookActivity.this, DataEntryActivity.class);
                startActivity(intent);
            }
        });
        //displayDatabaseInfo();

        mDbHelper = new BookDbHelper(this);

        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the books database.
     */
    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRICE,
                BookEntry.COLUMN_QUANTITY,
                BookEntry.COLUMN_SUPPLIER_NAME,
                BookEntry.COLUMN_SUPPLIER_PHONE_NUM,
                BookEntry.COLUMN_ISBN};

        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null
        );

        TextView displayView = findViewById(R.id.book_text_view);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // books table in the database).
            String displayNumRows = getString(R.string.number_of_rows) + cursor.getCount() + "\n";
            displayView.setText(displayNumRows);
            displayView.append("\n" + BookEntry._ID + " - " +
                    BookEntry.COLUMN_PRODUCT_NAME + " - " +
                    BookEntry.COLUMN_PRICE + " - " +
                    BookEntry.COLUMN_QUANTITY + " - " +
                    BookEntry.COLUMN_SUPPLIER_NAME + " - " +
                    BookEntry.COLUMN_SUPPLIER_PHONE_NUM + " - " +
                    BookEntry.COLUMN_ISBN + "\n");

            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int titleColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_SUPPLIER_PHONE_NUM);
            int isbnColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_ISBN);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentTitle = cursor.getString(titleColumnIndex);
                double currentPrice = cursor.getDouble(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);
                String currentIsbn = cursor.getString(isbnColumnIndex);
                displayView.append("\n" + currentId + " - " + currentTitle + " - " + String.format(Locale.US, "%.2f", currentPrice)
                        + " - " + currentQuantity + " - " + currentSupplierName + " - "
                        + currentSupplierPhone + " - " + currentIsbn);
            }
        } finally {
            // Closes the cursor and releases all resources to make it invalid
            cursor.close();
        }
    }

    private void insertBook() {

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, getString(R.string.dummy_title));
        values.put(BookEntry.COLUMN_PRICE, 11.72);
        values.put(BookEntry.COLUMN_QUANTITY, 59);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, getString(R.string.dummy_supplier_name));
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE_NUM, getString(R.string.dummy_supplier_phone));
        values.put(BookEntry.COLUMN_ISBN, getString(R.string.dummy_isbn));

        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, getText(R.string.toast_error_saving), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.toast_successful_save) + newRowId,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_book.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertBook();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
