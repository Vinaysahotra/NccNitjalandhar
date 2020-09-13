package com.example.nccnitjalandhar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recyclerview extends RecyclerView.Adapter<recyclerview.ViewHolder>{
    private static final String TAG = "recyclerview";

    private ArrayList<File> mImagesname=new ArrayList<>();
    private Context mcontext;
  public   String items[];

    public recyclerview(  Context mcontext,ArrayList<File> mImagesname,String mitms[]) {

        this.mImagesname = mImagesname;

        this.mcontext = mcontext;
        this.items=mitms;

}

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.container,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
holder.name.setText(items[position]);

    }

    @Override
    public int getItemCount() {
        return mImagesname.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

TextView name;
CircleImageView imageicon;

    public ViewHolder(View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.pdfname);
      CircleImageView c=itemView.findViewById(R.id.circleimage);

    }


}

}
