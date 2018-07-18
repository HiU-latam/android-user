package com.hiulatam.hiu.hiu.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.util.Log;

/**
 * Created by:  Shiny Solutions
 * Created on:  10/4/17.
 */

public class Config {

    public static final String TAG = "com.hiulatam.hiu.hiu";

    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final String EXTRA_CELEBRITY_ITEM = "EXTRA_CELEBRITY_ITEM";
    public static final String EXTRA_SEARCH_QUERY = "EXTRA_SEARCH_QUERY";

    public static final String ACTION_SEARCH_QUERY = "ACTION_SEARCH_QUERY";

    public static final String kAll = "All";

    public static final String MY_PREFS = "MyPrefs";
    public static final String PREFS_NOTIFICATION = "NOTIFICATION PREFERENCES";

    public static final void logInfo(String message){
        Log.i(TAG, message);
    }

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context){
        return context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE).edit();
    }

}
