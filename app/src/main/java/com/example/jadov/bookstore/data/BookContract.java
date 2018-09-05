package com.example.jadov.bookstore.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class BookContract {

    private BookContract() {
    }

    // Content authority for the provider
    public static final String CONTENT_AUTHORITY = "com.example.jadov.bookstore";
    // Base URI for the provider
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Creates the constant names for the books table name and headers
    public static final class BookEntry implements BaseColumns {
        // Appended path name for books within the Content URI for the provider
        static final String PATH_BOOKS = "books";
        // Content URI for books within the provider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);
        // The MIME type of the {@Link #CONTENT_URI} for a list of books
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;
        // The MIME type of the {@Link #CONTENT_URI} for a single book
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

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
