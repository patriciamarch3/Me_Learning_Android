package com.example.challengeroomapi.uihelpers;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class TopToast {

    public static void create(Context context, String message) {
        Toast topToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        topToast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        topToast.show();
    }
}
