package com.procleus.brime;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class SigninActivity extends AppCompatActivity {
    public static final String PREF = "com.procleus.brime";
    public static final String emailpref = "null";
    public static final String passwordpref = "nopassKey";
    public static final String loggedin = "IsLoggedIn";
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";
    public SharedPreferences session;
    Location mLastLocation = null;
    String longi, lati;
    ProgressDialog progressDialog;
    int responseOp;
    buttons b ;
    edittext etun, etpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);
        etun = (edittext) findViewById(R.id.editText);
        etpass = (edittext) findViewById(R.id.editText2);
        buttons stl_btn = (buttons) findViewById(R.id.stl_btn);
        stl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SigninActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        buttons btlog = (buttons) findViewById(R.id.log_btn);
        btlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

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
     * @return encrypted text
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

    public void logIn() {
        new PostClass(this).execute();
    }

     public void onLogInSuccess() {
         notif();
         Toast.makeText(getBaseContext(), "Logged in successfully", Toast.LENGTH_LONG).show();
         finish();
         Intent i = new Intent(SigninActivity.this, MainActivity.class);
         startActivity(i);
    }
    public void onLogInFailed(String error) {
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();

    }

   /* @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            longi = String.valueOf(mLastLocation.getLatitude());
            lati = String.valueOf(mLastLocation.getLongitude());
        }

    }
*/
    public void notif() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Log in Successful");
        mBuilder.setContentText("Logged in from " + longi + " , " + lati);
        mBuilder.setSmallIcon(R.drawable.logo);
        int mNotificationId = 4848;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    public void fnotif() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Log in Failed");
        mBuilder.setContentText("Logged in tried from " + longi + " , " + lati);
        mBuilder.setSmallIcon(R.drawable.logo);
        int mNotificationId = 4848;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

  /*  @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
*/
    private class PostClass extends AsyncTask<String, Void, Void> {
        private final Context context;
        //data for login
        String email = etun.getText().toString();
        String password = etpass.getText().toString();
        public PostClass(Context c) {
            this.context = c;
        }
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(SigninActivity.this, R.style.Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Login ...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL("http://api.brime.tk/login");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String urlParameters = "email=" + URLEncoder.encode(email, "UTF-8") + "&p=" + hashText(password);
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
                    if (message.equals("Logged in")) {
                        responseOp = 1;

                        session = getSharedPreferences(PREF, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = session.edit();
                        //editor.putString(ID, );
                        editor.putString("emailpref", email);
                        editor.putString("passwordpref", hashText(password));
                        editor.putBoolean("loggedin", true);
                        editor.apply();
                    } else if (message.equals("user is not verified")){
                        responseOp = 3;
                    } else {
                        responseOp = 2;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                br.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (responseOp==1) {
                notif();
                onLogInSuccess();
            } else if (responseOp == 3) {
                fnotif();
                onLogInFailed("Email Id not verified");

            } else {
                fnotif();
                onLogInFailed("Data Validation error");
            }
            progressDialog.dismiss();
        }
    }
}
