package com.example.android.requests.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.requests.R;
import com.facebook.share.model.ShareLinkContent;


public class Share extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Share() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.share, container, false);
        ImageView whatsapp = (ImageView)rootview.findViewById(R.id.whatseapp);
        ImageView message = (ImageView)rootview.findViewById(R.id.messaege);
        ImageView facebook = (ImageView)rootview.findViewById(R.id.facebook);
        ImageView twitter = (ImageView)rootview.findViewById(R.id.twitter);
        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);

            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smsBody="Sms Body";
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra("sms_body", smsBody);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .build();

            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootview;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
