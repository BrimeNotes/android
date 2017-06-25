package com.procleus.brime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.procleus.brime.R;
import com.procleus.brime.data.NotesDbHelperOld;
import com.procleus.brime.models.NotesModel;

import java.util.ArrayList;
import java.util.List;

public class EditNotesActivity extends AppCompatActivity {
    static Integer _id = 0, _pos = 0;
    static String _access_type = "";
    NotesDbHelperOld helper;
    List<NotesModel> dbList;
    EditText note,title;
    Boolean SAVE_NOTE_ACTIVE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        note = (EditText) findViewById(R.id.note);
        note.setFocusable(false);
        title = (EditText) findViewById(R.id.noteTitle);
        title.setFocusable(false);

        getSupportActionBar().setTitle("Edit NotesDbHelperOld");
        getSupportActionBar().setElevation((float) 0.1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

         Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        _id = bundle.getInt("id");
        _access_type = bundle.getString("access_type");
        _pos = bundle.getInt("pos");
        initialiseNotes(_id, _access_type, _pos);

        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_edit_notes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SAVE_NOTE_ACTIVE == false) {
                    fab.setImageResource(R.drawable.ic_done_24dp);
                    editNotes(v);

                }
                else{
                    saveNotes(v, _id, _pos);
                }
            }
        });

    }


    public void editNotes(View v) {
      //  NotesDbHelperOld tn = new NotesDbHelperOld(this);
       // TextView title = (TextView) findViewById(R.id.noteTitle);
        SAVE_NOTE_ACTIVE = true;
        title.setFocusableInTouchMode(true);
        title.setFocusable(true);
        note.setFocusableInTouchMode(true);
        note.setFocusable(true);
    }

    public void initialiseNotes(Integer id, String accessVal, Integer pos) {
        helper = new NotesDbHelperOld(this);
        dbList= new ArrayList<NotesModel>();
        dbList = helper.getDataFromDB(accessVal,0);

        if(dbList.size()>0){
            String title_str = dbList.get(pos).getTitle();
            String desc_str = dbList.get(pos).getDesc();
            title.setText(title_str);
            note.setText(desc_str);
        }
    }

    public void saveNotes(View v, Integer id, Integer pos) {

        /** Here Id Auto Increments starting from 1 while Adapter initialises from 0
         * Therefore Previous NotesDbHelperOld get Edit
         * TO Solve it id=id+1
         */

       // Log.d("ID VAL gjhkjhjkhkj :",id.toString());
        helper.updateTextNote(id,note.getText().toString(),title.getText().toString());
        Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();

        finish();
    }
}
