package com.example.nccnitjalandhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.ArrayList;

public class login extends AppCompatActivity {

    EditText email,password1,username1;
    AwesomeValidation awesomeValidation;
    Button b;
    information_storage info;
final ArrayList<String>emailstored=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        info = new information_storage(this);
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.putBoolean("activity_executed", true);
        edt.commit();

b=findViewById(R.id.login1);
        email = findViewById(R.id.editTextTextEmailAddress);
        password1 = findViewById(R.id.password);
        username1 = findViewById(R.id.username);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.username, RegexTemplate.NOT_EMPTY, R.string.invalid);
        awesomeValidation.addValidation(this, R.id.editTextTextEmailAddress, Patterns.EMAIL_ADDRESS, R.string.mail);
        awesomeValidation.addValidation(this, R.id.password, ".{6,}", R.string.invalidpassword);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()) {
                    String emailq = email.getText().toString().trim();
                    String name = username1.getText().toString().trim();
                    String passwordq = password1.getText().toString().trim();
                    boolean val = info.adduser(name, passwordq, emailq);
                    Cursor res=info.getdata();
                    int count=0;






                          if (val) {
                        Toast.makeText(getApplicationContext(), "login successfull", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(login.this,termsandcondition.class);
                        startActivity(i);
                        finish();
                    }
                          else if(count==1){
                              Toast.makeText(getApplicationContext(), "login failed as email already exist", Toast.LENGTH_SHORT).show();
                          }



                } else {
                    Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
    }
