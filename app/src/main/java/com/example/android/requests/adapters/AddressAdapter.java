package com.example.android.requests.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.requests.R;
import com.example.android.requests.models.Address;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by tuljain on 1/15/2016.
 */
public class AddressAdapter extends  RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private LayoutInflater layoutInflater;
    List<Address> data = Collections.emptyList();


    public AddressAdapter (Context c, List<Address> data){
            layoutInflater = layoutInflater.from(c);
        this.data = data;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_address, parent, false);
        AddressViewHolder addressViewHolder = new AddressViewHolder(view);
        return addressViewHolder;
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        Address address = data.get(position);
        holder.t1.setText(address.flatno);
        holder.t2.setText(address.location);
        holder.t3.setText(address.nearby);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView t1;
        TextView t2;
        TextView t3;
        public AddressViewHolder(View itemView) {
            super(itemView);
            t1 = (TextView)itemView.findViewById(R.id.t1);
            t2 = (TextView)itemView.findViewById(R.id.t2);
            t3 = (TextView)itemView.findViewById(R.id.t3);

        }
    }

}
