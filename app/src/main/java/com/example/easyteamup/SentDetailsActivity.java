package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SentDetailsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView nameTextView, locationTextView, descriptionTextView, dueTimeTextView;
    String eventID;
    String name;
    String location;
    String description;
    String dueTime;
    int dueTimeNanoseconds;
    String proposed1;
    String proposed2;
    String proposed3;
    Long dueTimeSeconds;
    Event e;

    RadioButton proposed1_button, proposed2_button, proposed3_button;
    Button edit_event_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // !!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!
        // !!!!!!!!!!!!!!!!!!!!!!!
        setContentView(R.layout.activity_sent_details);

        nameTextView = findViewById(R.id.event_name_text_view_details_page);
        locationTextView = findViewById(R.id.event_location_text_view);
        descriptionTextView = findViewById(R.id.description_text_view);
        proposed1_button = findViewById(R.id.proposed_time1);
        proposed2_button = findViewById(R.id.proposed_time_2);
        proposed3_button = findViewById(R.id.proposed_time_3);

        getData();
        setData();
        Context context = this;
        Button editEventButton = (Button) findViewById(R.id.edit_event_button);
        editEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send to create event activity but with intents
                Intent intent = new Intent(context, EditEventActivity.class);
                intent.putExtra("eventID", eventID);
                System.out.println("HERE!!!!!!!!!!!!");
                context.startActivity(intent);
            }
        });
    }

    private void getData() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("location")) {
            eventID = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            location = getIntent().getStringExtra("location");
            description = getIntent().getStringExtra("description");
            dueTime = getIntent().getStringExtra("dueTime");
            proposed1 = getIntent().getStringExtra("proposed1");
            proposed2 = getIntent().getStringExtra("proposed2");
            proposed3 = getIntent().getStringExtra("proposed3");
            System.out.println(dueTime);
            System.out.println(proposed1);
            System.out.println(proposed2);
            System.out.println(proposed3);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        nameTextView.setText(name);
        locationTextView.setText(location);
        descriptionTextView.setText(description);
        proposed1_button.setText(proposed1);
        proposed2_button.setText(proposed2);
        proposed3_button.setText(proposed3);
    }
}