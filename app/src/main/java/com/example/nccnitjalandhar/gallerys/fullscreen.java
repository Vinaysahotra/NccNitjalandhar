package com.example.nccnitjalandhar.gallerys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.nccnitjalandhar.MainActivity;
import com.example.nccnitjalandhar.R;
import com.example.nccnitjalandhar.adapters.ImageAdapter;
import com.example.nccnitjalandhar.registerations.termsandcondition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class fullscreen extends AppCompatActivity implements View.OnClickListener {
ImageView imageView;
    public static final String[] permissions_storage = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        imageView= findViewById(R.id.full);



        Intent i=getIntent();
        int position=i.getExtras().getInt("l");

        ImageAdapter imageAdapter=new ImageAdapter(this);
        imageView.setImageResource(imageAdapter.imagearray[position]);
        Button  next= findViewById(R.id.next);
        Button  save= findViewById(R.id.saveimage);
        save.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View view) {
checkpermissions();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                FileOutputStream outputStream = null;
                File sdCard = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

                File directory = new File(sdCard + "/Mypics");
                directory.mkdir();

                String filename = String.format("%d.jpg",System.currentTimeMillis());
                File outFile = new File(directory, filename);

                try {
                    outputStream= new FileOutputStream(outFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    i.setData(Uri.fromFile(outFile));
                    sendBroadcast(i);
                    Toast.makeText(fullscreen.this, "image is saved in gallery", Toast.LENGTH_SHORT).show();

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        next.setOnClickListener(this);


    }
    private void checkpermissions() {


        int permissionwrite_storage = ActivityCompat.checkSelfPermission(fullscreen.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionread_storage = ActivityCompat.checkSelfPermission(fullscreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionwrite_storage != PackageManager.PERMISSION_GRANTED && permissionread_storage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(fullscreen.this,
                    permissions_storage,
                    1);


        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next) {
            Intent i = new Intent(fullscreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }



}