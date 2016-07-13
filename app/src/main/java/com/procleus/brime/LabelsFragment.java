package com.procleus.brime;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Ujjwal on 07-07-2016.
 */
public class LabelsFragment extends Fragment {

    private ArrayList<String> labelsRetrieved;
    private static ListView listView;
    EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Labels");
        final View v =inflater.inflate(R.layout.labels_gragment, container, false);
        final Notes tn =new Notes(getActivity());

        labelsRetrieved = new ArrayList<String>();
        labelsRetrieved=tn.retrieveLabel();

        listView = (ListView)v.findViewById(R.id.listLabel);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, labelsRetrieved);
        listView.setAdapter(arrayAdapter);
        ImageButton addLabelBtn = (ImageButton)v.findViewById(R.id.addLabelBtn);
        addLabelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLabelFunc(v,tn);
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
    public void addLabelFunc(View v,Notes nt) {
        editText = (edittext) v.findViewById(R.id.addLabelInput);
        String ed = editText.getText().toString().trim();
        if (ed.isEmpty()) {
            editText.setError("Provide a Label Name");
        } else {
            nt.insertLabel(editText.getText().toString());
            Log.i("Bless", editText.getText().toString());
            ArrayList<String> labelsRetrieved = new ArrayList<String>();
            labelsRetrieved = nt.retrieveLabel();
            listView = (ListView) getView().findViewById(R.id.listLabel);
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, labelsRetrieved);

            listView.setAdapter(arrayAdapter);

        }
    }

}