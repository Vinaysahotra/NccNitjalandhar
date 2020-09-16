package com.example.nccnitjalandhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity{
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerlayout;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home);
    Toolbar toolbar = findViewById(R.id.toolbar);
    TextView name=findViewById(R.id.name);

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
                    Toast.makeText(getApplicationContext(),"home opened",Toast.LENGTH_LONG).show();
                    drawerlayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.menu_gallery:
                    Toast.makeText(getApplicationContext(),"gallery opened",Toast.LENGTH_LONG).show();
                    drawerlayout.closeDrawer(GravityCompat.START);
                    Intent i1=new Intent(MainActivity.this,gallery.class);
                    startActivity(i1);
                    break;
                case R.id.menu_pdf:
                    Toast.makeText(getApplicationContext(),"pdfs opened",Toast.LENGTH_LONG).show();
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


}




    }
