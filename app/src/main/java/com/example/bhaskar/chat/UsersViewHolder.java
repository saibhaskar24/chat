package com.example.bhaskar.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

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

    public void setstatus(String status) {
        TextView n = view.findViewById(R.id.singleuserstatus);
        n.setText(status);

    }

    public void setimage(String image) {
        CircleImageView n = view.findViewById(R.id.singleuserprofile);
        Picasso.get().load(image).into(n);

    }
}
