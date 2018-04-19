package com.dannextech.apps.invoiceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.InputQueue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddProduct extends AppCompatActivity {

    EditText etName, etUnitPrice, etQuantity;
    TextView tvName, tvOrg, tvEmail, tvPhone;
    Button btAddMore, btEnough;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        final InvoiceDbQueries query = new InvoiceDbQueries(this);

        tvName = (TextView) findViewById(R.id.tvProdCusName);
        tvOrg = (TextView) findViewById(R.id.tvProdCusOrg);
        tvEmail = (TextView) findViewById(R.id.tvProdCusEmail);
        tvPhone = (TextView) findViewById(R.id.tvProdCusPhone);

        etName = (EditText) findViewById(R.id.etProdName);
        etUnitPrice = (EditText) findViewById(R.id.etProdPrice);
        etQuantity = (EditText) findViewById(R.id.etProdQuant);

        btAddMore = (Button) findViewById(R.id.btAddMore);
        btEnough = (Button) findViewById(R.id.btEnough);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        tvName.setText(preferences.getString(InvoiceDbContract.Customer.COL_NAME," "));
        tvOrg.setText(preferences.getString(InvoiceDbContract.Customer.COL_ORG," "));
        tvEmail.setText(preferences.getString(InvoiceDbContract.Customer.COL_EMAIL," "));
        tvPhone.setText(preferences.getString(InvoiceDbContract.Customer.COL_PHONE," "));

        btAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFilled()){
                    query.saveProduct(etName.getText().toString(),Integer.parseInt(etUnitPrice.getText().toString()),Integer.parseInt(etQuantity.getText().toString()),tvName.getText().toString(),tvOrg.getText().toString());
                    clearAll();
                }
            }
        });
        btEnough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFilled()){
                    query.saveProduct(etName.getText().toString(),Integer.parseInt(etUnitPrice.getText().toString()),Integer.parseInt(etQuantity.getText().toString()),tvName.getText().toString(),tvOrg.getText().toString());
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        });
    }

    private void clearAll() {
        etName.setText("");
        etUnitPrice.setText("");
        etQuantity.setText("");
    }

    private boolean allFilled() {
        if (etName.getText().toString().isEmpty()){
            etName.setError("Product Name is required");
            return false;
        }else if (etUnitPrice.getText().toString().isEmpty()){
            etUnitPrice.setError("Unit Prices is required");
            return false;
        }else if (etQuantity.getText().toString().isEmpty()){
            etQuantity.setError("Quantity is required");
            return false;
        }else
            return true;
    }
}
