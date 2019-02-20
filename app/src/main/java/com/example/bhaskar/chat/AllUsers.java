package com.example.bhaskar.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AllUsers extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    Query databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        recyclerView = (RecyclerView) findViewById(R.id.allusers_list);
        //firebaseRecyclerAdapter.startListening();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("All Users");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            assert  getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    protected void onStart() {
        Log.d("started","YES");
        super.onStart();
        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(databaseReference,Users.class).build();
        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter  = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull Users model) {
                holder.setname(model.getName());
                holder.setstatus(model.getStatus());
                holder.setimage(model.getThumb_image());
                final String userid = getRef(position).getKey();
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AllUsers.this,profileactivity.class);
                        intent.putExtra("userid", userid);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.singleuser, viewGroup, false);
                return new UsersViewHolder(view);
            }
        };


        recyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }
}
