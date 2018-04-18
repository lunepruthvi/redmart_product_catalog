package com.redmart.catalog.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.redmart.catalog.R;
import com.redmart.catalog.utils.Utils;


public class CustomFontTextView extends AppCompatTextView {

    public CustomFontTextView(Context context) {
        super(context);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.customTextView, 0, 0);
            try {
                int textStyleIndex = typedArray.getInt(R.styleable.customTextView_textStyle, 0);
                setTextFontStyle(context, textStyleIndex);
            } finally {
                typedArray.recycle();
            }
        }
    }

    public void setTextFontStyle(Context context, int textStyleIndex) {
        setTypeface(Utils.getFont(context, textStyleIndex));
    }

}



