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

/**
 * Created by Utkarsh on 07-07-2016.
 */
public class SettingsFragment extends Fragment {

    public static String[] settingsOptions = new String[]{"AccountInfo","ChangePassword","ShareApp","SignOut"};
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
             switch (position)
             {
                 case 0:
                     Intent i = new Intent(v.getContext(),SettingsClickedAcitvity.class);
                     getActivity().startActivity(i);
                     break;

                 case 1:
                     Intent i1 = new Intent(getActivity(),SettingsClickedActivity2.class);
                     getActivity().startActivity(i1);break;

                 case 2:
                     Intent i2 = new Intent(v.getContext(),SettingsClickedActivity3.class);
                     getActivity().startActivity(i2);

                 case 3:
                     Intent i3 = new Intent(v.getContext(),SettingsClickedActivity4.class);
                     getActivity().startActivity(i3);
             }

            }
        });



        // Inflate the layout for this fragment
        return v;
    }
}
