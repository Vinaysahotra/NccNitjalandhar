package com.example.nccnitjalandhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class termsandcondition extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termsandconditions);
        TextView t1=(TextView) findViewById(R.id.continue1);
        t1.setOnClickListener((View.OnClickListener) this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.continue1:

                Intent i=  new Intent(termsandcondition.this,gallery.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"thanks",Toast.LENGTH_SHORT).show();
        }}

}