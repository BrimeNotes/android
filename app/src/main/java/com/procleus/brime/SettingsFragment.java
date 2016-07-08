package com.procleus.brime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Utkarsh on 07-07-2016.
 */
public class SettingsFragment extends Fragment {

    public static String[] settingsOptions = new String[]{"AccountInfo","ChangePassword","ShareApp","SignOut"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.settings_fragment,container,false);
        ListView lv = (ListView)v.findViewById(R.id.listView);

        ArrayAdapter<String> ar = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,settingsOptions);
        lv.setAdapter(ar);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            /*    Intent i = new Intent(getContext(),SettingsClickedAcitvity.class);
                i.putExtra("Id",position);
                startActivity(i);*/

            }
        });

        // Inflate the layout for this fragment
        return v;
    }
}
