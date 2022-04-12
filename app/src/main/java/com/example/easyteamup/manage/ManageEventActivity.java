package com.example.easyteamup.manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easyteamup.R;
import com.example.easyteamup.manage.received.ReceivedEventsActivity;
import com.example.easyteamup.manage.registered.RegisteredEventsActivity;
import com.example.easyteamup.manage.sent.SentEventsActivity;

public class ManageEventActivity extends AppCompatActivity {
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

    }

}
