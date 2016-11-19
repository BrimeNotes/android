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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

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
import java.util.Arrays;
import java.util.List;

public class SigninActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
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
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;

    public static String convertByteToHex(byte data[]) {
        StringBuffer hexData = new StringBuffer();
        for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
            hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));

        return hexData.toString();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //login resource bind
        etun=(edittext)findViewById(R.id.editText);
        etpass=(edittext)findViewById(R.id.editText2);
        textview tv = (textview) findViewById(R.id.textView3);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SigninActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        textview tv2 = (textview) findViewById(R.id.textView4);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SigninActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

        //FBcode
        final List<String> permissionNeeds = Arrays.asList("user_friends","user_photos","email");
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager= CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                if(profile!=null){
                    //support local session in app when login through FB
                    session = getSharedPreferences(PREF,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=session.edit();
                    editor.putBoolean("loggedin",true);
                    editor.commit();
                    Intent i =new Intent(SigninActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException error) {
            }
        });

        buttons button =(buttons)findViewById(R.id.fb_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(SigninActivity.this,permissionNeeds);
            }
        });
        //FB_CodeEND
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                //.enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(LocationServices.API)
                .build();
        b = (buttons)findViewById(R.id.google_signin_btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        callbackManager.onActivityResult(requestCode,resultCode,data);//FB Data
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
          GoogleSignInAccount acct = result.getSignInAccount();
            //Toast.makeText(SigninActivity.this,acct.getDisplayName(),Toast.LENGTH_LONG).show();
            //support local session in app when login through FB
            session = getSharedPreferences(PREF,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=session.edit();
            editor.putBoolean("loggedin",true);
            editor.commit();
            Intent i = new Intent(SigninActivity.this, MainActivity.class);
            startActivity(i);
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    public void logIn() {
        progressDialog = new ProgressDialog(SigninActivity.this, R.style.Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login ...");
        progressDialog.show();
        new PostClass(this).execute();
        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
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
            }, 3000);
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
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            longi = String.valueOf(mLastLocation.getLatitude());
            lati = String.valueOf(mLastLocation.getLongitude());
        }

    }

    public void notif() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Log in Successful");
        mBuilder.setContentText("Logged in from " + longi + " , " + lati);
        mBuilder.setSmallIcon(R.drawable.logo);
        int mNotificationId = 4848;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    public void fnotif() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle("Log in Failed");
        mBuilder.setContentText("Logged in tried from " + longi + " , " + lati);
        mBuilder.setSmallIcon(R.drawable.logo);
        int mNotificationId = 4848;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class PostClass extends AsyncTask<String, Void, Void> {
        private final Context context;
        //data for login
        String email = etun.getText().toString();
        String password = etpass.getText().toString();
        public PostClass(Context c) {
            this.context = c;
        }
        protected void onPreExecute() {
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
    }
}
