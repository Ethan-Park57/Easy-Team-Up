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
import java.util.Calendar;
import java.util.Date;
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
    ArrayList<Event> registeredEventsList = new ArrayList<>();
    RegisteredEventsRecyclerViewAdapter registeredAdapter;
    ArrayList<String> notif = new ArrayList<>();
    String deadlinedEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        recyclerView = findViewById(R.id.notifs_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();

        db.collection("events").whereArrayContains("participants", id)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int i = 0;
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    // System.out.println(document.getId() + "=> " + document.getData());
                    registeredEventsList.add(document.toObject(Event.class));
                    System.out.println("reg size: before checking time: " + registeredEventsList.size());
                    Calendar current = Calendar.getInstance();
                    //Date date = new Date(String.valueOf(registeredEventsList.get(i).getDueTime()));
                    Date deadlineJavaDate = registeredEventsList.get(i).getDueTime().toDate();
                    if(deadlineJavaDate.before(current.getTime())){
                        deadlinedEvent = registeredEventsList.get(i).getEventID();
                        System.out.println("deadline passed....!");

                    }else{
                        System.out.println("deadline NOT passed....!");

                    }
                    System.out.println("we're in the registered events in notifcations" + document);
                    System.out.println("reg size:++: " + registeredEventsList.size());
                    // buf.add(document.toObject(Event.class));
                    i++;
                }
                //registeredAdapter = new RegisteredEventsRecyclerViewAdapter(this, registeredEventsList);
                //recyclerView.setAdapter(registeredAdapter);
                // firestoreCallback.onCallback(sentEventsList);
            } else {
                System.out.println("Error getting documents: " + task.getException());
            }
        });
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
            notif = hostUser.get(0).getNotifications();
            if(deadlinedEvent != null){
                String eventID;
                notif.add("@" + deadlinedEvent);
                db.collection("users").document(user.getUid()).update("notifications", notif);
            }
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