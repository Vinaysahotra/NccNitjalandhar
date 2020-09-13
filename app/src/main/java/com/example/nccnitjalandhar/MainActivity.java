package com.example.nccnitjalandhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class MainActivity extends AppCompatActivity {
EditText email,password1,username1;
AwesomeValidation awesomeValidation;
information_storage db;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        information_storage info=new information_storage(this);
        final Button b=(Button)findViewById(R.id.login1);
   db=new information_storage(this);


        email=findViewById(R.id.editTextTextEmailAddress);
        password1=findViewById(R.id.password);
        username1=findViewById(R.id.username);



        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.username, RegexTemplate.NOT_EMPTY,R.string.invalid);
        awesomeValidation.addValidation(this,R.id.editTextTextEmailAddress, Patterns.EMAIL_ADDRESS,R.string.mail);
        awesomeValidation.addValidation(this,R.id.password,".{6,}",R.string.invalidpassword);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(awesomeValidation.validate()){
                            String  emailq=email.getText().toString().trim();
                            String  name=username1.getText().toString().trim();
                            String passwordq=password1.getText().toString().trim();
                            boolean val=db.adduser(name,passwordq,emailq);

if(val){
    Toast.makeText(getApplicationContext(),"login successfull",Toast.LENGTH_SHORT).show();
}

                            Intent i=  new Intent(MainActivity.this,termsandcondition.class);
                            i.putExtra("email",emailq);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"login failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



}