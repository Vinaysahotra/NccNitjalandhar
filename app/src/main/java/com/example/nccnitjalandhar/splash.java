package com.example.nccnitjalandhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splash extends AppCompatActivity {
    FirebaseUser currentuser;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mauth=FirebaseAuth.getInstance();
  currentuser=mauth.getCurrentUser();
Handler handler=new Handler();
handler.postDelayed(new Runnable() {
    @Override
    public void run() {
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("activity_executed", false)&&currentuser!=null) {
            Intent intent = new Intent(splash.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            if(FirebaseAuth.getInstance().getCurrentUser()==null) {
                Intent intent = new Intent(splash.this, login.class);
                startActivity(intent);
                finish();
            }
        }
    }
},4000);


    }

}