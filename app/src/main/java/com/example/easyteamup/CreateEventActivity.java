package com.example.easyteamup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String[] paths = {"Sports", "Studying", "Social",
            "Academic", "Food/Drink", "Other"};
    private Spinner spinner;

    protected BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);


        EditText eventNameInput = (EditText) findViewById(R.id.event_name);
        EditText eventDescriptionInput = (EditText) findViewById(R.id.event_description);
        Button createEventButton = (Button) findViewById(R.id.create_event);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(CreateEventActivity.this,
                android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        createEventButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String eventName = eventNameInput.getText().toString();
                System.out.println("Event name: " + eventName);
                //String eventType = eventTypeInput.getText().toString();
                //System.out.println("Event type: " + eventType);
                String eventDescription = eventDescriptionInput.getText().toString();
                System.out.println("Event description: " + eventDescription);

                Map<String, Object> data = new HashMap<>();
                //data.put("eventType", eventType);
                data.put("eventDescription", eventDescription);
                data.put("eventName", eventName);
                String id = db.collection("events").document().getId();
                data.put("EventID", id);
                db.collection("events").document(id).set(data);

                startActivity(new Intent(CreateEventActivity.this, ManageEventActivity.class));
            }
        });
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.create_event_page);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


