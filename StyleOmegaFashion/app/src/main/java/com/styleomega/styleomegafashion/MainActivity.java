package com.styleomega.styleomegafashion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharepref;
    public static final String preference="pref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharepref=getSharedPreferences(preference, Context.MODE_PRIVATE);

    }

    public void GoToLogOrRegisterLayout(View view){
        if(view.getId()==R.id.BLogin){


            Intent i=new Intent(MainActivity.this,Login.class);
            startActivity(i);
        }
        else if(view.getId()==R.id.BRegister){
            Intent i=new Intent(MainActivity.this,Register.class);
            startActivity(i);
        }

        else if(view.getId()==R.id.GuestBtn){
            SharedPreferences.Editor editor=sharepref.edit();
            editor.putString("username", "Guest");
            editor.commit();
            Intent i=new Intent(MainActivity.this,Navigation.class);
            startActivity(i);
        }
    }
}
