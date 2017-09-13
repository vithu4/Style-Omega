package com.styleomega.styleomegafashion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    SharedPreferences sharepref;
    public static final String preference="pref";
    databaseConnection dbcon=new databaseConnection(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharepref=getSharedPreferences(preference,Context.MODE_PRIVATE);
    }

    public void GoToLogin(View view){
        EditText user=(EditText)findViewById(R.id.ETUsername);
        String username=user.getText().toString();
        EditText pas=(EditText)findViewById(R.id.ETPassword);
        String password=pas.getText().toString();
        SharedPreferences.Editor editor=sharepref.edit();

        Cursor results=dbcon.SearchUserInformation(username);

        int count=results.getCount();
        if(count>0) {
            String Name=results.getString(1);
            String pass=results.getString(2);
            if (pass.equals(password)) {
                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                editor.putString("username", username);
                editor.putString("name", Name);
                editor.putString("password", password);
                editor.commit();
                Intent i = new Intent(Login.this, Navigation.class);
                startActivity(i);
            } else {
                Toast.makeText(Login.this, "Login not Successful", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(Login.this, "Username is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public void GoToForgotPassword(View view) {
        Intent i = new Intent(Login.this, frgotPassword.class);
        startActivity(i);

    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();

    }
}
