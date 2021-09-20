package com.namita.Common;

//Created By Namita

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceHelper {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PreferenceHelper(Context context) {
        pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
    }

    public void setBoolean(String key, boolean flag){
        editor = pref.edit();
        editor.putBoolean(key, flag);
        editor.commit();
    }

    public boolean getBoolean(String key){
        return pref.getBoolean(key,false);
    }

    public void setString(String key, String tag){
        editor = pref.edit();
        editor.putString(key, tag);
        editor.commit();
    }

    public String getString(String key){
        return pref.getString(key,null);
    }

    public String getString(String key, String defaultValue){
        return pref.getString(key,defaultValue);
    }

    public void setInt(String key, int fragmentCount){
        editor = pref.edit();
        editor.putInt(key,fragmentCount);
        editor.commit();
    }

    public int getInt(String key){
        return pref.getInt(key,0);
    }

    public void clearData(){
        editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
