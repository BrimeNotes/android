package com.procleus.brime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ujjwal on 07-07-2016.
 */
public class AccountInfoFragment extends Fragment {
    private static String url;
    EditText Email;
    SharedPreferences sharedPreferences = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = this.getActivity().getSharedPreferences("com.procleus.brime", Context.MODE_PRIVATE);
        final View v =inflater.inflate(R.layout.account_info_layout, container, false);
        Email = (EditText) v.findViewById(R.id.Email);
        final String name = sharedPreferences.getString("emailpref", "Guest");
        Email.setText(name);
        url = "http://api.brime.tk/ShowUser.php?email=" + name;
        new JSONParse(v).execute();
        return v;
    }
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        View v;
        protected JSONParse(View v) {
            this.v = v;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                json = json.getJSONObject("user");
                String firstName = json.getString("firstName");
                String lastName = json.getString("lastName");
                EditText first_name = (EditText) v.findViewById(R.id.first_name);
                EditText last_name = (EditText) v.findViewById(R.id.last_name);
                first_name.setText(firstName);
                last_name.setText(lastName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
