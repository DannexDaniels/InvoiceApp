package com.dannextech.apps.invoiceapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 2/2/18.
 */

public class ProductBillAdapter extends RecyclerView.Adapter<ProductBillAdapter.ViewHolder> {

    ProductBillModel[] products;
    Context context;
    public ProductBillAdapter(ProductBillModel[] cusnames, Context applicationContext) {
        products = cusnames;
        context = applicationContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_detail,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: "+products[position].getProdName());
        Log.i(TAG, "onBindViewHolder: "+products[position].getQuantity());
        Log.i(TAG, "onBindViewHolder: "+products[position].getPrice());
        holder.tvProdName.setText(products[position].getProdName());
        holder.tvQuant.setText("@ Sh. "+String.valueOf(products[position].getQuantity()));
        holder.tvPrice.setText(String.valueOf(products[position].getPrice()));
    }

    @Override
    public int getItemCount() {
        return products.length == 0 ? 0 : products.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuant, tvProdName, tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPrice = (TextView) itemView.findViewById(R.id.tvProdPrice);
            tvProdName = (TextView) itemView.findViewById(R.id.tvProdName);
            tvQuant = (TextView) itemView.findViewById(R.id.tvProdQuantity);
        }
    }
}
