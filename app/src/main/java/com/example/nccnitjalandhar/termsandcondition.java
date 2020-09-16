package com.example.nccnitjalandhar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.TedPermission;

import java.security.Permission;

public class termsandcondition extends AppCompatActivity implements View.OnClickListener{
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static final String[]permissions_storage= {
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE

};
    private  static final String[]permissionscontacts= {
            Manifest.permission.CALL_PHONE
    };


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.termsandconditions);
        checkpermissions();
        TextView t1= findViewById(R.id.continue1);

        t1.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void checkpermissions(){


        int permissionwrite_storage=ActivityCompat.checkSelfPermission(termsandcondition.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_callphone=ActivityCompat.checkSelfPermission(termsandcondition.this,Manifest.permission.CALL_PHONE);

        if(permissionwrite_storage!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(termsandcondition.this,
                    permissions_storage,
                    1);


        }


    }




    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.continue1) {
          Intent i = new Intent(termsandcondition.this, MainActivity.class);
            startActivity(i);
            finish();
            Toast.makeText(getApplicationContext(), "thanks", Toast.LENGTH_SHORT).show();
        }
    }


}