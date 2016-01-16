package com.example.android.requests.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.requests.R;
import com.example.android.requests.activities.NewAddress;
import com.example.android.requests.models.Address;
//import com.example.android.requests.utils.EditAddressClass;

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
    Context c1;


    public AddressAdapter (Context c, List<Address> data){
            layoutInflater = layoutInflater.from(c);
        this.c1 =c;
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
       final Address adess = data.get(position);
        holder.t1.setText(adess.flatno);
        holder.t2.setText(adess.location);
        holder.t3.setText(adess.nearby);
        holder.im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG", "i GOT CLICKLED");
                int id = adess.id;
                Intent newAddress = new Intent(c1, com.example.android.requests.activities.NewAddress.class);
                newAddress.putExtra("address_id", id);
                c1.startActivity(newAddress);
            }

        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder  {
        TextView t1;
        TextView t2;
        TextView t3;
        ImageView im1;
        Context c;
        public AddressViewHolder(View itemView) {
            super(itemView);
            this.c=itemView.getContext();
            t1 = (TextView)itemView.findViewById(R.id.t1);
            t2 = (TextView)itemView.findViewById(R.id.t2);
            t3 = (TextView)itemView.findViewById(R.id.t3);
            im1 = (ImageView)itemView.findViewById(R.id.edit_address_icon);
//            im1.setOnClickListener(this);

        }

//        @Override
//        public void onClick(View v) {
//            String fragmnet = "Edit Address";
//            Intent newAddress = new Intent(c, NewAddress.class);
//            c.startActivity(newAddress);
//
//        }

//        @Override
//        public void onClick(View v) {
//            Log.i("TAG", "i GOT CLICKLED");
////            Intent newAddress = new Intent(this, com.example.android.requests.activities.NewAddress.class);
////            startActivity(newAddress);
//        }
    }

}
