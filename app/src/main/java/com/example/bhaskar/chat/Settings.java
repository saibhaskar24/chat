package com.example.bhaskar.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {

    DatabaseReference databaseReference ;
    FirebaseUser currentUser;
    Image image;

    TextView name,status;
    Button change_pic,change_status;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        name = (TextView) findViewById(R.id.name);
        status = (TextView) findViewById(R.id.status);
        change_pic = (Button) findViewById(R.id.change_pic);
        change_status = (Button) findViewById(R.id.change_status);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Change Status");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            assert  getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating : ");
        progressDialog.setMessage("please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String n,s,im,im_t;
                n = dataSnapshot.child("name").getValue().toString();
                s = dataSnapshot.child("status").getValue().toString();
                im = dataSnapshot.child("image").getValue().toString();
                im_t = dataSnapshot.child("thumb_image").getValue().toString();
                name.setText(n);
                status.setText(s);
                progressDialog.hide();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public  void  change_status(View view) {
        Intent intent = new Intent(this, ChangeStatus.class);
        startActivity(intent);
    }

    public  void  change_pic(View view) {

        Intent gintent = new Intent();
        gintent.setType("image/*");
        gintent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gintent.createChooser(gintent,"Select image"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK) {
            String imageUri = data.getDataString();

            Toast.makeText(this,imageUri,Toast.LENGTH_LONG).show();
        }
    }
}
