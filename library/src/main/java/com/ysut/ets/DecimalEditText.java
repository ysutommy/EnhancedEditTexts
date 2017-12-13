package com.ysut.ets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 自定义保持小数位数的编辑框
 *
 * Created by ysutommy on 2016/10/19.
 */
public class DecimalEditText extends EditText {

    private int digits = 2; //小数位数，默认2位
    private float maxValue = Float.MAX_VALUE;

    public DecimalEditText(Context context) {
        this(context, null);
    }

    public DecimalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DecimalEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public DecimalEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DecimalEditText);
            try {
                digits = ta.getInt(R.styleable.DecimalEditText_decimal_digits, digits);
                maxValue = ta.getFloat(R.styleable.DecimalEditText_decimal_max_value, maxValue);
            } finally {
                ta.recycle();
            }
        }
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        super.addTextChangedListener(new InnerTextWatcher());
    }

    class InnerTextWatcher implements TextWatcher {
        String temp;
        int dot;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) return;
            temp = s.toString();
            dot = s.charAt(0);
            if (dot == '-') {
                s.delete(0, 1);
            } else if (dot == '.') {
                s.insert(0, "0");
            } else if (dot == '0' && s.length() == 2 && s.charAt(1) != '.') {
                s.insert(1, ".");
            } else if (Double.parseDouble(temp) > maxValue) {
                s.delete(s.length() - 1, s.length());
            } else if (!temp.endsWith(".")) {
                int dot = temp.indexOf('.');
                if (dot > 0 && temp.substring(dot + 1, s.length()).length() > digits) {
                    s.delete(s.length() - 1, s.length());
                }
            }
        }
    }
}
