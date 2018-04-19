package com.dannextech.apps.invoiceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by amoh on 12/28/2017.
 */

public class CustomerBillAdapter extends RecyclerView.Adapter<CustomerBillAdapter.ViewHolder> {

    CustomerBillModel [] customer;
    Context context;

    public CustomerBillAdapter(CustomerBillModel[] customerBillModels, Context applicationContext) {
        customer = customerBillModels;
        context = applicationContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_details,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(customer[position].getName());
        //holder.tvStatus.setText(customer[position].getSentStatus());
        holder.tvDate.setText(customer[position].getDate());
        holder.tvBill.setText("Bill "+customer[position].getBillId());

        holder.cvBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                final SharedPreferences.Editor editor = preferences.edit();

                editor.putString("cusname",customer[position].getName());
                editor.putString("cusphone",customer[position].getPhone());
                editor.putString("cusemail",customer[position].getEmail());
                editor.putString("cusorg",customer[position].getOrg());
                editor.apply();

                v.getContext().startActivity(new Intent(context,Bill.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return customer.length == 0 ? 0 : customer.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus, tvBill, tvDate;
        CardView cvBill;
        public ViewHolder(View itemView) {
            super(itemView);

            tvBill = (TextView) itemView.findViewById(R.id.tvBill);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            //tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            tvName = (TextView) itemView.findViewById(R.id.tvName);

            cvBill = (CardView) itemView.findViewById(R.id.cvBills);
        }
    }
}
