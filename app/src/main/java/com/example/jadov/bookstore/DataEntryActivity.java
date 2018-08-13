package com.example.jadov.bookstore;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jadov.bookstore.data.BookContract.BookEntry;
import com.example.jadov.bookstore.data.BookDbHelper;

public class DataEntryActivity extends AppCompatActivity {

    /**
     * EditText field to enter the book's title
     */
    private EditText mTitleEditText;

    /**
     * EditText field to enter the book's isbn
     */
    private EditText mIsbnEditText;

    /**
     * EditText field to enter the book's price
     */
    private EditText mPriceEditText;

    /**
     * EditText field to enter the book's quantity
     */
    private EditText mQuantityEditText;

    /**
     * EditText field to enter the supplier's name
     */
    private EditText mSupplierNameEditText;

    /**
     * EditText field to enter the supplier's phone number
     */
    private EditText mSupplierPhoneEditText;

    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        // Find all relevant views that we will need to read user input from
        mTitleEditText = findViewById(R.id.edit_book_title);
        mIsbnEditText = findViewById(R.id.edit_book_isbn);
        mPriceEditText = findViewById(R.id.edit_book_price);
        mQuantityEditText = findViewById(R.id.edit_book_quantity);
        mSupplierNameEditText = findViewById(R.id.edit_supplier_name);
        mSupplierPhoneEditText = findViewById(R.id.edit_supplier_phone);

        mDbHelper = new BookDbHelper(this);
    }

    private void insertBook() {

        String title = mTitleEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String supplierName = mSupplierNameEditText.getText().toString().trim();
        String supplierPhone = mSupplierPhoneEditText.getText().toString().trim();
        String isbn = mIsbnEditText.getText().toString().trim();

        // Sets variables to null or default if user doesn't enter any input
        double price = 0.00;
        int quantity = 0;

        if (title.equals("")) {
            title = null;
        }
        if (!priceString.equals("")) {
            price = Double.parseDouble(String.format(priceString, "%.2f"));
        }
        if (!quantityString.equals("")) {
            quantity = Integer.parseInt(quantityString);
        }
        if (supplierName.equals("")) {
            supplierName = null;
        }
        if (supplierPhone.equals("")) {
            supplierPhone = null;
        }
        if (isbn.equals("")) {
            isbn = null;
        }

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, title);
        values.put(BookEntry.COLUMN_PRICE, price);
        values.put(BookEntry.COLUMN_QUANTITY, quantity);
        values.put(BookEntry.COLUMN_SUPPLIER_NAME, supplierName);
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE_NUM, supplierPhone);
        values.put(BookEntry.COLUMN_ISBN, isbn);

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
        // Inflate the menu options from the res/menu/menu_data_entry.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_data_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertBook();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (BookActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
