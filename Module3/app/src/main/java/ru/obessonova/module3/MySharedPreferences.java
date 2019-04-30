package ru.obessonova.module3;

import android.content.SharedPreferences;

import java.io.Serializable;

public class MySharedPreferences implements Storage, Serializable {
    
    private static final String APP_PREFERENCES_TITLE = "Title";
    private static final String APP_PREFERENCES_DESCRIPTIONS = "Descriptions";
    
    @Override
    public void setStorage(String title, String descript) {
        SharedPreferences.Editor editor = Tasks.sSettings.edit();
        editor.putString(APP_PREFERENCES_TITLE, title);
        editor.putString(APP_PREFERENCES_DESCRIPTIONS, descript);
        editor.apply();
    }
}
