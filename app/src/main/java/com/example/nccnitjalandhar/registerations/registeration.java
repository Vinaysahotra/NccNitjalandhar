package com.example.nccnitjalandhar.registerations;

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
import com.example.nccnitjalandhar.MainActivity;
import com.example.nccnitjalandhar.R;
import com.example.nccnitjalandhar.termsandcondition;
import com.example.nccnitjalandhar.userinfo;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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
private  static final int RC_SIGN_IN=123;
   DatabaseReference data;
    private  FirebaseAuth mAuth;
     String userID;
    private  GoogleSignInClient client;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);
        sign_in=findViewById(R.id.signin);
        setpassword=findViewById(R.id.setpassword);
        email=findViewById(R.id.email);
        alreadyuser=findViewById(R.id.alreadyuser);
        username=findViewById(R.id.username);
        SignInButton signInButton_google = findViewById(R.id.sign_in_google);
        progressBar=findViewById(R.id.progressBar1);
awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.username, RegexTemplate.NOT_EMPTY, R.string.invalid);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.mail);
        awesomeValidation.addValidation(this, R.id.setpassword, ".{6,}", R.string.invalidpassword);
        mAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions options=new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client= GoogleSignIn.getClient(this,options);
signInButton_google.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent signIntent=client.getSignInIntent();
        startActivityForResult(signIntent,RC_SIGN_IN);
    }
});


        fstore=FirebaseFirestore.getInstance();
        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login.class));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == RC_SIGN_IN) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account=task.getResult(ApiException.class);
            assert account != null;
            firebaseGoogle(account.getIdToken());

        } catch (ApiException e) {
            Toast.makeText(registeration.this,"error is"+ e,Toast.LENGTH_SHORT).show();


        }
    }
}

    private void firebaseGoogle(String idToken) {
        AuthCredential authCredential= GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user=mAuth.getCurrentUser();
                    assert user != null;
                    updateUI(user);

                    startActivity(new Intent(getApplicationContext(), termsandcondition.class));
                    finish();

                }
                else {
                    Toast.makeText(registeration.this,"google not linked ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateUI(FirebaseUser user) {

        data= FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        userinfo classuser=new userinfo(user.getEmail(),user.getDisplayName(),"default",user.getUid(),"offline");
        data.setValue(classuser);
        DocumentReference documentReference=fstore.collection("users").document(user.getUid());
        Map<String,Object>user1=new HashMap<>();
        user1.put("fname",user.getDisplayName());
        user1.put("email",user.getEmail());
        documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(registeration.this,"Sign in done..",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("users","error is"+e.toString());
            }
        });
        }
}
