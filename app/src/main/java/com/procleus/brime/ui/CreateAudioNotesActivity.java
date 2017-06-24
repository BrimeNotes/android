package com.procleus.brime.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.procleus.brime.R;
import com.procleus.brime.data.NotesDbHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateAudioNotesActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences = null;
    private static final int REQUEST_CODE = 1234;
    ArrayList<String>  labelsRetrieved;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.procleus.brime", MODE_PRIVATE);
        setContentView(R.layout.activity_create_audio_notes);
        getSupportActionBar().hide();

        FloatingActionButton fab_audio = (FloatingActionButton)findViewById(R.id.fab_create_audio_notes);
        FloatingActionButton fab_notes = (FloatingActionButton)findViewById(R.id.fab_create_notes);

        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            fab_audio.setEnabled(false);
            fab_audio.setVisibility(View.GONE);
        }

        fab_audio.animate().alpha(1f).setDuration(2000).start();
        fab_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voicerecog();
            }
        });

        fab_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isLoggedIn = sharedPreferences.getBoolean("loggedin", false);
                create(v, isLoggedIn);
            }
        });
    }

    private void voicerecog() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            EditText Etitle = (EditText)findViewById(R.id.noteTitle);
            EditText ENote = (EditText)findViewById(R.id.note);
            if(Etitle.hasFocus()){
                Etitle.setText(matches.get(0));
            }
            else{
                ENote.setText(matches.get(0));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /*Note s Creating Function Normal Like create NotesDbHelper*/

    public void create(View v, Boolean isLoggedIn) {
        CustomDialog(v, isLoggedIn);
    }

    private void CustomDialog(View v,final Boolean isLoggedIn) {
        final Dialog dialog = new Dialog(CreateAudioNotesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        dialog.show();

        final Button negative = (Button) dialog.findViewById(R.id.btn_no);
        final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner);
        final Button positive = (Button) dialog.findViewById(R.id.btn_yes);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lables_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        /*/This is Database Spinner Retreival*/
        /* Spinner getting Data from dataBase*/

        final NotesDbHelper tn =new NotesDbHelper(CreateAudioNotesActivity.this);



        labelsRetrieved = new ArrayList<String>();
        labelsRetrieved=tn.retrieveLabel();


        ArrayAdapter<String> my_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labelsRetrieved);

        my_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(my_adapter);


        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RadioGroup radioGroup = (RadioGroup)dialog.findViewById(R.id.radioGroup1);
                        int id = radioGroup.getCheckedRadioButtonId();
                        if (!isLoggedIn) {
                            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                                radioGroup.getChildAt(i).setEnabled(false);
                            }
                        } else {
                            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                                radioGroup.getChildAt(i).setEnabled(true);
                            }
                        }
                        if (id == R.id.radioPublic) {
                            NotesDbHelper tn = new NotesDbHelper(getApplicationContext());
                            EditText title = (EditText) findViewById(R.id.noteTitle);
                            EditText note = (EditText) findViewById(R.id.note);
                            String note_desc, note_title;
                            note_desc = note.getText().toString();
                            note_title = title.getText().toString();
                            if (note_title.replace(" ", "").length() == 0 ) {
                                note_title = (new Date()).toString();
                            }
                            tn.insertTextNote(note_desc, note_title, "public", 1,String.valueOf(parent.getItemAtPosition(position)));
                            Log.i("hello", note_title + "   " + note_desc);
                            finish();
                            Toast.makeText(CreateAudioNotesActivity.this, String.valueOf(parent.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (id == R.id.radioPrivate) {
                            NotesDbHelper tn = new NotesDbHelper(CreateAudioNotesActivity.this);
                            EditText title = (EditText) findViewById(R.id.noteTitle);
                            EditText note = (EditText) findViewById(R.id.note);
                            String note_desc, note_title;
                            note_desc = note.getText().toString();
                            note_title = title.getText().toString();
                            if (note_title.replace(" ", "").length() == 0 ) {
                                note_title = (new Date()).toString();
                            }
                            tn.insertTextNote(note_desc, note_title, "private", 1,String.valueOf(parent.getItemAtPosition(position)));
                            Log.i("hello", note_title + "   " + note_desc);
                            finish();
                            Toast.makeText(CreateAudioNotesActivity.this, "Private segment", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(CreateAudioNotesActivity.this, "Please choose Access Method", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CreateAudioNotesActivity.this, String.valueOf(parent.getItemIdAtPosition(0)), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
