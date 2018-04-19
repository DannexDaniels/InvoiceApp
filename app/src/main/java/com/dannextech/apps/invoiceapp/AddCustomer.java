package com.dannextech.apps.invoiceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCustomer extends AppCompatActivity {

    EditText etName, etLoc, etEmail, etOrg, etPhone;
    Button btAddProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        final InvoiceDbQueries query = new InvoiceDbQueries(this);

        final String paid = "not paid", sent = "not sent";

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();

        etName = (EditText) findViewById(R.id.etCusName);
        etLoc = (EditText) findViewById(R.id.etCusLocation);
        etEmail = (EditText) findViewById(R.id.etCusEmail);
        etOrg = (EditText) findViewById(R.id.etCusOrganization);
        etPhone = (EditText) findViewById(R.id.etCusPhone);
        btAddProd = (Button) findViewById(R.id.btAddProducts);

        btAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allFilled()){
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String dateToday = dateFormat.format(currentTime);

                    query.saveCustomer(etName.getText().toString(),etLoc.getText().toString(),etOrg.getText().toString(),etEmail.getText().toString(),etPhone.getText().toString(),dateToday,paid,sent);

                    editor.putString(InvoiceDbContract.Customer.COL_NAME,etName.getText().toString());
                    editor.putString(InvoiceDbContract.Customer.COL_ORG,etOrg.getText().toString());
                    editor.putString(InvoiceDbContract.Customer.COL_PHONE,etPhone.getText().toString());
                    editor.putString(InvoiceDbContract.Customer.COL_EMAIL,etEmail.getText().toString());
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(),AddProduct.class));
                }
            }
        });
    }

    private boolean allFilled() {
        if (etName.getText().toString().isEmpty()){
            etName.setError("Name is required");
            return false;
        }else if (etLoc.getText().toString().isEmpty()){
            etLoc.setError("Location is required");
            return false;
        }else if(etOrg.getText().toString().isEmpty()){
            etOrg.setError("Organization is required");
            return false;
        }else if(etEmail.getText().toString().isEmpty()){
            etEmail.setError("Email is required");
            return false;
        }else if (etPhone.getText().toString().isEmpty()){
            etPhone.setError("Phone is required");
            return false;
        }else
            return true;
    }
}
