package com.example.bhaskar.chat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MenuInflater;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import okhttp3.internal.Internal;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ViewPager viewPager;
    SectionPagerAdapter sectionPagerAdapter;
    TabLayout tabLayout;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("chat me");
        setSupportActionBar(mToolbar);
        tabLayout = (TabLayout) findViewById(R.id.main_tabs);
        viewPager = (ViewPager) findViewById(R.id.tabPager);
        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(sectionPagerAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            change();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            change();
        }
        else if(item.getItemId() == R.id.menu_settings) {
            Intent intent = new Intent(this,Settings.class);
            startActivity(intent);
        }
        else  if(item.getItemId() == R.id.menu_allusers) {
            Intent intent = new Intent(this,AllUsers.class);
            startActivity(intent);
        }
        return true;
    }

    private void change() {
        Intent intent = new Intent(this,start.class);
        startActivity(intent);
        finish();
    }

}
