package com.procleus.brime;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.google.android.gms.common.api.GoogleApiClient;

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

public class SigninActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";
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
                finish();
            }
        });

        buttons btlog=(buttons)findViewById(R.id.log_btn);
        btlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });


        //FBcode
        final List<String> permissionNeeds = Arrays.asList("user_friends","user_photos","email");
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager= CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                if(profile!=null)

                {
                    Toast.makeText(getApplicationContext(), profile.getFirstName()+profile.getLastName(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(SigninActivity.this,acct.getDisplayName(),Toast.LENGTH_LONG).show();
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
                        if(responseOp==1) {
                            onLogInSuccess();
                        }else
                             onLogInFailed("Data Validation error");
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

    public void autoLogIn() {

        new AutoPostClass(this).execute();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (responseOp == 1) {
                            onLogInSuccess();
                        } else
                            onLogInFailed("Data Validation error");
                        progressDialog.dismiss();
                    }
                }, 3000);

    }

     public void onLogInSuccess() {
        Toast.makeText(getBaseContext(), "Logged in successfully", Toast.LENGTH_LONG).show();
        finish();
        Intent i = new Intent(SigninActivity.this, MainActivity.class);
        startActivity(i);

    }

    public void onLogInFailed(String error) {
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();

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

                URL url = new URL("http://api.brime.tk/login.php");
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
                if (responseOutput.toString().replaceAll(" ", "").equals("Loggedin")) {
                    responseOp=1;
                    //TODO Save data in shared pref
                } else {
                    responseOp=2;    
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

    private class AutoPostClass extends AsyncTask<String, Void, Void> {

        private final Context context;
        //data for login
        //TODO : Fetch fuckin data from fuckin shared pref
        String email;
        String password;

        public AutoPostClass(Context c) {

            this.context = c;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {

            try {

                URL url = new URL("http://api.brime.tk/login.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String urlParameters = "email=" + URLEncoder.encode(email, "UTF-8") + "&p=" + password;
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
                if (responseOutput.toString().replaceAll(" ", "").equals("Loggedin")) {
                    responseOp = 1;
                } else {
                    responseOp = 2;
                    //TODO Delete data from shared pref
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
