package com.example.challengeroomapi.activities;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.challengeroomapi.R;
import com.example.challengeroomapi.uihelpers.TopToast;

class PreferenceController {

    static void changeSettings(SharedPreferences preferences, Activity activity) {
        String themeColor = preferences.getString("themeColor", "green");
        String language = preferences.getString("language", "english");
        switch (themeColor) {
            case "red":
                activity.setTheme(R.style.RedTheme);
                TopToast.create(activity, "change to red");
                break;

            case "blue":
                activity.setTheme(R.style.BlueTheme);
                TopToast.create(activity, "change to blue");
                break;

            default:
                activity.setTheme(R.style.GreenTheme);
                TopToast.create(activity, "change to green");
        }
    }
}