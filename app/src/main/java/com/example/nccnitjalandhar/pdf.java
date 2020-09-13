package com.example.nccnitjalandhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

import static android.os.Environment.*;

public class pdf extends AppCompatActivity {
private ArrayList<File>mnames=new ArrayList<>();
RecyclerView recyclerview;
File folder;
String []item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf);
        getSupportActionBar().setTitle("PDFs");
        recyclerview=findViewById(R.id.recylerv_view);
        new ItemTouchHelper(itemtouchhelper).attachToRecyclerView(recyclerview);
        initImages();
    }

    private void initImages(){
folder=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
mnames=getPdfFiles(folder);
ArrayList<File>mypdf=getPdfFiles(Environment.getExternalStorageDirectory());
item=new String[mypdf.size()];
for(int i=0;i<item.length;i++){
    item[i]=mypdf.get(i).getName().replace(".pdf","");
}
initrecyclerview();

    }

    private ArrayList<File> getPdfFiles(File folder) {
        ArrayList<File>array=new ArrayList<>();
        File[]files=folder.listFiles();
        if(files!=null){
            for(File single:files){
                if(single.isDirectory()&&!single.isHidden()){
                    array.addAll(getPdfFiles(single));
                }
                else{
                    if(single.getName().endsWith(".pdf"))
                    {
                        array.add(single);
                    }
                }
            }

        }
        return array;
    }


    private void initrecyclerview(){
        RecyclerView r=findViewById(R.id.recylerv_view);
        recyclerview adapter=new recyclerview(this,mnames,item);
        r.setAdapter(adapter);
        r.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }
    
    ItemTouchHelper.SimpleCallback itemtouchhelper=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mnames.remove(viewHolder.getAdapterPosition());
            recyclerview.notify();
        }
    };
}

