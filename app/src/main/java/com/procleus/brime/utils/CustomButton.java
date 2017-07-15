/**
 * @author Saad Hassan <hassan.saad.mail@gmail.com>
 * @author Suraj Rawat <suraj.raw120@gmail.com>
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

package com.procleus.brime.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class
CustomButton extends android.support.v7.widget.AppCompatButton {

    public CustomButton(Context context) {
        super(context);
        setFont();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface normal = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-ExtraLight.ttf");
        setTypeface(normal, Typeface.NORMAL);
       /* Button bt = (Button)findViewById(R.id.login_button);
        bt.setTypeface(normal,Typeface.NORMAL);*/
    }
}
