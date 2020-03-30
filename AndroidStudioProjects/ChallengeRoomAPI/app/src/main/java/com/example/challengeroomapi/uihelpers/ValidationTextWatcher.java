package com.example.challengeroomapi.uihelpers;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.challengeroomapi.R;
import com.google.android.material.textfield.TextInputLayout;

public class ValidationTextWatcher implements TextWatcher {
    private Context context;
    private View view;
    private TextInputLayout til;

    public ValidationTextWatcher(Context context, View view, TextInputLayout til) {
        this.context = context;
        this.view = view;
        this.til = til;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        validateEditText(context, view, til);
    }

    public static void validateEditText(Context context, View view, TextInputLayout til) {
        EditText et = (EditText) view;
        if (et.getText().toString().trim().isEmpty()) {
            switch (view.getId()) {
                case R.id.etISBN:
                    til.setError(context.getString(R.string.warning_no_isbn));
                    break;

                case R.id.etTitle:
                    til.setError(context.getString(R.string.warning_no_title));
                    break;

                case R.id.etAuthor:
                    til.setError(context.getString(R.string.warning_no_author));
                    break;
            }
        } else {
            til.setErrorEnabled(false);
        }
    }
}
