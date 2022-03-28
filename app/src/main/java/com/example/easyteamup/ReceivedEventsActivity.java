package com.example.easyteamup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ReceivedEventsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Event> buf = new ArrayList<>();
    ArrayList<Event> receivedEventsList = new ArrayList<>();
    // somethign like this
//    FirebaseUser currentUser = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceivedEventsActivity.this, ManageEventActivity.class));
            }
        });
    }

//        readData (list-> {
//            if (receivedEventsList.size() != buf.size()) {
//                addData();
//            }
//        });
//
//    }
//
//    private void readData(FirestoreCallback firestoreCallback) {
//        db.collection("ReceivedEvents").whereEqualTo("invitees", currentUser.getUID())
//                .get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                    // System.out.println(document.getId() + "=> " + document.getData());
//                    buf.add(document.toObject(Event.class));
//                }
//                firestoreCallback.onCallback(receivedEventsList);
//            } else {
//                System.out.println("Error getting documents: " + task.getException());
//            }
//        });
//    }
//
//    private void addData() {
//        System.out.println(buf.size());
//        receivedEventsList.addAll(0, buf);
//        // receivedAdapter.notifyItemRangeInserted(0, buf,size());
//    }
//
//    private interface FirestoreCallback {
//        void onCallback(ArrayList<Event> list);
//    }
}
