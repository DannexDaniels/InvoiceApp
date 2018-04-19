package com.dannextech.apps.invoiceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * Created by amoh on 12/28/2017.
 */

public class InvoiceDbQueries {
    private SQLiteDatabase db;
    private Context context;
    private InvoiceDbHelper dbHelper;

    public InvoiceDbQueries(Context context) {
        this.context = context;
        dbHelper = new InvoiceDbHelper(context);
        dbHelper.getInstance(context);
    }

    public void saveCustomer(String name, String loc, String org, String email, String phone, String date, String paid, String sent){
        db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(InvoiceDbContract.Customer.COL_NAME,name);
        val.put(InvoiceDbContract.Customer.COL_LOCATION,loc);
        val.put(InvoiceDbContract.Customer.COL_ORG,org);
        val.put(InvoiceDbContract.Customer.COL_EMAIL,email);
        val.put(InvoiceDbContract.Customer.COL_PHONE,phone);
        val.put(InvoiceDbContract.Customer.COL_DATE,date);
        val.put(InvoiceDbContract.Customer.COL_PAID_STATUS,paid);
        val.put(InvoiceDbContract.Customer.COL_SENT_STATUS,sent);

        db.insert(InvoiceDbContract.Customer.TABLE_NAME,null,val);
        db.close();

        Toast.makeText(context,"Customer Saved Successfully",Toast.LENGTH_SHORT).show();
    }

    public void saveProduct(String prodName, int quantity, int price, String cusName, String custOrg){
        db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(InvoiceDbContract.Products.COL_NAME,prodName);
        val.put(InvoiceDbContract.Products.COL_QUANTITY,quantity);
        val.put(InvoiceDbContract.Products.COL_PRICE,price);
        val.put(InvoiceDbContract.Products.COL_CUSTOMER_NAME,cusName);
        val.put(InvoiceDbContract.Products.COL_CUSTOMER_ORG,custOrg);

        db.insert(InvoiceDbContract.Products.TABLE_NAME,null,val);
        db.close();

        Toast.makeText(context,"Product Saved Successfully",Toast.LENGTH_SHORT).show();
    }

    public ProductBillModel[] retrieveProduct(String cusName){
        db = dbHelper.getReadableDatabase();

        String columns[] = {InvoiceDbContract.Products.COL_CUSTOMER_NAME, InvoiceDbContract.Products.COL_CUSTOMER_ORG, InvoiceDbContract.Products.COL_NAME, InvoiceDbContract.Products.COL_PRICE, InvoiceDbContract.Products.COL_QUANTITY};
        String selection = InvoiceDbContract.Products.COL_CUSTOMER_NAME + "=?";
        String selectionArgs[] = {cusName};
        Cursor cursor = db.query(InvoiceDbContract.Products.TABLE_NAME,columns,selection,selectionArgs,null,null,null);

        ProductBillModel[] viewProducts = new ProductBillModel[cursor.getCount()];

        int position = 0;
        while (cursor.moveToNext()){
            viewProducts[position] = new ProductBillModel();
            viewProducts[position].setProdName(cursor.getString(cursor.getColumnIndexOrThrow(InvoiceDbContract.Products.COL_NAME)));
            viewProducts[position].setPrice(cursor.getInt(cursor.getColumnIndexOrThrow(InvoiceDbContract.Products.COL_PRICE)));
            viewProducts[position].setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(InvoiceDbContract.Products.COL_QUANTITY)));
            position++;
        }
        return viewProducts;
    }
    public CustomerBillModel[] retrieveBill(){
        db = dbHelper.getReadableDatabase();

        String columns[] = {InvoiceDbContract.Customer._ID, InvoiceDbContract.Customer.COL_NAME, InvoiceDbContract.Customer.COL_ORG, InvoiceDbContract.Customer.COL_LOCATION, InvoiceDbContract.Customer.COL_EMAIL, InvoiceDbContract.Customer.COL_PHONE, InvoiceDbContract.Customer.COL_DATE, InvoiceDbContract.Customer.COL_SENT_STATUS, InvoiceDbContract.Customer.COL_PAID_STATUS};

        Cursor cursor = db.query(InvoiceDbContract.Customer.TABLE_NAME,columns,null,null,null,null,null);

        CustomerBillModel[] viewCustomer = new CustomerBillModel[cursor.getCount()];

        int position = 0;
        while (cursor.moveToNext()){
            viewCustomer[position] = new CustomerBillModel();
            viewCustomer[position].setName(cursor.getString(cursor.getColumnIndexOrThrow(InvoiceDbContract.Customer.COL_NAME)));
            viewCustomer[position].setLoc(cursor.getString(cursor.getColumnIndexOrThrow(InvoiceDbContract.Customer.COL_LOCATION)));
            viewCustomer[position].setOrg(cursor.getString(cursor.getColumnIndexOrThrow(InvoiceDbContract.Customer.COL_ORG)));
            viewCustomer[position].setEmail(cursor.getString(cursor.getColumnIndexOrThrow(InvoiceDbContract.Customer.COL_EMAIL)));
            viewCustomer[position].setPhone(cursor.getString(cursor.getColumnIndexOrThrow(InvoiceDbContract.Customer.COL_PHONE)));
            viewCustomer[position].setBillId(cursor.getInt(cursor.getColumnIndexOrThrow(InvoiceDbContract.Customer._ID)));
            viewCustomer[position].setDate(cursor.getString(cursor.getColumnIndexOrThrow(InvoiceDbContract.Customer.COL_DATE)));
            viewCustomer[position].setPaidStatus(cursor.getString(cursor.getColumnIndexOrThrow(InvoiceDbContract.Customer.COL_PAID_STATUS)));
            Log.e(TAG, "retrieveBill: "+viewCustomer[position].getSentStatus());
            viewCustomer[position].setSentStatus(cursor.getString(cursor.getColumnIndexOrThrow(InvoiceDbContract.Customer.COL_SENT_STATUS)));
            position++;
        }
        return viewCustomer;

        /*
        * Something to do: use the customer _ID as the link between the customer and the product
        */
    }

    public void updateStatus(String phone){
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(InvoiceDbContract.Customer.COL_SENT_STATUS,"SENT");


        int result = db.update(InvoiceDbContract.Customer.TABLE_NAME,values,InvoiceDbContract.Customer.COL_PHONE+"="+phone,null);

        if (result == -1){
            Toast.makeText(context,"Not Updated",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,"Updated",Toast.LENGTH_LONG).show();
        }
    }

}
