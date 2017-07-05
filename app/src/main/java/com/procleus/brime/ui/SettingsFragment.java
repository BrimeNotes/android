package com.procleus.brime.ui;

import android.os.Bundle;
import android.preference.PreferenceFragment;


import com.procleus.brime.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.pref_settings);
    }
}





//OLD SettingsFragment
//public class SettingsFragment extends Fragment {


//    ArrayList<String> settingsOptions = new ArrayList<String>();
//    SharedPreferences sharedPreferences = null;
// //   public static String[] settingsOptions = new String[]{"Account Info","Change Password","Share App","Sign Out", "About Us"};
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//       final View v = inflater.inflate(R.layout.settings_fragment,container,false);
//        ListView lv = (ListView)v.findViewById(R.id.listView);
//        ((MainActivity) getActivity()).setActionBarTitle("Settings");
//        ((MainActivity) getActivity()).showFloatingActionButton(false);
//        sharedPreferences = this.getActivity().getSharedPreferences("com.procleus.brime", Context.MODE_PRIVATE);
//        settingsOptions.add("Account Info");
//        settingsOptions.add("Change Password");
//        settingsOptions.add("Share App");
//        settingsOptions.add("About Us");
//        Boolean bool = sharedPreferences.getBoolean("loggedin", false);
//        if (bool == false) {
//            settingsOptions.add("Log In");
//        } else {
//            settingsOptions.add("SignOut");
//        }
//
//        ArrayAdapter<String> ar = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,settingsOptions);
//        lv.setAdapter(ar);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (settingsOptions.get(4).equals("Log In")) {
//                    Intent i = new Intent(v.getContext(), SigninActivity.class);
//                    startActivity(i);
//                } else {
//                    Intent intent = new Intent(v.getContext(), SettingsClickedActivity.class);
//                    String x = String.valueOf(position);
//                    intent.putExtra("a", x);
//                    startActivity(intent);
//                }
//            }
//        });
//        return v;
//    }
//}
