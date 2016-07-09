package com.procleus.brime;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsClickedAcitvity extends AppCompatActivity {

    SharedPreferences userDetails;
    SharedPreferences.Editor edit;
    EditText fname;
    EditText lname;
    EditText email;
    TextView dob;
    static int day=12;
    static int month=6;
    static int year=2006;
    DatePicker datePicker;
    Button btnsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_acitvity);


        fname = (EditText)findViewById(R.id.first_name);
        lname = (EditText)findViewById(R.id.last_name);
        email = (EditText)findViewById(R.id.Email);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        dob = (TextView)findViewById(R.id.dob);
         /*day = datePicker.getDayOfMonth();
         month = datePicker.getMonth() + 1;
         year = datePicker.getYear();
*/
        userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
        btnsave = (Button)findViewById(R.id.button_save);


        edit=userDetails.edit();
        if(userDetails.getString("first_name","NULL")=="NULL") {

            edit.putString("first_name","Enter");
            edit.putString("last_name", "Enter");
            edit.putString("Email", "Enter");
            edit.putInt("fday", day);
            edit.putInt("fmonth", month);
            edit.putInt("fyear", year);
            edit.commit();

            fname.setText(userDetails.getString("first_name","No_fisrtname"));
            lname.setText(userDetails.getString("last_name","No_lastname"));
            email.setText(userDetails.getString("Email","No_email"));
            email.setText(userDetails.getString("Email","No_email"));
            dob.setText(String.valueOf(userDetails.getInt("fday",day))+ "/"+String.valueOf(userDetails.getInt("fmonth",month))+ "/"+String.valueOf(userDetails.getInt("fyear",year)));
        }
        else
        {
            fname.setText(userDetails.getString("first_name","No_fisrtname"));
            lname.setText(userDetails.getString("last_name","No_lastname"));
            email.setText(userDetails.getString("Email","No_email"));
            dob.setText(String.valueOf(userDetails.getInt("fday",day))+ "/"+String.valueOf(userDetails.getInt("fmonth",month))+ "/"+String.valueOf(userDetails.getInt("fyear",year)));
        }


    }

    public void button_save(View v)
    {

        edit.clear();
            edit.putString("first_name",fname.getText().toString().trim());
            edit.putString("last_name", lname.getText().toString().trim());
            edit.putString("Email", email.getText().toString().trim());
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth() + 1;
        year = datePicker.getYear();
        edit.putInt("fday", day);
        edit.putInt("fmonth", month);
        edit.putInt("fyear", year);
        Toast.makeText(SettingsClickedAcitvity.this, String.valueOf(day)+String.valueOf(month)+String.valueOf(year), Toast.LENGTH_SHORT).show();
        edit.commit();
        Toast.makeText(SettingsClickedAcitvity.this, "SavedSuccesfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userDetails = getSharedPreferences("userdetails", MODE_PRIVATE);
        edit.putString("first_name","Enter");
        edit.putString("last_name", "Enter");
        edit.putString("Email", "Enter");
        edit.putInt("fday", day);
        edit.putInt("fmonth", month);
        edit.putInt("fyear", year);
        Toast.makeText(SettingsClickedAcitvity.this, String.valueOf(day)+String.valueOf(month)+String.valueOf(year), Toast.LENGTH_SHORT).show();
        edit.commit();

    }
}
