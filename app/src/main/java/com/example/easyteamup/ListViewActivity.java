
package com.example.easyteamup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyteamup.manage.ManageEventActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class ListViewActivity extends AppCompatActivity {
    protected BottomNavigationView navigationView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Event> eventsList = new ArrayList<>();
    ArrayList<Event> buf = new ArrayList<>();
    RecyclerView recyclerView;
    ListViewRecyclerViewAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Button listtolist = (Button) findViewById(R.id.listtolist);
        listtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListViewActivity.this, ListViewActivity.class));

            }
        });

        Button listtomap = (Button) findViewById(R.id.listtomap);
        listtomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListViewActivity.this, MapViewActivity.class));
            }
        });

        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.events_page);
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
                        startActivity(new Intent(getApplicationContext(), ManageEventActivity.class));
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

        // set up recyclerview and adapter
        recyclerView = findViewById(R.id.list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        readData(list -> {
            if (eventsList.size() != buf.size()) {
                addData();
//                System.out.println(sentEventsList.get(0));
//                System.out.println(sentEventsList.get(1));
//                System.out.println(sentEventsList.get(2));
//                System.out.println(sentEventsList.get(3));
//                System.out.println(sentEventsList.get(4));
            }
        });
    }

    private void addData() {
        System.out.println(buf.size());
        eventsList.addAll(0, buf);
        // sentAdapter.notifyItemRangeInserted(0, buf.size());
    }

    private void readData(ListViewActivity.FirestoreCallback firestoreCallback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        System.out.println("ID of clare: " + id);

        db.collection("events").whereEqualTo("isPrivateEvent", false)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    // System.out.println(document.getId() + "=> " + document.getData());
                    eventsList.add(document.toObject(Event.class));
                    System.out.println("Document: !!!!!!!!!" + document);
                    // buf.add(document.toObject(Event.class));
                }
                listAdapter = new ListViewRecyclerViewAdapter(this, eventsList);
                recyclerView.setAdapter(listAdapter);
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