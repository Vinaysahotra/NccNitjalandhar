package com.example.nccnitjalandhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class fullscreen extends AppCompatActivity implements View.OnClickListener {
ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        imageView=(ImageView)findViewById(R.id.full);

        getSupportActionBar().setTitle("full image");
        Log.d( "myapp","hello");
        Intent i=getIntent();
        int position=i.getExtras().getInt("l");
        ImageAdapter imageAdapter=new ImageAdapter(this);
        imageView.setImageResource(imageAdapter.imagearray[position]);
        Button  next=(Button)findViewById(R.id.next);
        Button  save=(Button)findViewById(R.id.saveimage);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                FileOutputStream outputStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + "/Mypics");
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
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next) {
            Intent i = new Intent(fullscreen.this, MainActivity.class);

            startActivity(i);
        }
    }



}