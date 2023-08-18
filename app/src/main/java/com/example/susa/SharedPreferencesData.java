package com.example.susa;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mohamed daahir faarah->615774091 on 04/07/2020.
 */

public class SharedPreferencesData {

    private static SharedPreferences savelifePreference;
    private static SharedPreferencesData savelifeInstanse;
    public static String TOKEN = "token";
    public static String LANGUAGE = "language";
    public static String BYTEIMAGE = "byteImage";
    public static String USERNAME = "username";
    public static String USER_id = "user_id";
    public static String EMAIL = "email";
    public static String PHONE_NUMBER = "phone";

    public static String from ="from";

    public SharedPreferencesData() {
    }

    public static SharedPreferencesData getInstance(Context context) {
        savelifePreference = context.getSharedPreferences("dhulkoob", context.MODE_PRIVATE);
        return new SharedPreferencesData();
    }

    public void putToken(String token) {
        SharedPreferences.Editor editor = savelifePreference.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }
    public String getTOKEN() {
        return savelifePreference.getString(TOKEN, "");
    }

    public void putLanguage(String lang) {
        SharedPreferences.Editor editor = savelifePreference.edit();
        editor.putString(LANGUAGE, lang);
        editor.commit();
    }
    public String getLanguage() {
        return savelifePreference.getString(LANGUAGE, "");
    }

    public void putimageByte(Uri arr) {
        SharedPreferences.Editor editor = savelifePreference.edit();
        editor.putString(BYTEIMAGE, arr.toString());
        editor.commit();
    }
    public String getimageByte() {
        return savelifePreference.getString(BYTEIMAGE, "");
    }


    public void putusername(String name) {
        SharedPreferences.Editor editor = savelifePreference.edit();
        editor.putString(USERNAME, name);
        editor.commit();
    }
    public String getUsername() {
        return savelifePreference.getString(USERNAME, "");
    }

    public void putuser_id(String username) {
        SharedPreferences.Editor editor = savelifePreference.edit();
        editor.putString(USER_id, username);
        editor.commit();
    }

    public String getUSER_id() {
        return savelifePreference.getString(USER_id, "");
    }

    public void putUserEmail(String name) {
        SharedPreferences.Editor editor = savelifePreference.edit();
        editor.putString(EMAIL, name);
        editor.commit();
    }
    public String getUserEmail() {
        return savelifePreference.getString(EMAIL, "");
    }

    public void putUserPhone(String name) {
        SharedPreferences.Editor editor = savelifePreference.edit();
        editor.putString(PHONE_NUMBER, name);
        editor.commit();
    }
    public String getUserPhone() {
        return savelifePreference.getString(PHONE_NUMBER, "");
    }




    public void putfrom(String frm) {
        SharedPreferences.Editor editor = savelifePreference.edit();
        editor.putString(from, frm);
        editor.commit();
    }

    public String getfrom() {
        return savelifePreference.getString(from, "");
    }
    public void putBookmarkedIds(Set<String> bookmarkedIds) {
        SharedPreferences.Editor editor = savelifePreference.edit();
        editor.putStringSet("bookmarked_ids", bookmarkedIds);
        editor.apply();
    }

    public Set<String> getBookmarkedIds() {
        return savelifePreference.getStringSet("bookmarked_ids", new HashSet<>());
    }



}
