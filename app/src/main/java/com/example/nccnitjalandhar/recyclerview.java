package com.example.nccnitjalandhar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recyclerview extends RecyclerView.Adapter<recyclerview.ViewHolder>{
    private  final String TAG = "recyclerview";

    private ArrayList<File> mImagesname=new ArrayList<>();
    private Context mcontext;
  public   String items[];


    public recyclerview(Context mcontext, ArrayList<File> mImagesname, String mitms[]) {

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
holder.count.setText(String.valueOf(position+1));
holder.name.setText(items[position]);
holder.rout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(mcontext,pdfview.class);
        i.putExtra("position",position);
        mcontext.startActivity(i);
    }
});
    }

    @Override
    public int getItemCount() {
        return mImagesname.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView imageicon;
        RelativeLayout rout;
        TextView count;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageicon = itemView.findViewById(R.id.imageView3);
            count = itemView.findViewById(R.id.countpdf);
            rout = itemView.findViewById(R.id.relative);
//        itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//   mImagesname.remove(getAdapterPosition());
//                notifyItemRemoved(getAdapterPosition());
//                return true;
//            }
//        } );
        }
    }



}
