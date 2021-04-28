package com.nodexsolutions.bankingapplication.Databases;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "moneymanagment";
    private static final String THEME = "theme";
    private static final String CURRENCY = "currency";
    private static final String APP_BACKGROUND = "background";
    private static final String APP_WALLPAPER = "wallpaper";
    private static final String SELECTED_AMOUNT = "amount";
    private static final String SELECTED_AMOUNT_NAME = "name";
    private static final String TIME_RANGE = "time_range";
    private static final String DATE_FORMATE = "format";
    private static final String IS_PREMIUM = "premium";
    private static final String DAY_START = "day_start";
    private static final String SELECT_LANGUAGE = "language";
    private static final String ALREADY_SIGNIN = "already";
    private static final String UNIQUE_KEY = "key";

    private Context context;

    public PrefManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setCurrency(String phoneNumber){
        editor.putString(CURRENCY,phoneNumber);
        editor.commit();
    }

    public String getCurrency(){
        return sharedPreferences.getString(CURRENCY,null);
    }

    public void setTheme(String key){
        editor.putString(THEME,key);
        editor.commit();
    }

    public String getTheme(){
        return sharedPreferences.getString(THEME,null);
    }

    public void setAppBackground(String key){
        editor.putString(APP_BACKGROUND,key);
        editor.commit();
    }

    public String getAppBackground(){
        return sharedPreferences.getString(APP_BACKGROUND,null);
    }

    public void setAppWallpaper(String key){
        editor.putString(APP_WALLPAPER,key);
        editor.commit();
    }

    public String getAppWallpaper(){
        return sharedPreferences.getString(APP_WALLPAPER,null);
    }

    public void setSelectedAmount(String key){
        editor.putString(SELECTED_AMOUNT,key);
        editor.commit();
    }

    public String getSelectedAmount(){
        return sharedPreferences.getString(SELECTED_AMOUNT,null);
    }

    public void setSelectedAmountName(String key){
        editor.putString(SELECTED_AMOUNT_NAME,key);
        editor.commit();
    }

    public String getSelectedAmountName(){
        return sharedPreferences.getString(SELECTED_AMOUNT_NAME,null);
    }

    public void setTimeRange(String key){
        editor.putString(TIME_RANGE,key);
        editor.commit();
    }

    public String getTimeRange(){
        return sharedPreferences.getString(TIME_RANGE,"weekly");
    }

    public void setDateFormate(String key){
        editor.putString(DATE_FORMATE,key);
        editor.commit();
    }

    public String getDateFormate(){
        return sharedPreferences.getString(DATE_FORMATE,"MM/dd/yyyy");
    }

    public void setIsPremium(Boolean key){
        editor.putBoolean(IS_PREMIUM,key);
        editor.commit();
    }

    public Boolean getIsPremium(){
        return sharedPreferences.getBoolean(IS_PREMIUM,false);
    }

    public void setDayStart(String key){
        editor.putString(DAY_START,key);
        editor.commit();
    }

    public String getDayStart(){
        return sharedPreferences.getString(DAY_START,"monday");
    }

    public void setSelectLanguage(String key){
        editor.putString(SELECT_LANGUAGE,key);
        editor.commit();
    }

    public String getSelectLanguage(){
        return sharedPreferences.getString(SELECT_LANGUAGE,"en");
    }

    public void setAlreadySignin(Boolean key){
        editor.putBoolean(ALREADY_SIGNIN,key);
        editor.commit();
    }

    public Boolean getAlreadySignin(){
        return sharedPreferences.getBoolean(ALREADY_SIGNIN,false);
    }

    public void setUniqueKey(String key){
        editor.putString(UNIQUE_KEY,key);
        editor.commit();
    }

    public String getUniqueKey(){
        return sharedPreferences.getString(UNIQUE_KEY,"");
    }
}
