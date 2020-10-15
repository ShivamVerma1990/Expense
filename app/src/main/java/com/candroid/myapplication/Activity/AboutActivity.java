package com.candroid.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.candroid.myapplication.DashActivity;
import com.candroid.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AboutActivity extends AppCompatActivity {
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom);

        bottomNavigationView.setSelectedItemId(R.id.About);
toolbar=findViewById(R.id.toolbar);
        setToolbar();

bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.DashBoard:
                        startActivity(new Intent(getApplicationContext(), DashActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.About:

                        return true;

                }

                return false;
            }
        });

    }
    public  void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About");


    }


}
