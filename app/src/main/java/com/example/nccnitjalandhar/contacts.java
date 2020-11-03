package com.example.nccnitjalandhar;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class contacts extends AppCompatActivity {
FirebaseAuth firebaseAuth;
FirebaseUser user;
StorageReference mStorage;
RecyclerView recyclerView;
DatabaseReference reference;
contactsadapter myadapter;

Uri images[];
private List<userinfo> musers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        recyclerView=findViewById(R.id.recylerv_view_contact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
       reference=FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid());
musers=new ArrayList<>();

readusers();

firebaseAuth=FirebaseAuth.getInstance();
user=firebaseAuth.getCurrentUser();

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);




    }

    private void readusers() {
        final FirebaseUser currentuser=FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference
                =FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
musers.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    userinfo user=dataSnapshot.getValue(userinfo.class);
                    assert user != null;
                    assert currentuser != null;
                    if(!user.getEmail().equals(currentuser.getEmail())&&!musers.contains(user)){
                        musers.add(user);
                    }

                }
                myadapter=new contactsadapter(contacts.this,musers,false);
                recyclerView.setAdapter(myadapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    private void status(String status){
        reference=FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
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
    }


}