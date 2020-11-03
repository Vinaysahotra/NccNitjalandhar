package com.example.nccnitjalandhar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registeration extends AppCompatActivity {
   private EditText username;
   private EditText email;
    Button sign_in;
    TextView alreadyuser;
    AwesomeValidation awesomeValidation ;
    FirebaseFirestore fstore;
   private EditText setpassword;
   ProgressBar progressBar;
   FirebaseDatabase root;
   DatabaseReference data;
    FirebaseAuth mAuth;
     String userID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);
        sign_in=findViewById(R.id.signin);
        setpassword=findViewById(R.id.setpassword);
        email=findViewById(R.id.email);
        alreadyuser=findViewById(R.id.alreadyuser);
        username=findViewById(R.id.username);
        progressBar=findViewById(R.id.progressBar1);
awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.username, RegexTemplate.NOT_EMPTY, R.string.invalid);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.mail);
        awesomeValidation.addValidation(this, R.id.setpassword, ".{6,}", R.string.invalidpassword);
        mAuth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
                finish();

            }
        });
sign_in.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (awesomeValidation.validate()) {
            String textemail = email.getText().toString().trim();
            String textusername = username.getText().toString().trim();
            String textpassword= setpassword.getText().toString().trim();
            progressBar.setVisibility(View.VISIBLE);
            registeruser(textemail,textpassword,textusername);
        }
    }

    private void registeruser(final String textemail, String textpassword, final String username1) {

        mAuth.createUserWithEmailAndPassword(textemail, textpassword)
                .addOnCompleteListener(registeration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(registeration.this, "signed in",Toast.LENGTH_SHORT).show();

                            userID=mAuth.getCurrentUser().getUid();

                            data= FirebaseDatabase.getInstance().getReference("users").child(userID);
                            userinfo classuser=new userinfo(textemail,username1,"default",userID,"offline");
                            data.setValue(classuser);
                            DocumentReference documentReference=fstore.collection("users").document(userID);
                            Map<String,Object>user=new HashMap<>();
                            user.put("fname",username1);
                            user.put("email",textemail);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                   Log.d("users","profile is created"+userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("users","error is"+e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),login.class));
                            finish();

                        } else {
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(registeration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
});
    }
}
