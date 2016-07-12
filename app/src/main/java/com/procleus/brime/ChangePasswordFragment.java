package com.procleus.brime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

/**
 * Created by Ujjwal on 07-07-2016.
 */
public class ChangePasswordFragment extends Fragment {
    Button buttonChange;
    EditText oldPassword;
    EditText newPassword;
    String oldPasswordValue;
    String newPasswordValue;
    String name;
    SharedPreferences sharedPreferences;
    int responseOp;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = this.getActivity().getSharedPreferences("com.procleus.brime", Context.MODE_PRIVATE);
        View v = inflater.inflate(R.layout.change_password_layout, container, false);
        name = sharedPreferences.getString("emailpref", "Guest");
        buttonChange = (Button) v.findViewById(R.id.button);
        oldPassword = (EditText) v.findViewById(R.id.old_password);
        newPassword = (EditText) v.findViewById(R.id.new_password);
        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPasswordValue = oldPassword.getText().toString();
                oldPasswordValue = hashText(oldPasswordValue);
                newPasswordValue = hashText(newPassword.getText().toString());
                progressDialog = new ProgressDialog(getActivity(), R.style.Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Changing Password...");
                progressDialog.show();
                new ChangePassword(v).execute();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                if(responseOp==1) {
                                    Toast.makeText(getContext(), "Changed Successfully", Toast.LENGTH_LONG).show();
                                    SettingsClickedActivity activity = (SettingsClickedActivity) getActivity();
                                    activity.Sign_Out();

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

    private class ChangePassword extends AsyncTask<String, Void, Void> {

        View v;
        public ChangePassword(View v) {
            this.v = v;
        }
        protected void onPreExecute() { }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL("http://api.brime.tk/ChangePassword.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String urlParameters = "email=" + URLEncoder.encode(name, "UTF-8") +
                        "&op=" + oldPasswordValue +
                        "&np=" + newPasswordValue;
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
                if (responseOutput.toString().contains("Changed")) {
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
    public static String hashText(String textToHash) {
        try {
            final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            sha512.update(textToHash.getBytes());
            return convertByteToHex(sha512.digest());
        } catch (Exception e) {
            return textToHash;
        }
    }
    public static String convertByteToHex(byte data[]) {
        StringBuffer hexData = new StringBuffer();
        for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
            hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));

        return hexData.toString();
    }
}
