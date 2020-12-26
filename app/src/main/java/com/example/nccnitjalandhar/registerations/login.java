package com.example.nccnitjalandhar.registerations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.nccnitjalandhar.R;
import com.example.nccnitjalandhar.termsandcondition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText email,password1;
    AwesomeValidation awesomeValidation;
    Button b;
    TextView signin;
    FirebaseAuth fauth;
TextView resetpass;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
progressBar=findViewById(R.id.progressBar2);

        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        edt.putBoolean("activity_executed", true);
        edt.commit();

b=findViewById(R.id.login1);
        email = findViewById(R.id.editTextTextEmailAddress);
        password1 = findViewById(R.id.password);
        signin=findViewById(R.id.register1);
        resetpass=findViewById(R.id.forgot_password);

resetpass.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final AlertDialog.Builder passordreset=new AlertDialog.Builder(view.getContext());
        final EditText resetpass=new EditText(view.getContext());
        passordreset.setTitle("reset password?");
        passordreset.setMessage("Enter your registered email");
        passordreset.setView(resetpass);
        passordreset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String mail=resetpass.getText().toString();
                fauth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(login.this,"reset link send on mail",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this,"error,"+e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
        passordreset.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        passordreset.create().show();

    }
});
fauth=FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.username, RegexTemplate.NOT_EMPTY, R.string.invalid);
        awesomeValidation.addValidation(this, R.id.editTextTextEmailAddress, Patterns.EMAIL_ADDRESS, R.string.mail);
        awesomeValidation.addValidation(this, R.id.password, ".{6,}", R.string.invalidpassword);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(login.this, registeration.class);
                startActivity(i);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()) {
                    String emailq = email.getText().toString().trim();

                    String passwordq = password1.getText().toString().trim();
                    progressBar.setVisibility(View.VISIBLE);
                    fauth.signInWithEmailAndPassword(emailq, passwordq).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(login.this, "login successful", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), termsandcondition.class));
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(login.this, " error,"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
            }
        });


    }
    }
