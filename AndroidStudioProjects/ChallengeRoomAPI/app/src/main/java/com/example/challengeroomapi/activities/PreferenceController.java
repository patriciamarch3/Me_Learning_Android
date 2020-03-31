package com.example.challengeroomapi.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import com.example.challengeroomapi.R;

import java.util.Locale;

class PreferenceController {

    static Context changeSettings(SharedPreferences preferences, Context context) {
        String themeColor = preferences.getString("themeColor", "green");
        changeColor(themeColor, context);

        String language = preferences.getString("language", "en");
        return changeLanguage(language, context);
    }

    private static void changeColor(String color, Context context) {
        switch (color) {
            case "red":
                context.setTheme(R.style.RedTheme);
                break;

            case "blue":
                context.setTheme(R.style.BlueTheme);
                break;

            default:
                context.setTheme(R.style.GreenTheme);
        }
    }

    private static Context changeLanguage(String language, Context context) {
        Resources res = context.getResources();
        Locale locale = new Locale(language);
        Configuration config = res.getConfiguration();

        // version < 17
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        } else { // version >= 17
            config.setLocale(locale);
            // version >= 24
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                LocaleList localeList = new LocaleList(locale);
                LocaleList.setDefault(localeList);
                config.setLocales(localeList);
            }
            context = context.createConfigurationContext(config);
        }
        return context;
    }
}
