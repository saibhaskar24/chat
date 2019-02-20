package com.example.bhaskar.chat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class profileactivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView image;
    TextView name, status;
    DatabaseReference databaseReference,friends,friendrequest;
    FirebaseUser firebaseUser;
    ProgressDialog progressBar ;
    Button rr,sr;
    String uid,muid,state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileactivity);
        image = (ImageView) findViewById(R.id.image);
        name = (TextView) findViewById(R.id.name);
        status = (TextView) findViewById(R.id.status);
        rr = (Button) findViewById(R.id.rr);
        sr = (Button) findViewById(R.id.sr);
        friends = FirebaseDatabase.getInstance().getReference("friends");
        friendrequest = FirebaseDatabase.getInstance().getReference("friendrequest");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        muid = firebaseUser.getUid();
        progressBar = new ProgressDialog(this);
        progressBar.setTitle("Loading");
        progressBar.setMessage("Wait");
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Profile");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            assert  getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        uid = getIntent().getStringExtra("userid");
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String n,s,i;
                n = dataSnapshot.child("name").getValue().toString();
                s = dataSnapshot.child("status").getValue().toString();
                i = dataSnapshot.child("image").getValue().toString();
                name.setText(n);
                status.setText(s);
                Picasso.get().load(i).placeholder(R.drawable.profile).into(image);
                progressBar.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void rr(View view) {


    }

    public void sr(View view) {

        if(state.equals("not sent")) {
            friendrequest.child(uid).child(muid).setValue("sent").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    friendrequest.child(muid).child(uid).setValue("rec");
                    state = "sent";
                    sr.setText("Undo Request");
                }
            });
        }
        else  if(state.equals("sent")) {

        }
         else  if (state.equals("rec")) {

        }


    }
}
