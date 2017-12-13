package com.ysut.ets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 *
 * Created by ysutommy on 2016/10/18.
 * */
public class SpilttedEditText extends EditText {

    private TextWatcher textWatcher;
    private String separator = " ";
    private int sepLen = 4;
    private String defAllowedChars = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ -,";

    public SpilttedEditText(Context context) {
        this(context, null);
    }

    public SpilttedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SpilttedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public SpilttedEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SpilttedEditText);
            try {
                String temp = ta.getString(R.styleable.SpilttedEditText_separator);
                if (!TextUtils.isEmpty(temp)) separator = temp;
                temp = ta.getString(R.styleable.SpilttedEditText_allowed_input);
                if (!TextUtils.isEmpty(temp)) defAllowedChars = temp;
                sepLen = ta.getInt(R.styleable.SpilttedEditText_sep_len, sepLen);
            } finally {
                ta.recycle();
            }
        }
        if (sepLen > 0) {
            if (!TextUtils.isEmpty(defAllowedChars))setKeyListener(DigitsKeyListener.getInstance(defAllowedChars));
            textWatcher = new InnerTextWatcher();
            super.addTextChangedListener(textWatcher);
        }
    }


    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        if (textWatcher != null) removeTextChangedListener(textWatcher);
        super.addTextChangedListener(watcher);
    }

    class InnerTextWatcher implements TextWatcher {
        final StringBuffer buffer = new StringBuffer();
        int len, start, selection;

        void reInitBuffer() {
            len = buffer.length();
            start = sepLen;
            while (start < len++) {
                buffer.insert(start, separator);
                start += sepLen + 1;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int len = buffer.length();
            selection = getSelectionEnd();
            buffer.delete(0, len);
            buffer.append(s.toString().replace(separator, ""));
            reInitBuffer();
            removeTextChangedListener(textWatcher);

            setText(buffer.toString());

            if (buffer.length() > len) {
                setSelection(buffer.length());
            } else {
                setSelection(selection > buffer.length() ? buffer.length() : selection);
            }

            SpilttedEditText.super.addTextChangedListener(textWatcher);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

}
