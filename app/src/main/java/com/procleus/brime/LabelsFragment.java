package com.procleus.brime;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Ujjwal on 07-07-2016.
 */
public class LabelsFragment extends Fragment {

    private ArrayList<String> labelsRetrieved;
    private static ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Labels");
        View v =inflater.inflate(R.layout.labels_gragment, container, false);
        final Notes tn =new Notes(getActivity());


        labelsRetrieved = new ArrayList<String>();
        labelsRetrieved=tn.retrieveLabel();

        listView = (ListView)v.findViewById(R.id.listLabel);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, labelsRetrieved);
        listView.setAdapter(arrayAdapter);
        FloatingActionButton fab_listView = (FloatingActionButton)v.findViewById(R.id.fab_label);

        fab_listView.animate().translationX(-500f).start();
        fab_listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Dialog Work......


                final Dialog dialog = new Dialog(getActivity());

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_label);



                dialog.show();

                final Button negative = (Button) dialog.findViewById(R.id.btn_no);
                final Button positive = (Button) dialog.findViewById(R.id.btn_yes);




                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ///*******No Button
                        dialog.dismiss();


                    }
                });


                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText editText = (EditText)dialog.findViewById(R.id.label_editText);
                        tn.insertLabel(editText.getText().toString());
                        Log.i("Bless",editText.getText().toString());
                        ArrayList<String> labelsRetrieved = new ArrayList<String>();
                        labelsRetrieved=tn.retrieveLabel();

                        listView = (ListView)getView().findViewById(R.id.listLabel);
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, labelsRetrieved);
                        listView.setAdapter(arrayAdapter);
                        dialog.dismiss();
                    }
                });


            }
        });

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        listView = (ListView)getView().findViewById(R.id.listLabel);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, labelsRetrieved);
        listView.setAdapter(arrayAdapter);
    }
}
