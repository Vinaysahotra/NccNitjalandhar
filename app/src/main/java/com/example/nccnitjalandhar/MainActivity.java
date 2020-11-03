package com.example.nccnitjalandhar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nccnitjalandhar.api.ApiInterface;
import com.example.nccnitjalandhar.api.Apiclient;
import com.example.nccnitjalandhar.news_model.Article;
import com.example.nccnitjalandhar.news_model.News;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerlayout;
    public  static final String API_KEY="5fc9a3838f594871a2fdee0fe2ff7ecf";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles=new ArrayList<>();
    private newsAdapter adapter;
    DatabaseReference reference;
    private StorageReference mstorage;
    ImageView imageView;
    private  static int GALLERY_REQUEST=123;
    String name;
    String email;
    TextView navusername;
    ProgressBar profilepicload;
    TextView emailuser;
    FirebaseFirestore fsotore;
    FirebaseAuth mauth;
    FirebaseUser currentuser;
    String userId;
    private SwipeRefreshLayout swipeRefreshLayout;
private static final int PICK_IMAGE=1;
    private int id;

    @SuppressLint("ResourceAsColor")
    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home);

        nav= findViewById(R.id.navmenu);
        drawerlayout =findViewById(R.id.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toggle=new ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.open,R.string.close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

mauth=FirebaseAuth.getInstance();
fsotore=FirebaseFirestore.getInstance();
currentuser=mauth.getCurrentUser();
        mstorage = FirebaseStorage.getInstance().getReference();
userId= mauth.getCurrentUser().getUid();

 StorageReference profilepic=mstorage.child("users/"+mauth.getCurrentUser().getUid()+"/images");
        profilepic.child("users/" + mauth.getCurrentUser().getUid() + "/images");
        profilepic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if(uri!=null) {
                    Glide.with(MainActivity.this).load(uri).into(imageView);


                }
            }


        });
        View headerview=nav.getHeaderView(0);
         navusername=headerview.findViewById(R.id.name);
         emailuser = headerview.findViewById(R.id.usermail);
        imageView=headerview.findViewById(R.id.profile_photo);
        profilepicload=headerview.findViewById(R.id.loadingphoto);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult( Intent.createChooser(intent,"pick an image"),GALLERY_REQUEST);

            }
        });

        DocumentReference documentReference=fsotore.collection("users").document(userId);
        if(mauth.getCurrentUser()!=null)
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if(value.exists())
                {

                    navusername.setText(value.getString("fname"));
                    emailuser.setText(value.getString("email"));
                }
            }
        });


    nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){

                case R.id.menu_home:
                    Toast.makeText(getApplicationContext(),"refreshing news",Toast.LENGTH_LONG).show();

                    drawerlayout.closeDrawer(GravityCompat.START);


                    break;
                case R.id.menu_gallery:
                    Toast.makeText(getApplicationContext(),"gallery opened",Toast.LENGTH_LONG).show();
                    drawerlayout.closeDrawer(GravityCompat.START);
                    Intent i1=new Intent(MainActivity.this,gallery.class);
                    startActivity(i1);
                    break;
                case R.id.menu_pdf:
                    Toast.makeText(getApplicationContext(),"opening pdfs",Toast.LENGTH_LONG).show();
                    drawerlayout.closeDrawer(GravityCompat.START);
                    Intent i3=new Intent(MainActivity.this,pdf.class);
                    startActivity(i3);
                    break;
                case R.id.menu_contacts:
                    Toast.makeText(getApplicationContext(),"contacts opened",Toast.LENGTH_LONG).show();
                    drawerlayout.closeDrawer(GravityCompat.START);
                    Intent i2=new Intent(MainActivity.this,contacts.class);
                    startActivity(i2);
                    break;
                case R.id.logout:
                    drawerlayout.closeDrawer(GravityCompat.START);
                    mauth.signOut();
                    Intent i4=new Intent(MainActivity.this,login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i4);
                       finish();
                    break;

            }
            return true;
        }
    });


        recyclerView=findViewById(R.id.recyclerview2);
        layoutManager=new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        adapter=new newsAdapter(articles,this);
        recyclerView.setAdapter(adapter);
      loadJdson();


}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Toast.makeText(MainActivity.this, "photo not chosen", Toast.LENGTH_LONG).show();

            return;
        }
        if (requestCode == GALLERY_REQUEST&& resultCode==RESULT_OK&&data!=null) {
profilepicload.setVisibility(View.VISIBLE);
            final Uri imagedata=data.getData();




            final StorageReference fileRef = mstorage.child("users/"+mauth.getCurrentUser().getUid()+"/images");

            fileRef.putFile(imagedata)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
    @Override
    public void onSuccess(Uri uri) {
Glide.with(MainActivity.this).load(uri).into(imageView);
        Map<String,Object>map=new HashMap<>();
        map.put("email",emailuser.getText().toString());
        map.put("profilepic",uri.toString());
        map.put("username",navusername.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).updateChildren(map);
profilepicload.setVisibility(View.GONE);
    }
});

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                           Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        }


    public void loadJdson(){


    ApiInterface apiInterface= Apiclient.getApiclient().create(ApiInterface.class);
    Call<News> call;
    String country=utils.getCountry();
    call=apiInterface.getNews(country,API_KEY);
    call.enqueue(new Callback<News>() {
        @Override
        public void onResponse(Call<News> call, Response<News> response) {
            assert response.body() != null;
            if(response.isSuccessful() && response.body().getArticle() != null){
                if(!articles.isEmpty()){
                    articles.clear();
                }
                articles=response.body().getArticle();
                adapter=new newsAdapter(articles,MainActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                initListener();

            }
            else{
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<News> call, Throwable t) {

        }
    });


}
private void initListener(){
        adapter.setOnItemClickListener(new newsAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                Intent i=new Intent(MainActivity.this,news.class);
              Article article=articles.get(position);
                i.putExtra("url",article.getUrl());
              i.putExtra("title",article.getTitle());
                i.putExtra("author",article.getAuthor());
                i.putExtra("date",article.getPublishedAt());
                i.putExtra("image",article.getUrlToImage());
                i.putExtra("source",article.getSource().getName());


                startActivity(i);


            }
        });
}
    private void status(String status){
        reference=FirebaseDatabase.getInstance().getReference("users").child(currentuser.getUid());
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
