package com.example.nccnitjalandhar;

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

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.nccnitjalandhar.api.ApiInterface;
import com.example.nccnitjalandhar.api.Apiclient;
import com.example.nccnitjalandhar.models.Article;
import com.example.nccnitjalandhar.models.News;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerlayout;
    public  static final String API_KEY="5fc9a3838f594871a2fdee0fe2ff7ecf";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles=new ArrayList<>();
    private newsAdapter adapter;
    private ImageView imageView;
private static final int PICK_IMAGE=1;

    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home);
    Toolbar toolbar = findViewById(R.id.toolbar);
    TextView name=findViewById(R.id.name);
    imageView=findViewById(R.id.profile_photo);

    setSupportActionBar(toolbar);
    nav= findViewById(R.id.navmenu);
    drawerlayout =findViewById(R.id.drawer);

    toggle=new ActionBarDrawerToggle(this,drawerlayout,toolbar,R.string.open,R.string.close);
    drawerlayout.addDrawerListener(toggle);
    toggle.syncState();


    nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){

                case R.id.menu_home:
                    Toast.makeText(getApplicationContext(),"loading news",Toast.LENGTH_LONG).show();

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

    }
