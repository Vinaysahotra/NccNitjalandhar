package com.example.nccnitjalandhar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.nccnitjalandhar.api.ApiInterface;
import com.example.nccnitjalandhar.api.Apiclient;
import com.example.nccnitjalandhar.models.Article;
import com.example.nccnitjalandhar.models.News;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class news extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{
private ImageView imageView;
private TextView appbar_title,appbar_subtitle,date,time,title;
private FrameLayout date_behaviour;
private AppBarLayout appBarLayout;
private  boolean isHideToolbar=false;
private Toolbar toolbar;
private LinearLayout titleappbar;
private String murl,mimg,mtitle,mdate,msource,mauthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);
final CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsing_toolbar);
collapsingToolbarLayout.setTitle("");
appBarLayout=findViewById(R.id.appbar);
        appbar_title=findViewById(R.id.title_on_appbar);
        appbar_subtitle=findViewById(R.id.subtitle_on_appbar);
appBarLayout.addOnOffsetChangedListener(this);
date_behaviour=findViewById(R.id.date_behavior);
        imageView=findViewById(R.id.backdrop);
titleappbar=findViewById(R.id.title_appbar);
title=findViewById(R.id.title);
date=findViewById(R.id.date);

time=findViewById(R.id.time);
        Intent i=getIntent();
        murl=i.getStringExtra("url");
        mimg=i.getStringExtra("image");
        mtitle=i.getStringExtra("title");
        mdate=i.getStringExtra("date");
        msource=i.getStringExtra("source");
        mauthor=i.getStringExtra("author");
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.error(utils.getRandomDrawbleColor());

        Glide.with(this)
                .load(mimg)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);

        date.setText(utils.DateFormat(mdate));
        title.setText(mtitle);
        appbar_title.setText(msource);
appbar_subtitle.setText(murl);

        String author=null;
if(mauthor !=null|| mauthor !=""){
    mauthor="\u2022"+mauthor;
}else {
    author="";
}
time.setText(msource+author+"\u2022"+utils.DateToTimeFormat(mdate));
initWebview(murl);
    }
    private void initWebview(String url){
        WebView webView=findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
int maxScroll=appBarLayout.getTotalScrollRange();
float percentage=(float)Math.abs(verticalOffset)/(float)maxScroll;
if(percentage==1f&&isHideToolbar){
    date_behaviour.setVisibility(View.GONE);
    titleappbar.setVisibility(View.VISIBLE);
    isHideToolbar=isHideToolbar;
}else if(percentage<1f&&isHideToolbar){
    date_behaviour.setVisibility(View.VISIBLE);
    titleappbar.setVisibility(View.GONE);
    isHideToolbar=isHideToolbar;
}
    }
}