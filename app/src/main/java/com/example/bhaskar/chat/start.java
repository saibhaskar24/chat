package com.example.bhaskar.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class start extends AppCompatActivity {

    Button c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        c = (Button)findViewById(R.id.c);
    }
    public  void c(View view) {
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
        finish();
    }
}
