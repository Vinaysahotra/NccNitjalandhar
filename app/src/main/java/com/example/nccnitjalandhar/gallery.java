package com.example.nccnitjalandhar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class gallery extends AppCompatActivity  {

    GridView gridView;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        Button next;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        gridView=(GridView)findViewById(R.id.grid_view);
        gridView.setAdapter(new ImageAdapter(this));
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