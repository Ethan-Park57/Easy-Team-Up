package com.example.easyteamup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyteamup.Event;
import com.example.easyteamup.R;
import com.example.easyteamup.ManageEventActivity;
import com.example.easyteamup.RegisteredEventsRecyclerViewAdapter;
import com.example.easyteamup.SentEventsActivity;
import com.example.easyteamup.SentEventsRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ReceivedEventsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Event> buf = new ArrayList<>();
    ArrayList<Event> receivedEventsList = new ArrayList<>();
    RecyclerView recyclerView;
    ReceivedEventsRecyclerViewAdapter receivedAdapter;

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

        // set up recyclerview and adapter
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        readData(list -> {
            if (receivedEventsList.size() != buf.size()) {
                addData();
            }
        });

        System.out.println("DONE");
    }

    private void readData(FirestoreCallback firestoreCallback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        System.out.println("ID of clare: " + id);

        db.collection("events").whereArrayContains("invitees", id)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    // System.out.println(document.getId() + "=> " + document.getData());
                    receivedEventsList.add(document.toObject(Event.class));
                    System.out.println("Document: !!!!!!!!!" + document);
                    // buf.add(document.toObject(Event.class));
                }
                receivedAdapter = new ReceivedEventsRecyclerViewAdapter(this, receivedEventsList);
                recyclerView.setAdapter(receivedAdapter);
                // firestoreCallback.onCallback(sentEventsList);
            }
            else {
                System.out.println("Error getting documents: " + task.getException());
            }
        });
    }

    public ArrayList<Event> readDataTesting(String id) {
        ArrayList<Event> res = new ArrayList<>();

        db.getInstance().collection("events").whereArrayContains("invitees", id)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    res.add(document.toObject(Event.class));
                }
            }
            else {
                System.out.println("Error getting documents: " + task.getException());
            }
        });
        return res;
    }

    private void addData() {
        System.out.println(buf.size());
        receivedEventsList.addAll(0, buf);
        // receivedAdapter.notifyItemRangeInserted(0, buf,size());
    }

    private interface FirestoreCallback {
        void onCallback(ArrayList<Event> list);
    }
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
//}





