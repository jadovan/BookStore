package com.example.jadov.bookstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jadov.bookstore.data.BookContract.BookEntry;

public class BookDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "book_store.db";

    // SQL query to create the books table
    private static final String SQL_CREATE_BOOKS_TABLE =
            "CREATE TABLE " + BookEntry.TABLE_NAME + " (" +
                    BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    BookEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL," +
                    BookEntry.COLUMN_PRICE + " REAL NOT NULL," +
                    BookEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 1," +
                    BookEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL," +
                    BookEntry.COLUMN_SUPPLIER_PHONE_NUM + " TEXT NOT NULL," +
                    BookEntry.COLUMN_ISBN + " TEXT NOT NULL)";

    // SQL query to delete the books table
    private static final String SQL_DELETE_BOOKS_TABLE =
            "DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME;

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BOOKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_BOOKS_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
