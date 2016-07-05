package com.procleus.brime;

/**
 * Created by suraj on 04-07-2016.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignupActivity extends AppCompatActivity {

    edittext etname,etemail,etpass;
    buttons btnsign;
    textview loginlink;
    //Added hash function from maxsam4 commit
     public final static String encryptSHA512(String target) {
             try {
                    MessageDigest sh = MessageDigest.getInstance("SHA-512");
                    sh.update(target.getBytes());
                    StringBuffer sb = new StringBuffer();
                    for (byte b : sh.digest()) sb.append(Integer.toHexString(0xff & b));
                    return sb.toString();
                 } catch (NoSuchAlgorithmException e) {
                     throw new RuntimeException(e);
                 }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Binding resources
        etname=(edittext)findViewById(R.id.input_name);
        etemail=(edittext)findViewById(R.id.input_email);
        etpass=(edittext)findViewById(R.id.input_password);
        loginlink=(textview)findViewById(R.id.link_login);
        btnsign=(buttons)findViewById(R.id.btn_signup);
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

    }

    public void signup(){

        if (!validate()) {
            onSignupFailed();
            return;
        }
        btnsign.setEnabled(false);
        final ProgressDialog progressDialog=new ProgressDialog(SignupActivity.this,R.style.AppTheme2);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account");
        progressDialog.show();
        String name = etname.getText().toString();
        String email = etemail.getText().toString();
        String password = etpass.getText().toString();

        // TODO: Signup code

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        onSignupSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public boolean validate() {
        boolean valid = true;

        String name = etname.getText().toString();
        String email = etemail.getText().toString();
        String password = etpass.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            etname.setError("at least 3 characters");
            valid = false;
        } else {
            etname.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
    public void onSignupSuccess() {
        btnsign.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(getBaseContext(), "Account created successfully", Toast.LENGTH_LONG).show();
        finish();
        Intent i = new Intent(SignupActivity.this, SigninActivity.class);
        startActivity(i);

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign-up failed", Toast.LENGTH_LONG).show();

        btnsign.setEnabled(true);
    }
}
