package com.procleus.brime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CreateNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);
        EditText title;
        getSupportActionBar().hide();
    }

    public void create(View v) {
        Notes tn = new Notes(this);
        EditText title = (EditText) findViewById(R.id.noteTitle);
        EditText note = (EditText) findViewById(R.id.note);
        tn.insertTextNote(note.getText().toString(), title.getText().toString(), 1);
        finish();
        Log.i(title.getText().toString(), note.getText().toString());
    }
}
