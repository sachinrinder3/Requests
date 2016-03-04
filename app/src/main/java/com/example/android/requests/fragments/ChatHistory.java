package com.example.android.requests.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.requests.R;
import com.example.android.requests.adapters.AddressAdapter;
import com.example.android.requests.adapters.ChatHistoryAdapter;
import com.example.android.requests.models.Address;
import com.example.android.requests.models.ChatMessage;
import com.example.android.requests.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;


public class ChatHistory extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private ChatHistoryAdapter chatHistoryAdapter;

    private OnFragmentInteractionListener mListener;

    public ChatHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatHistory newInstance(String param1, String param2) {
        ChatHistory fragment = new ChatHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
//        builder.setTitle("AppCompatDialog");
//        builder.setMessage("Lorem ipsum dolor...");
//        builder.setPositiveButton("OK", null);
//        builder.setNegativeButton("Cancel", null);
        View v = inflater.inflate(R.layout.fragment_chat_history, container, false);
//        builder.show();
        recyclerView = (RecyclerView)v.findViewById(R.id.chat_history_list);
        recyclerView.setHasFixedSize(true);
        chatHistoryAdapter= new ChatHistoryAdapter(getActivity(), getdata());
        recyclerView.setAdapter(chatHistoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return v;
    }

    public List<com.example.android.requests.models.ChatHistory> getdata (){
        List<com.example.android.requests.models.ChatHistory> data = new ArrayList<>();

        DataBaseHelper dataBaseHelper;
        SQLiteDatabase sqLiteDatabase;
        dataBaseHelper = new DataBaseHelper(getContext());
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        String[] columns = {  "message_service"};

        Cursor c = sqLiteDatabase.query( true, "CHAT_TABLE", columns, null, null, "message_service",  null, "message_service",null);
        while (c.moveToNext()){
            String imagename = c.getString(0).toLowerCase() ;
            int res = getResources().getIdentifier(imagename, "drawable", getContext().getPackageName());
            com.example.android.requests.models.ChatHistory chatHistory = new com.example.android.requests.models.ChatHistory( c.getString(0), res);
            data.add(chatHistory);
        }
        return data;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
