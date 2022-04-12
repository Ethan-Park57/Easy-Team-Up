package com.example.easyteamup.manage.sent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyteamup.Event;
import com.example.easyteamup.R;
import com.example.easyteamup.manage.ManageEventActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class SentEventsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Event> sentEventsList = new ArrayList<>();
    ArrayList<Event> buf = new ArrayList<>();
    RecyclerView recyclerView;
    SentEventsRecyclerViewAdapter sentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);

//        for (int i = 1; i <= 10; i++) {
//            Event curr = new Event();
//            curr.setEventName(Integer.toString(i));
//            curr.setHostID(310904);
//            curr.setLocation("location " + Integer.toString(i));
//            db.collection("SentEvents").add(curr);
//        }

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SentEventsActivity.this, ManageEventActivity.class));
            }
        });

        // set up recyclerview and adapter
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        readData(list -> {
            if (sentEventsList.size() != buf.size()) {
                addData();
                System.out.println(sentEventsList.get(0));
                System.out.println(sentEventsList.get(1));
                System.out.println(sentEventsList.get(2));
                System.out.println(sentEventsList.get(3));
                System.out.println(sentEventsList.get(4));
            }
        });

//        System.out.println("Sent events size: " + sentEventsList.size());
//        System.out.println("Buffer  size: " + buf.size());

        // readData();

//        readData(list -> {
//            if (sentEventsList.size() != buf.size()) {
//                addData();
//                System.out.println("Sent events size: " + sentEventsList.size());
//                System.out.println("Buffer  size: " + buf.size());
//            }
//        });

        System.out.println("DONE");
    }
    private void addData() {
        System.out.println(buf.size());
        sentEventsList.addAll(0, buf);
        // sentAdapter.notifyItemRangeInserted(0, buf.size());
    }

    private void readData(FirestoreCallback firestoreCallback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        System.out.println("ID of clare: " + id);

        db.collection("events").whereEqualTo("hostID", id)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    // System.out.println(document.getId() + "=> " + document.getData());
                    sentEventsList.add(document.toObject(Event.class));
                    System.out.println("Document: !!!!!!!!!" + document);
                    // buf.add(document.toObject(Event.class));
                }
                sentAdapter = new SentEventsRecyclerViewAdapter(this, sentEventsList);
                recyclerView.setAdapter(sentAdapter);
                // firestoreCallback.onCallback(sentEventsList);
            } else {
                System.out.println("Error getting documents: " + task.getException());
            }
        });
    }

    private interface FirestoreCallback {
        void onCallback(ArrayList<Event> list);
    }
}
//

//=======
//                .get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                    // System.out.println(document.getId() + "=> " + document.getData());
//                    sentEventsList.add(document.toObject(Event.class));
//                    // buf.add(document.toObject(Event.class));
//                }
//                sentAdapter = new SentEventsRecyclerViewAdapter(this, sentEventsList);
//                recyclerView.setAdapter(sentAdapter);
//                // firestoreCallback.onCallback(sentEventsList);
//            } else {
//                System.out.println("Error getting documents: " + task.getException());
//            }
//        });
//>>>>>>> Stashed changes
//    }
//

//

//}
//



//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_events, R.id.navigation_create, R.id.navigation_manage)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
//
//        Task<QuerySnapshot> ref = db.collection("events").whereEqualTo("hostID", 310904)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    private static final String TAG = "Document Data: ";
//
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//        System.out.println("Out");


//ArrayList<Merchant> merchantsList = new ArrayList<Merchant>();
//db.collection("Merchants")
//        .get()
//        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        System.out.println(domcuent.getID() + " => " + document.getData());
//                    }
//                } else {
//                    Log.d(TAG, "Error getting documents: ", task.getException());
//                }
//            }
//        });
