package com.procleus.brime;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by syedaamir on 03-07-2016.
 */
public class textview extends TextView{
    public textview(Context context) {
        super(context);
        setFont();

    }

    public textview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public textview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface normal = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-ExtraLight.ttf");
        setTypeface(normal, Typeface.NORMAL);

    }
}



