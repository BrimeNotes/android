package com.procleus.brime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;

public class IntroActivity extends AppCompatActivity {
    buttons signinBtn;
    buttons getstartbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        getstartbtn=(buttons)findViewById(R.id.btn_getstart);
        //linked SignupActivity on Getstared button click until getstarted not implemt
        getstartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent igstar= new Intent(IntroActivity.this,SignupActivity.class);
                startActivity(igstar);
            }
        });
        signinBtn =(buttons)findViewById(R.id.signinbtn) ;
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroActivity.this,SigninActivity.class);
                startActivity(i);
            }
        });
    }
}
