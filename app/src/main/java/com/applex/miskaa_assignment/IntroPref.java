package com.applex.miskaa_assignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class IntroPref {

    private SharedPreferences preferences;
    private final SharedPreferences.Editor editor;
    private static final String PREF_NAME = "com.applex.miskaa";
    private static final String IS_FIRST_LAUNCH = "first_time";

    @SuppressLint("CommitPrefEdits")
    public IntroPref(Context context) {
        if(context != null) {
            preferences = context.getSharedPreferences(PREF_NAME, 0);
        }
        editor = preferences.edit();
    }

    public void setIsFirstTimeLaunch(boolean firstTimeLaunch) {
        editor.putBoolean(IS_FIRST_LAUNCH, firstTimeLaunch);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return preferences.getBoolean(IS_FIRST_LAUNCH, true);
    }
}