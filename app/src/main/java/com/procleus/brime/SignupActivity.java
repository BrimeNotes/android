package com.procleus.brime;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;


public class SignupActivity extends AppCompatActivity {
    private static final int uniqueid=456;
    NotificationCompat.Builder notif;
    edittext etname, etemail, etpass;
    buttons btnsign;
    textview loginlink;
    ProgressDialog progressDialog;
    int responseOp;
    private GoogleApiClient client;


    /**
     * Converts Byte to Hexadecimal
     * @param data Byte data
     * @return hexadecimal data
     */
    public static String convertByteToHex(byte data[]) {
        StringBuffer hexData = new StringBuffer();
        for (int byteIndex = 0; byteIndex < data.length; byteIndex++) {
            hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));
        }
        return hexData.toString();
    }

    /**
     * Encrypts a text
     * @param textToHash text to be encrypted
     * @return encryted text
     */
    public static String hashText(String textToHash) {
        try {
            final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            sha512.update(textToHash.getBytes());
            return convertByteToHex(sha512.digest());
        } catch (Exception e) {
            return textToHash;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Binding resources
        etname = (edittext) findViewById(R.id.input_name);
        etemail = (edittext) findViewById(R.id.input_email);
        etpass = (edittext) findViewById(R.id.input_password);
        loginlink = (textview) findViewById(R.id.link_login);
        btnsign = (buttons) findViewById(R.id.btn_signup);
        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //link login activity
                Intent isignu = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(isignu);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Registers a user with valid credentials
     */
    public void signup() {
        String name = etname.getText().toString();
        String email = etemail.getText().toString();
        String password = etpass.getText().toString();
        if (!validate(name, email, password)) {
            onSignupFailed("Data Validation Error");
            return;
        }
        btnsign.setEnabled(false);
        progressDialog = new ProgressDialog(SignupActivity.this, R.style.Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account");
        progressDialog.show();
        new PostClass(this).execute();
        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if(responseOp==1) {
                        onSignupSuccess();
                    } else {
                        fnotif();
                        Log.i("Function called0", "yep");
                        onSignupFailed("Sign up Error, Please try again later");
                    }
                    progressDialog.dismiss();
                }
            }, 3000);
    }

    /**
     * Validates the inputted user's credentials on Signup
     * @param name  a non empty non-numeric string of length greater than or equal to 3.
     * @param email a non empty email address
     * @param password a non empty string with minimum length of 8 characters
     * @return true if the credentials are validated else false
     */
    public boolean validate(String name, String email, String password) {
        boolean valid = true;

        if (name.isEmpty() || name.length() < 3) {
            etname.setError("at least 3 characters");
            valid = false;
        } else {
            etname.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etemail.setError("enter a valid email address");
            valid = false;
        } else {
            etemail.setError(null);
        }

        if (password.isEmpty() || password.length() < 8) {
            etpass.setError("password length must be greater than 8 & alphanumeric characters");
            valid = false;
        } else {
            etpass.setError(null);
        }

        return valid;
    }

    /**
     * Toast a successful signup message.
     * Shifts from Signup activity to Signin activity
     */
    public void onSignupSuccess() {
        btnsign.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(), "Account created successfully, Please verify your email", Toast.LENGTH_LONG).show();
        notif();
        Intent i = new Intent(SignupActivity.this, SigninActivity.class);
        startActivity(i);
        finish();
    }

    public void notif() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Registration Successful");
        mBuilder.setContentText("Thanks for choosing Brime, Please verify your email.");
        mBuilder.setSmallIcon(R.drawable.logo);
        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    public void fnotif() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Registration Failed");
        mBuilder.setContentText("Account registration failed, please try again later");
        mBuilder.setSmallIcon(R.drawable.logo);
        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        Log.i("Fucntion ran", "yes");
    }


    /**
     * Toast an error to the screen which caused a failed signup
     * @param error the error string received when there is a failed signup
     */
    public void onSignupFailed(String error) {
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();
        btnsign.setEnabled(true);
    }

    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;
        String email = etemail.getText().toString();
        String password = etpass.getText().toString();
        String username = etname.getText().toString();
        public PostClass(Context c) {

            this.context = c;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                URL url = new URL("http://api.brime.tk/register");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String urlParameters = "email=" + URLEncoder.encode(email, "UTF-8") + "&p=" + hashText(password) + "&username=" + username;
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
                Log.i("response", responseOutput.toString());
                try {
                    JSONObject reader = new JSONObject(responseOutput.toString());
                    String message = reader.get("message").toString();
                    Log.i("Message", message);
                    if (message.equals("Registered Successfully")) {
                        responseOp = 1;
                    } else {
                        responseOp = 2;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
