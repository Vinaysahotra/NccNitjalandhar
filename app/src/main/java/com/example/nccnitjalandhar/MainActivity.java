package com.example.nccnitjalandhar;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nccnitjalandhar.adapters.newsAdapter;
import com.example.nccnitjalandhar.api.ApiInterface;
import com.example.nccnitjalandhar.api.Apiclient;
import com.example.nccnitjalandhar.api.utils;
import com.example.nccnitjalandhar.news_model.Article;
import com.example.nccnitjalandhar.news_model.News;
import com.example.nccnitjalandhar.registerations.login;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.Arrays;
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
    public static final String API_KEY = "5fc9a3838f594871a2fdee0fe2ff7ecf";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private newsAdapter adapter;
    private StorageReference mstorage;
    ImageView imageView;
    private static int GALLERY_REQUEST = 123;
    TextView navusername;
    ProgressBar home_pro;
    ProgressBar profilepicload;
    TextView emailuser;
    FirebaseFirestore fsotore;
    FirebaseAuth mauth;
    GoogleSignInClient client;
    FirebaseUser currentuser;
    String userId;
    private AdView mAdView;
    private AdRequest adRequest;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adView);

        adRequest = new AdRequest.Builder().addTestDevice("B673AD81FA6D2CD53A5F891A0A0C1EDD").setHttpTimeoutMillis(7000).build();

        mAdView.loadAd(adRequest);



        mAdView.setAdListener(new AdListener() {


            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.

            }


        });

        home_pro = findViewById(R.id.home_progressBar);
        nav = findViewById(R.id.navmenu);
        drawerlayout = findViewById(R.id.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.open, R.string.close);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        mauth = FirebaseAuth.getInstance();
        fsotore = FirebaseFirestore.getInstance();
        View headerview = nav.getHeaderView(0);
        navusername = headerview.findViewById(R.id.name);
        emailuser = headerview.findViewById(R.id.usermail);
        imageView = headerview.findViewById(R.id.profile_photo);
        currentuser = mauth.getCurrentUser();
        mstorage = FirebaseStorage.getInstance().getReference();
        userId = mauth.getCurrentUser().getUid();


        StorageReference profilepic = mstorage.child("users/" + mauth.getCurrentUser().getUid() + "/images");
        profilepic.child("users/" + mauth.getCurrentUser().getUid() + "/images");
        profilepic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null) {
                    Glide.with(getApplicationContext()).load(uri).into(imageView);


                }
            }


        });


        profilepicload = headerview.findViewById(R.id.loadingphoto);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "pick an image"), GALLERY_REQUEST);

            }
        });

        final DocumentReference documentReference = fsotore.collection("users").document(userId);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                assert value != null;
                if (value.exists()) {

                    navusername.setText(value.getString("fname"));
                    emailuser.setText(value.getString("email"));
                } else if (googleSignInAccount != null) {
                    navusername.setText(googleSignInAccount.getDisplayName());
                    emailuser.setText(googleSignInAccount.getEmail());
                }


            }
        });


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.menu_home:
                        Toast.makeText(getApplicationContext(), "refreshing news", Toast.LENGTH_LONG).show();

                        drawerlayout.closeDrawer(GravityCompat.START);


                        break;
                    case R.id.menu_gallery:
                        Toast.makeText(getApplicationContext(), "gallery opened", Toast.LENGTH_LONG).show();
                        drawerlayout.closeDrawer(GravityCompat.START);
                        Intent i1 = new Intent(MainActivity.this, gallery.class);
                        startActivity(i1);
                        break;
                    case R.id.menu_pdf:
                        home_pro.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "opening pdfs", Toast.LENGTH_LONG).show();
                        drawerlayout.closeDrawer(GravityCompat.START);
                        Intent i3 = new Intent(MainActivity.this, pdf.class);
                        startActivity(i3);
                        home_pro.setVisibility(View.GONE);
                        break;
                    case R.id.menu_contacts:

                        drawerlayout.closeDrawer(GravityCompat.START);
                        Intent i2 = new Intent(MainActivity.this, contacts.class);
                        startActivity(i2);
                        break;
                    case R.id.logout:

                        drawerlayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        Intent i4 = new Intent(MainActivity.this, login.class);
                        startActivity(i4);


                        break;


                }
                return true;
            }
        });


        recyclerView = findViewById(R.id.recyclerview2);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new newsAdapter(articles, this);
        recyclerView.setAdapter(adapter);
        loadJdson("");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Toast.makeText(MainActivity.this, "photo not chosen", Toast.LENGTH_LONG).show();

            return;
        }
        if (requestCode == GALLERY_REQUEST && data != null) {
            profilepicload.setVisibility(View.VISIBLE);
            final Uri imagedata = data.getData();


            final StorageReference fileRef = mstorage.child("users/" + mauth.getCurrentUser().getUid() + "/images");

            assert imagedata != null;
            fileRef.putFile(imagedata)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(MainActivity.this).load(uri).into(imageView);
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("email", emailuser.getText().toString());
                                    map.put("profilepic", uri.toString());
                                    map.put("username", navusername.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("users").child(userId).updateChildren(map);
                                    profilepicload.setVisibility(View.GONE);
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }


    public void loadJdson(final String Keyword) {


        ApiInterface apiInterface = Apiclient.getApiclient().create(ApiInterface.class);
        Call<News> call;
        String country = utils.getCountry();
        String language = utils.getLanguage();
        if (Keyword.length() > 0) {
            call = apiInterface.getNewsSearch(Keyword, language, "publishedAt", API_KEY);
        } else {
            call = apiInterface.getNews(country, API_KEY);
        }
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body().getArticle() != null) {
                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticle();

                    adapter = new newsAdapter(articles, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    initListener();

                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });


    }

    private void initListener() {
        adapter.setOnItemClickListener(new newsAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                Intent i = new Intent(MainActivity.this, news.class);
                Article article = articles.get(position);
                i.putExtra("url", article.getUrl());
                i.putExtra("title", article.getTitle());
                i.putExtra("author", article.getAuthor());
                i.putExtra("date", article.getPublishedAt());
                i.putExtra("image", article.getUrlToImage());
                i.putExtra("source", article.getSource().getName());
                startActivity(i);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem item = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        final androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search latest news");


        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2) {
                    loadJdson(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadJdson(newText);
                return false;
            }
        });
        item.getIcon().setVisible(false, false);
        return true;
    }


}
