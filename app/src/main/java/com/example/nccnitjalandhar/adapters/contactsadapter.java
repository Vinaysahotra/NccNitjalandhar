package com.example.nccnitjalandhar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nccnitjalandhar.R;
import com.example.nccnitjalandhar.chats;
import com.example.nccnitjalandhar.userinfo;
import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class contactsadapter extends RecyclerView.Adapter<contactsadapter.Myviewholder> implements Filterable {
    private List<userinfo> users;
    List<userinfo>userswithoutfilter;
  private   Context context;
private boolean ischats;
String status="";
    public contactsadapter(Context context,List<userinfo> users,boolean ischats) {
        this.users = users;
        this.context = context;
        this.ischats=ischats;
        this.userswithoutfilter=new ArrayList<>(users);
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
                status="offline";
                holder.offline.setVisibility(View.VISIBLE);
            } else if (user.getStatus().equals("online")) {
                holder.online.setVisibility(View.VISIBLE);
                holder.offline.setVisibility(View.GONE);
                status="online";
            }
        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, chats.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id",user.getId());
                intent.putExtra("status",status);
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

    @Override
    public Filter getFilter() {

        return filter;
    }
     private    Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<userinfo>filteredusers=new ArrayList<>();
                if(constraint.toString().isEmpty()){
                    filteredusers.addAll(userswithoutfilter);
                }else {
                    for(userinfo userInfo:userswithoutfilter){
                        if(userInfo.getUsername().toLowerCase().trim().contains(constraint.toString().toLowerCase().trim())){
                            filteredusers.add(userInfo);
                        }
                    }
                }
               FilterResults filterResults=new FilterResults();
                filterResults.values=filteredusers;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
users.clear();
users.addAll((List<userinfo> )results.values);
notifyDataSetChanged();
            }
        };
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
