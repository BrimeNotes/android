package com.procleus.brime;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class buttons extends Button {

    public buttons(Context context) {
        super(context);
        setFont();
    }

    public buttons(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public buttons(Context context, AttributeSet attrs, int defStyle) {
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
