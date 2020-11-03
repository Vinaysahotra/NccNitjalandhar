package com.example.nccnitjalandhar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nccnitjalandhar.messages.allmessages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.Myviewholder>{
    private List<allmessages> chats;
    Context context;
    public  static final int MSG_TYPE_LEFT=0;
    public static final int MSG_RIGHT_=1;
    FirebaseUser firebaseUser;
private String username;

    public messageAdapter(Context context,List<allmessages> chats,String username) {
        this.chats= chats;
        this.context = context;
        this.username=username;
    }


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_RIGHT_){
        View view= LayoutInflater.from(context).inflate(R.layout.rightmessage,parent,false);
        return new messageAdapter.Myviewholder(view);}
        else {
            View view2= LayoutInflater.from(context).inflate(R.layout.left_message,parent,false);
            return new messageAdapter.Myviewholder(view2);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final Myviewholder holder, int position) {
        final allmessages singlechat=chats.get(position);
        holder.showmessages.setText(singlechat.getMessage());

if(position==chats.size()-1){
    if(singlechat.isSeen()){
        holder.textView_seen.setText("seen");
    }
    else {
        holder.textView_seen.setText("delivered");
    }
}
else {
    holder.textView_seen.setVisibility(View.GONE);
}

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
    public static class Myviewholder extends RecyclerView.ViewHolder{
        TextView name;
        TextView showmessages;
        public  TextView textView_seen;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.sender);
            showmessages=itemView.findViewById(R.id.showmessage);
            textView_seen=itemView.findViewById(R.id.txt_seen);

        }
    }

    @Override
    public int getItemViewType(int position) {
       firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
       if(chats.get(position).getSender().equals(firebaseUser.getUid())){
           return MSG_RIGHT_;
       }
       else {
           return MSG_TYPE_LEFT;
       }


    }

}
