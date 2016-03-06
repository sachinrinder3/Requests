package com.example.android.requests.adapters;



import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

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

//            ProgressDialog progressDialog = ProgressDialog.show(c, "Loading...",
//                    "Editing Photo, please wait...", false, false);
//            // This dialog can't be canceled by pressing the back key
//            progressDialog.setCancelable(false);
//            // This dialog isn't indeterminate
//            progressDialog.setIndeterminate(false);
//            // The maximum number of items is 100
//            progressDialog.setMax(100);
//            // Set the current progress to zero
//            progressDialog.setProgress(0);
//            // Display the progress dialog
//            progressDialog.show();

//            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.order_dialog,null);
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.width = WindowManager.LayoutParams.FILL_PARENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            final PopupWindow pw = new PopupWindow(layout, lp.width, lp.height, true);
//            pw.showAtLocation(layout, Gravity.CENTER_VERTICAL, 0, 0);
//            AppCompatImageButton btncancel = (AppCompatImageButton) layout.findViewById(R.id.icon_close);
//            AppCompatTextView servicename = (AppCompatTextView) layout.findViewById(R.id.icon_text);
//            servicename.setText(yourOrderServiceName.getText());
//            AppCompatTextView price = (AppCompatTextView) layout.findViewById(R.id.price);
//            price.setText(yourOrderprice.getText());
//            btncancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    pw.dismiss();
//                }
//            });



//            final AppCompatDialog dialog = new AppCompatDialog(c);
//            //final PopupWindowCompat dialog = new PopupWindowCompat(c);
//            dialog.setContentView(R.layout.order_dialog);
//            dialog.getWindow().setBackgroundDrawable( new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            Window window = dialog.getWindow();
//            lp.copyFrom(window.getAttributes());
//            AppCompatActivity activity = (AppCompatActivity) c;
//            Display display = activity.getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            lp.width = size.x;
//            lp.height = size.y;
//            //window.setAttributes(lp);
//
//            AppCompatImageButton icon_close = (AppCompatImageButton) dialog.findViewById(R.id.icon_close);
//            icon_close.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();
        }
    }

}
