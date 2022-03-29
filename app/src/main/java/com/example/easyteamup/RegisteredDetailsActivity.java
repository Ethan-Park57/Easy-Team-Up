package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RegisteredDetailsActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView nameTextView, locationTextView, descriptionTextView, proposedTextView;
    String id, name, location, description, dueTime, proposed1, proposed2, proposed3;
    RadioButton proposed1_button, proposed2_button, proposed3_button;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_details);

    nameTextView = findViewById(R.id.event_name_text_view_details_page);
    locationTextView = findViewById(R.id.event_location_text_view);
    descriptionTextView = findViewById(R.id.description_text_view);
    proposed1_button = findViewById(R.id.proposed_time1);
    proposed2_button = findViewById(R.id.proposed_time_2);
    proposed3_button = findViewById(R.id.proposed_time_3);

    getData();
    setData();

    Button withdraw = findViewById(R.id.withdraw_button_details);
    Button cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(RegisteredDetailsActivity.this, ManageEventActivity.class));
        }
    });

        withdraw.setOnClickListener(new View.OnClickListener() {
        Event e;
        @Override
        public void onClick(View v) {
            DocumentReference docRef = db.collection("events").document(id);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    e = documentSnapshot.toObject(Event.class);
                    ArrayList<String> participantsCopy = new ArrayList<>();
                    if (e.getParticipants() != null) {
                        participantsCopy = e.getParticipants();
                    }
                    participantsCopy.remove(user.getUid());
                    db.collection("events").document(id).update("participants", participantsCopy);
                    Toast.makeText(RegisteredDetailsActivity.this, "Succesfuly withdrawn from the event!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    });

}

    private void getData() {
        if (getIntent().hasExtra("name") && getIntent().hasExtra("location")) {
            id = getIntent().getStringExtra("id");
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