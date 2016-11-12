package com.procleus.brime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AccountInfoFragment extends Fragment {
    private static String url;
    EditText Email;
    EditText first_name;
    EditText last_name;
    EditText birthDate;
    String name;
    String firstName = "";
    String lastName = "";
    String bdate;
    String updatedFirstName;
    String updatedLastName;
    String updatedBirthDate = "1996-06-25";
    SharedPreferences sharedPreferences = null;
    int responseOp;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = this.getActivity().getSharedPreferences("com.procleus.brime", Context.MODE_PRIVATE);
        final View v =inflater.inflate(R.layout.account_info_layout, container, false);
        Email = (EditText) v.findViewById(R.id.Email);
        name = sharedPreferences.getString("emailpref", "Guest");
        Email.setText(name);
        Email.setEnabled(false);
        url = "http://api.brime.tk/ShowUser.php?email=" + name;
        new JSONParse(v).execute();
        FloatingActionButton floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progressDialog = new ProgressDialog(getActivity(), R.style.Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Updating");
                progressDialog.show();
                updatedFirstName = first_name.getText().toString();
                updatedLastName = last_name.getText().toString();
                updatedBirthDate = birthDate.getText().toString();
                new UpdateUser(v).execute();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                if(responseOp==1) {
                                    Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }
        });
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
                firstName = json.getString("firstName");
                lastName = json.getString("lastName");
                bdate = json.getString("birthDate");
                first_name = (EditText) v.findViewById(R.id.first_name);
                last_name = (EditText) v.findViewById(R.id.last_name);
                birthDate = (EditText) v.findViewById(R.id.date_picker);
                first_name.setText(firstName);
                last_name.setText(lastName);
                birthDate.setText(bdate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class UpdateUser extends AsyncTask<String, Void, Void> {

        View v;
        public UpdateUser(View v) {
            this.v = v;
        }
        protected void onPreExecute() { }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL("http://api.brime.tk/UpdateUser.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String urlParameters = "email=" + URLEncoder.encode(name, "UTF-8") +
                                        "&firstName=" + updatedFirstName +
                                        "&lastName=" + updatedLastName +
                                        "&birthDate=" + updatedBirthDate;
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Brime Android App");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                //int responseCode = connection.getResponseCode();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                //Log.i("response", responseOutput.toString());
                if (responseOutput.toString().contains("Updated")) {
                    responseOp=1;
                    Log.i("op", responseOutput.toString());
                } else {
                    responseOp=2;
                    Log.i("op", responseOutput.toString());
                }
                br.close();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
}
