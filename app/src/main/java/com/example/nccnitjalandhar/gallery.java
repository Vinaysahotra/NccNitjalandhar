package com.example.nccnitjalandhar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class gallery extends AppCompatActivity  {

    GridView gridView;


    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gridView= findViewById(R.id.grid_view);
        gridView.setAdapter(new com.example.nccnitjalandhar.ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),fullscreen.class);
                intent.putExtra("l",i);
                startActivity(intent);
            }
        });

    }

}
