package com.example.easyteamup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class ListViewActivity extends AppCompatActivity {
    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Button listtolist = (Button) findViewById(R.id.listtolist);
        listtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListViewActivity.this, ListViewActivity.class));

            }
        });

        Button listtomap = (Button) findViewById(R.id.listtomap);
        listtomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListViewActivity.this, MapViewActivity.class));
            }
        });

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.events_page);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.events_page:
                        startActivity(new Intent(getApplicationContext(),ListViewActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.create_event_page:
                        startActivity(new Intent(getApplicationContext(),CreateEventActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.manage_event_page:
                        startActivity(new Intent(getApplicationContext(),ManageEventActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile_page:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
