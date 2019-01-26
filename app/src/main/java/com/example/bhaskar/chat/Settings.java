package com.example.bhaskar.chat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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

    TextView name,status;
    Button change_pic,change_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        name = (TextView) findViewById(R.id.name);
        status = (TextView) findViewById(R.id.status);
        change_pic = (Button) findViewById(R.id.change_pic);
        change_status = (Button) findViewById(R.id.change_status);

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        })
    }
}
