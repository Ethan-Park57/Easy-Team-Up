package com.example.easyteamup;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.easyteamup.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_events, R.id.navigation_create, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);



        Map<String, Object> data = new HashMap<>();
        data.put("capital", true);
        db.collection("cities").document("BJ").set(data, SetOptions.merge());


        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ad33333333a");
        user.put("last", "Love3333333lace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(null, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(null, "Error adding document", e);
                    }
                });

        System.out.println("Hello");
    }



}