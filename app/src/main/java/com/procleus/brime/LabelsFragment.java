package com.procleus.brime;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Ujjwal on 07-07-2016.
 */
public class LabelsFragment extends Fragment {

    private static ListView listView;
    public static String[] labels = new String[]{"Inspiration", "Personal", "Work"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle("Labels");
        View v =inflater.inflate(R.layout.labels_gragment, container, false);
        //Button addLabel = (Button) v.findViewById(R.id.add_label);
        /*addLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddLabel.class);
                startActivity(intent);
            }
        });*/
        listView = (ListView)v.findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, labels);
        listView.setAdapter(arrayAdapter);
        return v;
    }
}
