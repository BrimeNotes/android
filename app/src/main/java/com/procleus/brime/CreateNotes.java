package com.procleus.brime;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CreateNotes extends AppCompatActivity {
    
    SharedPreferences sharedPreferences = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.procleus.brime", MODE_PRIVATE);
        setContentView(R.layout.activity_create_notes);
        getSupportActionBar().hide();
        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_create_notes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isLoggedIn = sharedPreferences.getBoolean("loggedin", false);
                create(v, isLoggedIn);
            }
        });

    }

    public void create(View v, Boolean isLoggedIn) {
        CustomDialogBox cdd =new CustomDialogBox(this, isLoggedIn);
        cdd.show();
    }

    public void check()
    {
        Notes tn = new Notes(this);
        EditText title = (EditText) findViewById(R.id.noteTitle);
        EditText note = (EditText) findViewById(R.id.note);

        String note_desc,note_title;
        note_desc = note.getText().toString();
       // note_desc = note_desc.replaceAll("'", "\'");
        note_title = title.getText().toString();
       // note_title = note_title.replaceAll("'", "\'");
        tn.insertTextNote(note_desc, note_title,"public", 1);
        Log.i("hello", note_title + "   " + note_desc);
        finish();

    }

    public void check2()
    {
        Notes tn = new Notes(this);
        EditText title = (EditText) findViewById(R.id.noteTitle);
        EditText note = (EditText) findViewById(R.id.note);

        String note_desc,note_title;
        note_desc = note.getText().toString();
        // note_desc = note_desc.replaceAll("'", "\'");
        note_title = title.getText().toString();
        // note_title = note_title.replaceAll("'", "\'");
        tn.insertTextNote(note_desc, note_title,"private", 1);
        Log.i("hello", note_title + "   " + note_desc);
        finish();



    }

}
