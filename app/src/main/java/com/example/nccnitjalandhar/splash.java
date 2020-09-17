package com.example.nccnitjalandhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
Handler handler=new Handler();
handler.postDelayed(new Runnable() {
    @Override
    public void run() {
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("activity_executed", false)) {
            Intent intent = new Intent(splash.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(splash.this, login.class);
            startActivity(intent);
            finish();
        }
    }
},4000);


    }

}