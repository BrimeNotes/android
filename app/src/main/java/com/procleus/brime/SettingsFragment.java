package com.procleus.brime;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Ujjwal on 07-07-2016.
 */
public class SettingsFragment extends Fragment {

    public static String[] settingsOptions = new String[]{"Account Info","Change Password","Share App","Sign Out", "FAQ", "About Us"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       final View v = inflater.inflate(R.layout.settings_fragment,container,false);
        ListView lv = (ListView)v.findViewById(R.id.listView);

        ArrayAdapter<String> ar = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,settingsOptions);
        lv.setAdapter(ar);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(v.getContext(), SettingsClickedActivity.class);
                String x = String.valueOf(position);
                intent.putExtra("a", x);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}
