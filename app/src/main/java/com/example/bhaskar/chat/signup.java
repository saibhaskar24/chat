package com.example.bhaskar.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextInputLayout name,e,p;
    Button cr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        name=(TextInputLayout)findViewById(R.id.name);
        e=(TextInputLayout)findViewById(R.id.email);;
        p=(TextInputLayout)findViewById(R.id.pass);;
        cr = (Button)findViewById(R.id.cr);
    }

    public void cr(View view) {

        String email = e.getEditText().toString();
        String password = p.getEditText().toString();
        
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                }
            }
        });

    }
}
