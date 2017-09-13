package com.styleomega.styleomegafashion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class logout extends AppCompatActivity {

    SharedPreferences sharepref;
    public static final String preference="pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        sharepref=getSharedPreferences(preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharepref.edit();
        editor.clear();
        editor.commit();
        finish();
        Intent i=new Intent(logout.this,MainActivity.class);
        startActivity(i);
    }
}
