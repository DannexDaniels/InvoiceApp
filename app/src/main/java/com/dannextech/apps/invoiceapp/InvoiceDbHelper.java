package com.dannextech.apps.invoiceapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amoh on 12/28/2017.
 */

public class InvoiceDbHelper extends SQLiteOpenHelper {
    private InvoiceDbHelper mInstance = null;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Invoice";

    private static final String SQL_CREATE_CUSTOMER = "CREATE TABLE " +
            InvoiceDbContract.Customer.TABLE_NAME + "(" +
            InvoiceDbContract.Customer._ID + " INTEGER PRIMARY KEY, " +
            InvoiceDbContract.Customer.COL_NAME + " TEXT, " +
            InvoiceDbContract.Customer.COL_LOCATION + " TEXT, " +
            InvoiceDbContract.Customer.COL_ORG + " TEXT, " +
            InvoiceDbContract.Customer.COL_EMAIL + " TEXT, " +
            InvoiceDbContract.Customer.COL_PHONE + " TEXT, " +
            InvoiceDbContract.Customer.COL_DATE + " TEXT, " +
            InvoiceDbContract.Customer.COL_SENT_STATUS + " TEXT, " +
            InvoiceDbContract.Customer.COL_PAID_STATUS + " TEXT)";
    private static final String SQL_CREATE_PRODUCT = "CREATE TABLE " +
            InvoiceDbContract.Products.TABLE_NAME + "(" +
            InvoiceDbContract.Products._ID + "INTEGER PRIMARY KEY, " +
            InvoiceDbContract.Products.COL_NAME + " TEXT, " +
            InvoiceDbContract.Products.COL_PRICE + " INT, " +
            InvoiceDbContract.Products.COL_QUANTITY + " INT, " +
            InvoiceDbContract.Products.COL_CUSTOMER_NAME + " TEXT, " +
            InvoiceDbContract.Products.COL_CUSTOMER_ORG + " TEXT)";

    private static String SQL_DELETE_CUSTOMER = "DROP TABLE IF EXISTS " + InvoiceDbContract.Customer.TABLE_NAME;
    private static String SQL_DELETE_PRODUCT = "DROP TABLE IF EXISTS " + InvoiceDbContract.Products.TABLE_NAME;

    public InvoiceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CUSTOMER);
        db.execSQL(SQL_CREATE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CUSTOMER);
        db.execSQL(SQL_DELETE_PRODUCT);
        onCreate(db);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public InvoiceDbHelper getInstance(Context context){
        if (mInstance==null){
            mInstance = new InvoiceDbHelper(context.getApplicationContext());
        }
        return mInstance;
    }
}
