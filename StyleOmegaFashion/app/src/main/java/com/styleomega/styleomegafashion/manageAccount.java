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

public class manageAccount extends AppCompatActivity {
    databaseConnection dbCon=new databaseConnection(this);
    SharedPreferences sharepref;
    public static final String preference="pref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        EditText ETusernam=(EditText)findViewById(R.id.ETMAUsername);
        EditText ETname=(EditText)findViewById(R.id.ETMANAME);
        EditText ETpassw=(EditText)findViewById(R.id.ETMAOldPass);
        EditText ETTelNo=(EditText)findViewById(R.id.ETMATelNo);
        EditText ETEmail=(EditText)findViewById(R.id.ETMAEmail);

        sharepref=getSharedPreferences(preference,Context.MODE_PRIVATE);

        String usrname=sharepref.getString("username", "");
        Cursor results=dbCon.SearchUserInformation(usrname);

        String nameofUser=results.getString(1);
        String pass=results.getString(2);
        int TelNumber=results.getInt(3);
        String emailMA=results.getString(4);

        ETusernam.setText(usrname);
        ETname.setText(nameofUser);
        ETpassw.setText(pass);
        ETTelNo.setText(""+TelNumber);
        ETEmail.setText(emailMA);

    }

    public void updateUserInformation(View view) {
        EditText usernam = (EditText) findViewById(R.id.ETMAUsername);
        String username = usernam.getText().toString();
        EditText Name = (EditText) findViewById(R.id.ETMANAME);
        String name = Name.getText().toString();
        EditText pwd = (EditText) findViewById(R.id.ETMAOldPass);
        String password = pwd.getText().toString();
        EditText NewPassword = (EditText) findViewById(R.id.ETMANewPass);
        String newPass = NewPassword.getText().toString();
        EditText ETtelenumber = (EditText) findViewById(R.id.ETMATelNo);
        String telenumber = ETtelenumber.getText().toString();
        EditText ETemail = (EditText) findViewById(R.id.ETMAEmail);
        String emailMAchange = ETemail.getText().toString();
        int num=password.trim().length();
        int num2=name.trim().length();
        if (password.trim().length()>0 && name.trim().length()>0 && telenumber.length()>0 && emailMAchange.length()>0) {
            if (newPass.trim().length()<1) {
                Toast.makeText(manageAccount.this, "Account Details Updated", Toast.LENGTH_SHORT).show();
                dbCon.updateCustomer(username, name, password,telenumber,emailMAchange);
                Intent i = new Intent(manageAccount.this, manageAccount.class);
                startActivity(i);
            }
            else
            {

                dbCon.updateCustomer(username, name, newPass,telenumber,emailMAchange);
                Toast.makeText(manageAccount.this, "New Password has been set to " + newPass, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(manageAccount.this, manageAccount.class);
                startActivity(i);
            }

        }
        else
        {
            Toast.makeText(manageAccount.this, "Fields cannot be empty ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(manageAccount.this, Navigation.class));
        finish();

    }
    }

