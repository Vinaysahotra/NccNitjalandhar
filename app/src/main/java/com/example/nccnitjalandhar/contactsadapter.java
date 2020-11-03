package com.example.nccnitjalandhar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nccnitjalandhar.news_model.Article;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.auth.User;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.List;
import com.firebase.*;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.nccnitjalandhar.userinfo.*;
public class contactsadapter extends RecyclerView.Adapter<contactsadapter.Myviewholder>{
    private List<userinfo> users;
  private   Context context;
private boolean ischats;
    public contactsadapter(Context context,List<userinfo> users,boolean ischats) {
        this.users = users;
        this.context = context;
        this.ischats=ischats;
    }


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.contact_container,parent,false);

        return new contactsadapter.Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, int position) {
        final userinfo user=users.get(position);
          holder.setIsRecyclable(false);
          holder.useremail.setText(user.getEmail());
        holder.username.setText(user.getUsername());

        if(user.getProfilepic().equals("default")){
            holder.userimage.setImageResource(R.drawable.profilepic);
        }
        else {
            Glide.with(holder.userimage.getContext()).load(user.getProfilepic()).into(holder.userimage);
        }
        if(user.getStatus()!=null) {
            if (user.getStatus().equals("offline")) {
                holder.online.setVisibility(View.GONE);
                holder.offline.setVisibility(View.VISIBLE);
            } else if (user.getStatus().equals("online")) {
                holder.online.setVisibility(View.VISIBLE);
                holder.offline.setVisibility(View.GONE);
            }
        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,chats.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id",user.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class Myviewholder extends RecyclerView.ViewHolder{
private TextView username;
private CircleImageView userimage;
private TextView useremail;
private ImageView online;
private ImageView offline;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            userimage=itemView.findViewById(R.id.profile_contact);
            username=itemView.findViewById(R.id.username);
            useremail=itemView.findViewById(R.id.emailrecycler);
            online=itemView.findViewById(R.id.status_imgon);
            offline=itemView.findViewById(R.id.status_imgoff);

        }
    }
}
