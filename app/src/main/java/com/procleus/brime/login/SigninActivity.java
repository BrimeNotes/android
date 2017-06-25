package com.procleus.brime.login;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.procleus.brime.ui.MainActivity;
import com.procleus.brime.R;
import com.procleus.brime.utils.CustomButton;
import com.procleus.brime.utils.CustomEditText;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;


public class SigninActivity extends AppCompatActivity {
    public static final String PREF = "com.procleus.brime";
    public static final String emailpref = "null";
    public static final String passwordpref = "nopassKey";
    public static final String loggedin = "IsLoggedIn";
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";
    public SharedPreferences session;
    private String msg;
    Location mLastLocation = null;
    String longi, lati;
    ProgressDialog progressDialog;
    int responseOp;
    CustomButton b ;
    CustomEditText etun, etpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AwesomeValidation mAwesomeValidation = new AwesomeValidation(UNDERLABEL);
        mAwesomeValidation.setContext(this);  // mandatory for UNDERLABEL style

        setContentView(R.layout.activity_signin);
        etun = (CustomEditText) findViewById(R.id.editText);
        etpass = (CustomEditText) findViewById(R.id.editText2);
        CustomButton stl_btn = (CustomButton) findViewById(R.id.stl_btn);

        stl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SigninActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        CustomButton btlog = (CustomButton) findViewById(R.id.log_btn);
        btlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cvalidate(etun.getText().toString(),etpass.getText().toString())){
                    return  ;
                }
                progressDialog = new ProgressDialog(SigninActivity.this, R.style.Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Login ...");
                logIn( etun.getText().toString(),etpass.getText().toString());

            }
        });
    }

    /*     * Converts Byte to Hexadecimal
     * @param data Byte data
     * @return hexadecimal data

    public static String convertByteToHex(byte data[]) {
        StringBuffer hexData = new StringBuffer();
        for (int byteIndex = 0; byteIndex < data.length; byteIndex++) {
            hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));
        }
        return hexData.toString();
    }
    */
    /*
     * Encrypts a text
     * @param textToHash text to be encrypted
     * @return encrypted text

    public static String hashText(String textToHash) {
        try {
            final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            sha512.update(textToHash.getBytes());
            return convertByteToHex(sha512.digest());
        } catch (Exception e) {
            return textToHash;
        }
    }*/

    public void logIn(String user, String pass) {
        progressDialog.show();
        final String userid=user;
        final String password=pass;

        String loginurl = "http://brime.ml/user/login";
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);


        StringRequest req = new StringRequest(Request.Method.POST, loginurl,
              new Response.Listener<String>()
        //JsonObjectRequest req = new JsonObjectRequest(loginurl, new JSONObject(params), new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                         Log.d("Response:mba", response);
                        try{
                            JSONObject reader= new JSONObject(response);
                            msg = reader.get("message").toString();
                            if (msg.equals("User logged in successfully.")) {
                                responseOp = 1;

                                session = getSharedPreferences(PREF, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = session.edit();
                                //editor.putString(ID, );
                                editor.putString("emailpref", userid);
                                editor.putString("passwordpref",password);
                                editor.putBoolean("loggedin", true);
                                editor.apply();
                                onLogInSuccess();
                            }

                            Log.i("Message",msg);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                         NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                                Log.i("fuckoff:",obj.toString());
                                Toast.makeText(getApplicationContext(),"Login Failed !", Toast.LENGTH_LONG).show();
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }
                        progressDialog.dismiss();
                    }

                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("uid", userid);
                params.put("password",password);
                return params;
            }
        };
        requestQueue.add(req);

    }

    public boolean cvalidate(String uid,String pass) {
        boolean legal = true;
        if (uid.isEmpty()) {
            legal = false;
            etun.setError("Username can not be empty");
        } else {
            etun.setError(null);
        }
        if(pass.isEmpty()){
            legal=false;
            etpass.setError("Password can not be empty");
        }else{
            etpass.setError(null);
        }
        return legal;
    }

     public void onLogInSuccess() {
         notif(1);
         Toast.makeText(getBaseContext(), "Logged in successfully", Toast.LENGTH_LONG).show();
         finish();
         Intent i = new Intent(SigninActivity.this, MainActivity.class);
         startActivity(i);
    }
    public void onLogInFailed(String error) {
        notif(2);
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
    public void notif(int id) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.logo);
        if(id==1)
            mBuilder.setContentTitle("Log in Successful");
        else
            mBuilder.setContentTitle("Log in Failed");
        mBuilder.setContentText("Logged in from " + longi + " , " + lati);
        mBuilder.setSmallIcon(R.drawable.logo);
        int mNotificationId = 4848;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }



}
