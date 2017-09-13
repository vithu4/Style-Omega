package com.styleomega.styleomegafashion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class frgotPassword extends AppCompatActivity {
    databaseConnection dbcon=new databaseConnection(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frgot_password);
    }

    public void GoToForgotPasswordUpdate(View view) {

        EditText user=(EditText)findViewById(R.id.ETFrgtPassUsername);
        String username=user.getText().toString();
        EditText pas=(EditText)findViewById(R.id.ETFrgtNewPassword);
        String password=pas.getText().toString();

        if(username.length()>0 && password.length()>0) {
            boolean res=dbcon.SearchUserInfor(username);
            if(res) {
                dbcon.updatePassword(username, password);
                Toast.makeText(frgotPassword.this, "Password has been modified", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(frgotPassword.this, Login.class);
                startActivity(i);
            }
            else{
                Toast.makeText(frgotPassword.this, "Username is incorrect", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(frgotPassword.this, "Username or Password is empty", Toast.LENGTH_SHORT).show();
        }

    }
}
