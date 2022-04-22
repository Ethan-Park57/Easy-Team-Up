package com.example.easyteamup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NotificationsActivity extends AppCompatActivity {
    protected BottomNavigationView navigationView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    NotificationsRecyclerViewAdapter notifAdapter;
    ArrayList<User> hostUser = new ArrayList<>();
    ArrayList<User> buf = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        recyclerView = findViewById(R.id.notifs_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        readData(list -> {
            if (hostUser.size() != buf.size()) {
                addData();
            }
        });

    }

    private void addData() {
        System.out.println(buf.size());
        hostUser.addAll(0, buf);
    }

    private void readData(NotificationsActivity.FirestoreCallback firestoreCallback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        System.out.println("ID +:+ " + id);


        db.collection("users").document(id)
                .get().addOnCompleteListener(task -> {
                    System.out.println("Made it here... HI");
            //if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            //for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    // System.out.println(document.getId() + "=> " + document.getData());
                    hostUser.add(document.toObject(User.class));
                    System.out.println("notifications activity page is loaded" + document);
                    // buf.add(document.toObject(Event.class));
                //}
            Map<String, String> notif = new HashMap<>();
            notif = hostUser.get(0).getNotifications();
                notifAdapter = new NotificationsRecyclerViewAdapter(this, notif);
                recyclerView.setAdapter(notifAdapter);
                // firestoreCallback.onCallback(sentEventsList);
            //} else {
                System.out.println("Error getting documents: " + task.getException());
            //}
        });
    }

    private interface FirestoreCallback {
        void onCallback(ArrayList<User> list);
    }




}
