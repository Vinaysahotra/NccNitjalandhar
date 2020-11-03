package com.example.nccnitjalandhar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.nccnitjalandhar.messages.allmessages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class chats extends AppCompatActivity {
CircleImageView profilephoto;
TextView username;
FirebaseUser fuser;
Intent intent;
ValueEventListener seenlistener;
ImageButton send;
RecyclerView chats;
messageAdapter msgAdapter;
List<allmessages>mchat;
EditText messagesent;
DatabaseReference reference;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Toolbar toolbar=findViewById(R.id.toolbar2);
        setActionBar(toolbar);
        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.example.nccnitjalandhar.chats.this,contacts.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        fuser= FirebaseAuth.getInstance().getCurrentUser();

profilephoto=findViewById(R.id.toolbarimage);
username=findViewById(R.id.toolbarname);
messagesent=findViewById(R.id.message);

send=findViewById(R.id.send);


        chats=findViewById(R.id.chatsrecycle);
        chats.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        chats.setLayoutManager(linearLayoutManager);


intent=getIntent();
final String id=intent.getStringExtra("id");

send.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String message=messagesent.getText().toString();
        if(!message.equals("")){
            sendmessage(fuser.getUid(),id,message);
        }
        else{
            Toast.makeText(chats.this,"can't send empty message",Toast.LENGTH_SHORT).show();
        }
        messagesent.setText("");
    }
});
        reference= FirebaseDatabase.getInstance().getReference("users").child(id);
reference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
       userinfo user=snapshot.getValue(userinfo.class);
        assert user != null;
        username.setText(user.getUsername());

       if(!user.getProfilepic().equals("default")){
           Glide.with(getApplicationContext()).load(user.getProfilepic()).into(profilephoto);
       }
       readMessages(fuser.getUid(),id,username.toString());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
seenMessage(id);


    }
    private void seenMessage(final String userId){
        reference=FirebaseDatabase.getInstance().getReference("chats");
        seenlistener=reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    allmessages message=dataSnapshot.getValue(allmessages.class);
if(message.getReciever().equals(fuser.getUid())&& message.getSender().equals(userId)) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("seen", true);
    dataSnapshot.getRef().updateChildren(map);

}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void sendmessage(String sender,String reciever,String message){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object>map=new HashMap<>();
        map.put("sender",sender);
        map.put("reciever",reciever);
        map.put("message",message);
        map.put("seen",false);
        reference.child("chats").push().setValue(map);
    }
    private  void readMessages(final String  myId, final String userId, final String username){
        mchat=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mchat.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    allmessages chat=dataSnapshot.getValue(allmessages.class);
                    assert chat != null;
                    if(chat.getReciever().equals(myId)&&chat.getSender().equals(userId)||
                        chat.getReciever().equals(userId)&&chat.getSender().equals(myId)){
            mchat.add(chat);
                    }
                    msgAdapter=new messageAdapter(chats.this,mchat,username);
                    chats.setAdapter(msgAdapter);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void status(String status){
        reference=FirebaseDatabase.getInstance().getReference("users").child(fuser.getUid());
        HashMap<String,Object>map=new HashMap<>();
        map.put("status",status);
        reference.updateChildren(map);

    }

    @Override
    protected void onResume() {

        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();

        status("offline");
        reference.removeEventListener(seenlistener);
    }



}