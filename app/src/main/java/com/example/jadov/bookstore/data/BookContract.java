package com.example.jadov.bookstore.data;

import android.provider.BaseColumns;

public class BookContract {

    private BookContract() {
    }

    // Creates the constant names for the books table name and headers
    public static final class BookEntry implements BaseColumns {

        public static final String TABLE_NAME = "books";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE_NUM = "supplier_phone_number";
        public static final String COLUMN_ISBN = "isbn";

    }
}
