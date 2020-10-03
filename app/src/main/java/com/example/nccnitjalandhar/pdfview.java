package com.example.nccnitjalandhar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class pdfview extends AppCompatActivity {
PDFView pdf1;
int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);
        pdf1=findViewById(R.id.pdfView);
position=getIntent().getIntExtra("position",-1);
 viewpdf();

    }

    private void viewpdf() {
        pdf1.fromFile(pdf.mnames.get(position))
                .enableSwipe(true)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();


    }

}