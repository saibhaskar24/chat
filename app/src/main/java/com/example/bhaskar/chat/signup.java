package com.example.bhaskar.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class signup extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText name,e,p;
    Button cr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.name);
        e=(EditText)findViewById(R.id.e);
        p=(EditText)findViewById(R.id.p);
        cr = (Button)findViewById(R.id.cr);
    }

    public void cr(View view) {

        String n = name.getText().toString();
        String email = e.getText().toString();
        String password = p.getText().toString();
        reg(n,email,password);
    }

    private void reg(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(signup.this,MainActivity.class);
                    Toast.makeText(signup.this,"sucess",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(signup.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
