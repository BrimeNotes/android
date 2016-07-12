package com.procleus.brime;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Swastik on 11-07-2016.
 */
public class CustomDialogBox extends Dialog implements View.OnClickListener{

    Spinner spinner;
    public Activity c;
    public Dialog d;
    public Button yes, no;

    public CustomDialogBox(Activity context) {
        super(context);
        c=context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.lables_array, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_yes:
                RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);
                int id = rg.getCheckedRadioButtonId();
                if(id==R.id.radioPublic)
                {
                    //************////****//***/**/Public segement


                    Toast.makeText(getContext(), "Public segment", Toast.LENGTH_SHORT).show();
                    ((CreateNotes)c).check();
                    c.finish();
                }
                else if(id==R.id.radioPrivate){

                    /**********///***/*/*/*/*/*/*// PRivate Segment

                    Toast.makeText(getContext(), "Private segment", Toast.LENGTH_SHORT).show();
                    ((CreateNotes)c).check2();
                    c.finish();

                }
                else
                {
                    ///******None selected param

                    Toast.makeText(getContext(), "Please choose Access Method", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();

    }
}
