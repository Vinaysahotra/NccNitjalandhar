package com.example.nccnitjalandhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

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
        new ItemTouchHelper(itemtouchhelper).attachToRecyclerView(recyclerview1);
        recyclerview1=findViewById(R.id.recylerv_view);

        for(int i=0;i<item.length;i++){
            item[i]=mypdf.get(i).getName().replace(".pdf","");
        }
        adapter=new pdfrecyclerview(this,mnames,item);
        adapter.notifyDataSetChanged();




        recyclerview1.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerview1.addItemDecoration(dividerItemDecoration);
        recyclerview1.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(itemtouchhelper);
        itemTouchHelper.attachToRecyclerView(recyclerview1);


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
    File deleted=null;
    ItemTouchHelper.SimpleCallback itemtouchhelper=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position=viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    Intent i=new Intent(pdf.this,pdfview.class);
                    startActivity(i);
                case ItemTouchHelper.RIGHT:
                    deleted=mnames.get(position);
                    mnames.remove(position);
                   adapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerview1, (CharSequence) deleted, Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mnames.add(position,deleted);
                                    adapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;


            }
        }
    };
}

