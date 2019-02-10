package com.example.bhaskar.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class UsersViewHolder extends RecyclerView.ViewHolder {

    View view;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public  void setname (String name) {
        TextView n = view.findViewById(R.id.singleusername);
        n.setText(name);

    }
}
