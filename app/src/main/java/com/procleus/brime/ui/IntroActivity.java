/**
 * @author Saad Hassan <hassan.saad.mail@gmail.com>
 * @author Suraj Rawat <suraj.raw120@gmail.com>
 * @author Swastik Binjola <swastik.binjola2561@gmail.com>
 * @author Ujjwal Bhardwaj <ujjwalb1996@gmail.com>
 *
 * @license AGPL-3.0
 *
 * This code is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License, version 3,
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package com.procleus.brime.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.procleus.brime.R;
import com.procleus.brime.utils.CustomButton;
import com.procleus.brime.login.SigninActivity;

public class IntroActivity extends AppCompatActivity {
    CustomButton signinBtn;
    CustomButton getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        getStarted = (CustomButton) findViewById(R.id.getStarted);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, GetStartedActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signinBtn =(CustomButton)findViewById(R.id.signinbtn) ;
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroActivity.this,SigninActivity.class);
                startActivity(i);
            }
        });
    }
}
