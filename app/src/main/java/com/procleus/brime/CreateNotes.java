package com.procleus.brime;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CreateNotes extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);
        getSupportActionBar().hide();
        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_create_notes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create(v);
            }
        });

    }

    public void create(View v) {
        CustomDialogBox cdd =new CustomDialogBox(this);
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
        tn.insertTextNote(note_desc, note_title, 1);
        Log.i("hello", note_title + "   " + note_desc);
        finish();

    }
}
