package com.procleus.brime;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;

public class CreateNotes extends AppCompatActivity {

    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.procleus.brime", MODE_PRIVATE);
        setContentView(R.layout.activity_create_notes);
        getSupportActionBar().hide();
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_create_notes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isLoggedIn = sharedPreferences.getBoolean("loggedin", false);
                create(v, isLoggedIn);
            }
        });

    }

    public void create(View v, Boolean isLoggedIn) {

        CustomDialog(v, isLoggedIn);
    }

    private void CustomDialog(View v,final Boolean isLoggedIn) {



        final Dialog dialog = new Dialog(CreateNotes.this);

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



        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///*******No Button
                dialog.dismiss();


            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {

                positive.setOnClickListener(new View.OnClickListener() {

                    //Yes Button**
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
                            //************////****//***/**/Public segement

                            Notes tn = new Notes(getApplicationContext());
                            EditText title = (EditText) findViewById(R.id.noteTitle);
                            EditText note = (EditText) findViewById(R.id.note);


                            String note_desc, note_title;
                            note_desc = note.getText().toString();
                            // note_desc = note_desc.replaceAll("'", "\'");
                            note_title = title.getText().toString();
                            if (note_title.replace(" ", "").length() == 0 ) {
                                note_title = (new Date()).toString();
                            }
                            // note_title = note_title.replaceAll("'", "\'");
                            tn.insertTextNote(note_desc, note_title, "public", 1,String.valueOf(parent.getItemAtPosition(position)));
                            Log.i("hello", note_title + "   " + note_desc);
                            finish();
                            Toast.makeText(CreateNotes.this, String.valueOf(parent.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();

                            finish();
                        } else if (id == R.id.radioPrivate) {



                            /**********///***/*/*/*/*/*/*// PRivate Segment


                            Notes tn = new Notes(CreateNotes.this);
                            EditText title = (EditText) findViewById(R.id.noteTitle);
                            EditText note = (EditText) findViewById(R.id.note);

                            String note_desc, note_title;
                            note_desc = note.getText().toString();
                            // note_desc = note_desc.replaceAll("'", "\'");
                            note_title = title.getText().toString();
                            if (note_title.replace(" ", "").length() == 0 ) {
                                note_title = (new Date()).toString();
                            }
                            // note_title = note_title.replaceAll("'", "\'");
                            tn.insertTextNote(note_desc, note_title, "private", 1,String.valueOf(parent.getItemAtPosition(position)));
                            Log.i("hello", note_title + "   " + note_desc);
                            finish();

                            Toast.makeText(CreateNotes.this, "Private segment", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            ///******None selected param

                            Toast.makeText(CreateNotes.this, "Please choose Access Method", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }




                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


                Toast.makeText(CreateNotes.this, String.valueOf(parent.getItemIdAtPosition(0)), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
