package com.example.easyteamup.manage.registered;

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

public class RegisteredEventsActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Event> buf = new ArrayList<>();
    RecyclerView recyclerView;
    RegisteredEventsRecyclerViewAdapter registeredAdapter;
    ArrayList<Event> registeredEventsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);


        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisteredEventsActivity.this, ManageEventActivity.class));
            }
        });

        // set up recyclerview and adapter
        recyclerView = findViewById(R.id.registered_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        readData(list -> {
            if (registeredEventsList.size() != buf.size()) {
                addData();
            }
        });

        System.out.println("DONE");
    }
    private void addData() {
        System.out.println(buf.size());
        registeredEventsList.addAll(0, buf);
        // sentAdapter.notifyItemRangeInserted(0, buf.size());
    }

    private void readData(FirestoreCallback firestoreCallback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();
        System.out.println("ID of clare: " + id);

        db.collection("events").whereArrayContains("participants", id)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    // System.out.println(document.getId() + "=> " + document.getData());
                    registeredEventsList.add(document.toObject(Event.class));
                    System.out.println("Document: !!!!!!!!!" + document);
                    // buf.add(document.toObject(Event.class));
                }
                registeredAdapter = new RegisteredEventsRecyclerViewAdapter(this, registeredEventsList);
                recyclerView.setAdapter(registeredAdapter);
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






//package com.example.easyteamup;
//
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.view.View;
//        import android.widget.Button;
//
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.recyclerview.widget.LinearLayoutManager;
//        import androidx.recyclerview.widget.RecyclerView;
//
//        import com.google.firebase.auth.FirebaseAuth;
//        import com.google.firebase.auth.FirebaseUser;
//        import com.google.firebase.firestore.FirebaseFirestore;
//        import com.google.firebase.firestore.QueryDocumentSnapshot;
//
//        import java.util.ArrayList;
//        import java.util.Objects;
//
//public class RegisteredEventsActivity extends AppCompatActivity {
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    ArrayList<Event> buf = new ArrayList<>();
//    RecyclerView recyclerView;
//    // RegisteredEventsRecyclerViewAdapter registeredAdapter;
//    ArrayList<Event> registeredEventsList = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registered);
//
//
//        Button backButton = (Button) findViewById(R.id.back_button);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(RegisteredEventsActivity.this, ManageEventActivity.class));
//            }
//        });
//
//        // set up recyclerview and adapter
//        recyclerView = findViewById(R.id.registered_recycler_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        readData(list -> {
//            if (registeredEventsList.size() != buf.size()) {
//                addData();
//            }
//        });
//
//        System.out.println("DONE");
//    }
//    private void addData() {
//        System.out.println(buf.size());
//        registeredEventsList.addAll(0, buf);
//        // sentAdapter.notifyItemRangeInserted(0, buf.size());
//    }
//
//    private void readData(RegisteredEventsActivity.FirestoreCallback firestoreCallback) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String id = user.getUid();
//        System.out.println("ID of clare: " + id);
//
//        db.collection("events").whereEqualTo("hostID", id)
//                .get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                    // System.out.println(document.getId() + "=> " + document.getData());
//                    registeredEventsList.add(document.toObject(Event.class));
//                    System.out.println("Document: !!!!!!!!!" + document);
//                    // buf.add(document.toObject(Event.class));
//                }
//                // registeredAdapter = new SentEventsRecyclerViewAdapter(this, registeredEventsList);
//                // recyclerView.setAdapter(registeredEventsList);
//                // firestoreCallback.onCallback(sentEventsList);
//            } else {
//                System.out.println("Error getting documents: " + task.getException());
//            }
//        });
//    }
//
//    private interface FirestoreCallback {
//        void onCallback(ArrayList<Event> list);
//    }
//}
