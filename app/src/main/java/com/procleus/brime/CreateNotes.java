package com.procleus.brime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class CreateNotes extends AppCompatActivity {
    EditText title;
    EditText note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);
        EditText title;
        getSupportActionBar().hide();
    }

    public void create() {
        Notes tn = new Notes(this);
        setContentView(R.layout.activity_create_notes);
        title = (EditText) findViewById(R.id.noteTitle);
        note = (EditText) findViewById(R.id.note);
        tn.insertTextNote(note.getText().toString(), title.getText().toString(), 1);
    }
}
