package com.dannextech.apps.invoiceapp;

import android.provider.BaseColumns;

/**
 * Created by amoh on 12/28/2017.
 */

public class InvoiceDbContract {
    public InvoiceDbContract() {
    }

    public static class Customer implements BaseColumns{
        public static final String TABLE_NAME = "customer";
        public static final String COL_NAME = "name";
        public static final String COL_LOCATION = "location";
        public static final String COL_ORG = "organization";
        public static final String COL_EMAIL = "email";
        public static final String COL_PHONE = "phone";
        public static final String COL_DATE = "date";
        public static final String COL_SENT_STATUS = "sent_status";
        public static final String COL_PAID_STATUS = "paid_status";
    }

    public static class Products implements BaseColumns{
        public static final String TABLE_NAME = "products";
        public static final String COL_NAME = "prodName";
        public static final String COL_PRICE = "price";
        public static final String COL_QUANTITY = "quantity";
        public static final String COL_CUSTOMER_NAME = "customer";
        public static final String COL_CUSTOMER_ORG = "organization";
    }
}
