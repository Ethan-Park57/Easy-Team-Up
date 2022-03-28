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

public class ManageEventActivity extends AppCompatActivity {
    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events);

        Button sentPageButton = (Button) findViewById(R.id.to_sent_button);
        sentPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageEventActivity.this, SentEventsActivity.class));

            }
        });

        Button receivedPageButton = (Button) findViewById(R.id.to_received_button);
        receivedPageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ManageEventActivity.this, ReceivedEventsActivity.class));

            }
        });

        Button registeredEventsButton = (Button) findViewById(R.id.to_registered_button);
        registeredEventsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(ManageEventActivity.this, RegisteredEventsActivity.class));

            }
        });

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.manage_event_page);
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