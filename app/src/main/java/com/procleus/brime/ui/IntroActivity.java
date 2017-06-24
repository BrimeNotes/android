package com.procleus.brime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.procleus.brime.R;
import com.procleus.brime.utils.CustomButton;
import com.procleus.brime.login.SigninActivity;

public class IntroActivity extends AppCompatActivity {
    CustomButton signinBtn;
    CustomButton getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        getStarted = (CustomButton) findViewById(R.id.getStarted);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, GetStartedActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signinBtn =(CustomButton)findViewById(R.id.signinbtn) ;
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroActivity.this,SigninActivity.class);
                startActivity(i);
            }
        });
    }
}
