package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyteamup.Event;
import com.example.easyteamup.R;
import com.example.easyteamup.ManageEventActivity;
import com.example.easyteamup.RegisteredDetailsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

public class ReceivedDetailsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView nameTextView, locationTextView, descriptionTextView, proposedTextView;
    String id, hostID, name, location, description, dueTime, proposed1, proposed2, proposed3;
    RadioButton proposed1_button, proposed2_button, proposed3_button;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_details);

        nameTextView = findViewById(R.id.event_name_text_view_details_page);
        locationTextView = findViewById(R.id.event_location_text_view);
        descriptionTextView = findViewById(R.id.description_text_view);
        proposed1_button = findViewById(R.id.proposed_time1);
        proposed2_button = findViewById(R.id.proposed_time_2);
        proposed3_button = findViewById(R.id.proposed_time_3);

        getData();
        setData();

        Button registerButton = findViewById(R.id.register_button_details);
        Button declineButton = findViewById(R.id.decline_button);

        // remove the persons ids from invitees
        declineButton.setOnClickListener(new View.OnClickListener() {
            Event e;
            @Override
            public void onClick(View v) {
                db.collection("events").document(id)
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        e = documentSnapshot.toObject(Event.class);
                        ArrayList<String> inviteesCopy = new ArrayList<>();
                        if (e.getInvitees() != null) {
                            inviteesCopy = e.getInvitees();
                        }
                        inviteesCopy.remove(user.getUid());
                        db.collection("events").document(id).update("invitees", inviteesCopy);
                        Toast.makeText(ReceivedDetailsActivity.this, "Succesfuly declined " +
                                "invitation to the event!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            Event e;
            // add the person to participants and remove from invitees
            @Override
            public void onClick(View v) {
                DocumentReference docRef = db.collection("events").document(id);
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        e = documentSnapshot.toObject(Event.class);
                        DocumentReference hostDocRef = db.collection("users").document(e.getHostID());
                        hostDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshotHost) {
                                User host = documentSnapshotHost.toObject(User.class);
                                ArrayList<String> notificationsCopy;
                                if (host.getNotifications() == null) {
                                    notificationsCopy = new ArrayList<>();
                                } else {
                                    notificationsCopy = host.getNotifications();
                                }
                                notificationsCopy.add("+" + e.getEventID());
                                db.collection("users").document(e.getHostID()).update(
                                        "notifications", notificationsCopy
                                );
                            }
                        });
                        ArrayList<String> participantsCopy = new ArrayList<>();
                        if (e.getParticipants() != null) {
                            participantsCopy = e.getParticipants();
                        }
                        participantsCopy.add(user.getUid());
                        ArrayList<String> inviteesCopy = new ArrayList<>();
                        if (e.getInvitees() != null) {
                            inviteesCopy = e.getInvitees();
                        }
                        inviteesCopy.remove(user.getUid());
                        db.collection("events").document(id).update(
                                "participants", participantsCopy,
                                "invitees", inviteesCopy
                        );
                        Toast.makeText(ReceivedDetailsActivity.this, "Successfully " +
                                "registered for the event!", Toast.LENGTH_SHORT).show();                    }
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
//            System.out.println(dueTime);
//            System.out.println(proposed1);
//            System.out.println(proposed2);
//            System.out.println(proposed3);
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