package com.styleomega.styleomegafashion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    databaseConnection dbCon=new databaseConnection(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onRegister(View view){
        EditText usrnm=(EditText)findViewById(R.id.ETUsrNm);
        EditText nm=(EditText)findViewById(R.id.ETNm);
        EditText pass=(EditText)findViewById(R.id.ETPasword);
        EditText conPass=(EditText)findViewById(R.id.EtConfrmPassword);
        EditText TelNo=(EditText)findViewById(R.id.ETTelNo);
        EditText Email=(EditText)findViewById(R.id.ETEmail);

        String uNm=usrnm.getText().toString();
        String Nm=nm.getText().toString();
        String passwrd=pass.getText().toString();
        String confirmPassword=conPass.getText().toString();
        String telno=TelNo.getText().toString();
        int telenumber=Integer.parseInt(telno);
        String emailCus=Email.getText().toString();
        if(uNm.length()>0 && Nm.length()>0 &&passwrd.length()>0 &&confirmPassword.length()>0 &&telno.length()>0 &&emailCus.length()>0) {
            if (passwrd.equals(confirmPassword)) {
                boolean exist = dbCon.SearchUserInfor(uNm);
                if (!exist) {
                    Toast.makeText(Register.this, "Passwords match", Toast.LENGTH_SHORT).show();
                    setAndGetAllVariables SG = new setAndGetAllVariables();
                    SG.setCusid(uNm);
                    SG.setCusName(Nm);
                    SG.setPwd(passwrd);
                    SG.setTelNo(telenumber);
                    SG.setEmail(emailCus);
                    dbCon.insertCustomer(SG);
                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Register.this, Login.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Register.this, "Username already taken", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Register.this, "Passwords don't match", Toast.LENGTH_SHORT).show();

            }
        }
        else{
            Toast.makeText(Register.this, "Fields annot be blank", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Register.this, MainActivity.class));
        finish();

    }
}
