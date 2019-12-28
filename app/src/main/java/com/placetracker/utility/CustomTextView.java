package com.placetracker.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


@SuppressLint("AppCompatCustomView")
public class CustomTextView extends TextView {

    public CustomTextView (Context context) {
        super(context);
        init(context);
    }

    public CustomTextView (Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "malithi.TTF");
        setTypeface(font);
    }
}
