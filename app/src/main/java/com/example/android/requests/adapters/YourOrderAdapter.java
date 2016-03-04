package com.example.android.requests.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.requests.R;
import com.example.android.requests.models.YourOrders;

import java.util.Collections;
import java.util.List;

/**
 * Created by tulsijain on 04/03/16.
 */
public class YourOrderAdapter extends  RecyclerView.Adapter<YourOrderAdapter.YourOrderAdapterViewHolder> {

    private LayoutInflater layoutInflater;
    List<YourOrders> data = Collections.emptyList();
    Context c1;

    public YourOrderAdapter (Context c, List<YourOrders> data){
        layoutInflater = layoutInflater.from(c);
        this.c1 =c;
        this.data = data;
    }



    @Override
    public YourOrderAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_order, parent, false);
        YourOrderAdapterViewHolder yourOrderAdapterViewHolder = new YourOrderAdapterViewHolder(view);
        return yourOrderAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(YourOrderAdapterViewHolder holder, int position) {
        final YourOrders yourOrders = data.get(position);
        holder.yourOrderServiceName.setText(yourOrders.getService());
        holder.yourOrderprice.setText(yourOrders.getPrice());
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public static class YourOrderAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        android.support.v7.widget.AppCompatTextView yourOrderServiceName;
        android.support.v7.widget.AppCompatTextView yourOrderprice;
        Context c;

        public YourOrderAdapterViewHolder(View itemView) {
            super(itemView);
            this.c = itemView.getContext();
            yourOrderServiceName = (android.support.v7.widget.AppCompatTextView) itemView.findViewById(R.id.your_order_servicename);
            yourOrderprice = (android.support.v7.widget.AppCompatTextView) itemView.findViewById(R.id.your_order_price);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
        }
    }

}
