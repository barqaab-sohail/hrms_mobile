package com.example.hrms_android_3.classes;
import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceHelper {
    private final String INTRO = "intro";
    private final String TOKEN = "token";
    private final String TOKEN_TYPE = "type";
    private final String NAME = "name";
    private final String EMAIL = "email";
    private final String PICTURE_URL = "picture_url";
    private SharedPreferences app_prefs;
    private Context context;
    private static PreferenceHelper preferenceHelper;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }


    public String getName() {
        return app_prefs.getString(NAME, "");
    }

    public void putName(String name) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NAME, name);
        edit.commit();
    }

    public String getEmail() {
        return app_prefs.getString(EMAIL, "");
    }

    public void putEmail(String email) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(EMAIL, email);
        edit.commit();
    }

    public String getPictureUrl() {
        return app_prefs.getString(PICTURE_URL, "");
    }

    public void putPictureUrl(String pictureUrl) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(PICTURE_URL, pictureUrl);
        edit.commit();
    }

    public String getToken() {
        return app_prefs.getString(TOKEN, "");
    }

    public void putToken(String token) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(TOKEN, token);
        edit.commit();
    }

    public String getTokenType() {
        return app_prefs.getString(TOKEN_TYPE, "");
    }

    public void putTokenType(String type) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(TOKEN_TYPE, type);
        edit.commit();
    }

    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }
    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }


}
