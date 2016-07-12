package com.procleus.brime;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Swastik on 11-07-2016.
 */
public class CustomDialogBox extends Dialog implements View.OnClickListener{

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
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_yes:
                RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
                int id = rg.getCheckedRadioButtonId();
                if(id==R.id.radioButtonpublic)
                {
                    //************////****//***/**/Public segement


                    Toast.makeText(getContext(), "Public segment", Toast.LENGTH_SHORT).show();
                    ((CreateNotes)c).check();
                    c.finish();
                }
                else if(id==R.id.radioButtonprivate){

                    /**********///***/*/*/*/*/*/*// PRivate Segment

                    Toast.makeText(getContext(), "Private segment", Toast.LENGTH_SHORT).show();
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
