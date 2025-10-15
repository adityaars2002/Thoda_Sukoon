package com.example.thodasukoon;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private SharedPreferences prefs;

    public PrefManager(Context context) {
        prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        prefs.edit().putString("token", token).apply();
    }

    public String getToken() {
        return prefs.getString("token", null);
    }

    public void clearToken() {
        prefs.edit().remove("token").apply();
    }

}
