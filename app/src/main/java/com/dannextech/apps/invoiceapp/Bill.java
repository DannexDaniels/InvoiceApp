package com.dannextech.apps.invoiceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Bill extends AppCompatActivity {

    private static final String TAG = "Bill Logs";
    RecyclerView rvProducts;
    TextView tvname, tvemail, tvphone,tvorg;
    Button btSend;

    ProductBillModel[] products;

    String message = "The following is a list of products you bought:\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        rvProducts = (RecyclerView) findViewById(R.id.rvProducts);
        tvemail = (TextView) findViewById(R.id.tvProdCusEmail);
        tvname = (TextView) findViewById(R.id.tvProdCusName);
        tvorg = (TextView) findViewById(R.id.tvProdCusOrg);
        tvphone = (TextView) findViewById(R.id.tvProdCusPhone);
        btSend = (Button) findViewById(R.id.btSend);



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        tvname.setText(preferences.getString("cusname","name"));
        tvemail.setText(preferences.getString("cusemail","name"));
        tvorg.setText(preferences.getString("cusorg","name"));
        tvphone.setText(preferences.getString("cusphone","name"));


        final InvoiceDbQueries query = new InvoiceDbQueries(this);

        products = query.retrieveProduct(preferences.getString("cusname","Daniel Mwangi"));

        int pos = 0;
        int cost = 0;
        int total = 0;

        Log.e(TAG, "onCreate: Product length");


        while (pos<products.length){
            Log.e(TAG, "onCreate:  Contructing the message");
            message += "\n"+(pos+1)+". " + products[pos].getProdName() + " \t" + "@ Sh. "+String.valueOf(products[pos].getQuantity())+ " \t" + String.valueOf(products[pos].getPrice()) + "\n";
            cost = products[pos].getQuantity() * products[pos].getPrice();
            total = total + cost;
            pos++;
        }

        message += "\n Total Price: \t"+total;

        rvProducts.setHasFixedSize(true);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(new ProductBillAdapter(query.retrieveProduct(preferences.getString("cusname","Daniel Mwangi")),getApplicationContext()));
        rvProducts.setItemAnimator(new DefaultItemAnimator());

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: This is the message to be sent\n"+message );
                query.updateStatus(tvphone.getText().toString());
                sendMail(message, tvemail.getText().toString());

            }
        });
    }

    public void sendMail(String msg, String email){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "INVOICE");
        i.putExtra(Intent.EXTRA_TEXT   , msg);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
