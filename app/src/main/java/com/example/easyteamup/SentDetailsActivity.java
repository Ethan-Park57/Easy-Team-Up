package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;

import java.util.Date;

public class SentDetailsActivity extends AppCompatActivity {

    TextView nameTextView, locationTextView, descriptionTextView, proposedTextView;
    String name, location, description, dueTime, proposed1, proposed2, proposed3;
    RadioButton proposed1_button, proposed2_button, proposed3_button;

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
    }

    private void getData() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("location")) {
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