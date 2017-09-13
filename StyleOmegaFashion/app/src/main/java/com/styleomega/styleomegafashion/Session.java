package com.styleomega.styleomegafashion;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by HP on 8/26/2017.
 */

public class Session {
    String cusid,Name,pwd;

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusername(String usename) {
        prefs.edit().putString("username", usename).commit();
    }

    public String getusername() {
        String usename = prefs.getString("username","");
        return usename;
    }

    public void setName(String name) {
        prefs.edit().putString("name", name).commit();
    }

    public String getName() {
        String name = prefs.getString("name","");
        return name;
    }

    public void setPwd(String pwd) {
        prefs.edit().putString("pwd", pwd).commit();
    }

    public String getPwd() {
        String pwd = prefs.getString("pwd","");
        return pwd;
    }
}
