package com.example.nccnitjalandhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.example.nccnitjalandhar.adapters.pdfrecyclerview;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

import static android.os.Environment.*;


public class pdf extends AppCompatActivity {
    public static ArrayList<File> mnames=new ArrayList<>();
    RecyclerView recyclerview1;

    pdfrecyclerview adapter;
    File folder;
    String []item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf);
        folder=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        mnames=getPdfFiles(folder);
        ArrayList<File>mypdf=getPdfFiles(Environment.getExternalStorageDirectory());
        item=new String[mypdf.size()];

        recyclerview1=findViewById(R.id.recylerv_view);

        for(int i=0;i<item.length;i++){
            item[i]=mypdf.get(i).getName().replace(".pdf","");
        }
        adapter=new pdfrecyclerview(this,mnames,item);
        adapter=new pdfrecyclerview(this,mnames,item);
        adapter.notifyDataSetChanged();



        recyclerview1.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerview1.addItemDecoration(dividerItemDecoration);
        recyclerview1.setHasFixedSize(true);
        recyclerview1.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));




    }
    private ArrayList<File> getPdfFiles(File folder) {
        ArrayList<File> array = new ArrayList<>();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File single : files) {
                if (single.isDirectory() && !single.isHidden()) {
                    array.addAll(getPdfFiles(single));
                } else {
                    if (single.getName().endsWith(".pdf")) {
                        array.add(single);
                    }
                }
            }

        }
        return array;
    }
    };


