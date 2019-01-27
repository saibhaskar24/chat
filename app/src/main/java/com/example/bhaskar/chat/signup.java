package com.example.bhaskar.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class signup extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    EditText name,e,p;
    Button cr;
    Toolbar mToolbar;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.name);
        e=(EditText)findViewById(R.id.e);
        p=(EditText)findViewById(R.id.p);
        cr = (Button)findViewById(R.id.cr);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Create Account");

        setSupportActionBar(mToolbar);

        if(getSupportActionBar() != null) {
            assert  getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressDialog = new ProgressDialog(this);
    }

    public void cr(View view) {

        String n = name.getText().toString();
        String email = e.getText().toString();
        String password = p.getText().toString();
        if(!TextUtils.isEmpty(n) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
            progressDialog.setTitle("Restering user ");
            progressDialog.setMessage("please wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            reg(n,email,password);
        }

    }

    private void reg(final String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(current_user.getUid());
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("name",name);
                    hashMap.put("status","Hello");
                    hashMap.put("image","default");
                    hashMap.put("thumb_image","default");
                    databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                progressDialog.dismiss();
                                Intent intent = new Intent(signup.this,MainActivity.class);
                                Toast.makeText(signup.this,"sucess",Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                else {
                    progressDialog.hide();
                    Toast.makeText(signup.this, " ERROR ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
