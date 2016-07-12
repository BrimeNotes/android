package com.procleus.brime;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditNotes extends AppCompatActivity {
    Notes helper;
    List<NotesModel> dbList;
    EditText note,title;
    Boolean SAVE_NOTE_ACTIVE = false;
    static int _id = 0 ;
    static String _access_type ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        note = (EditText) findViewById(R.id.note);
        note.setFocusable(false);
        title = (EditText) findViewById(R.id.noteTitle);
        title.setFocusable(false);

        getSupportActionBar().setTitle("Edit Notes");
        getSupportActionBar().setElevation((float) 0.1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

         Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        _id = bundle.getInt("id");
        _access_type = bundle.getString("access_type");
        initialiseNotes(_id,_access_type);

        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_edit_notes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SAVE_NOTE_ACTIVE == false) {
                    fab.setImageResource(R.drawable.ic_done_24dp);
                    editNotes(v);

                }
                else{
                    saveNotes(v,_id);
                }
            }
        });

    }


    public void editNotes(View v) {
      //  Notes tn = new Notes(this);
       // TextView title = (TextView) findViewById(R.id.noteTitle);
        SAVE_NOTE_ACTIVE = true;
        title.setFocusableInTouchMode(true);
        title.setFocusable(true);
        note.setFocusableInTouchMode(true);
        note.setFocusable(true);
    }
    public void initialiseNotes(int id,String accessVal){
        helper = new Notes(this);
        dbList= new ArrayList<NotesModel>();
        dbList = helper.getDataFromDB(accessVal,0);

        if(dbList.size()>0){
            String title_str= dbList.get(id).getTitle();
            String desc_str=dbList.get(id).getDesc();
            title.setText(title_str);
            note.setText(desc_str);
        }
    }
    public void saveNotes(View v,int id){
        Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
    }
}
