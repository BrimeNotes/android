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
