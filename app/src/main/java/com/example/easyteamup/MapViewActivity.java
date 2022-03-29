package com.example.easyteamup;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapViewActivity extends AppCompatActivity {
    protected BottomNavigationView navigationView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        Button maptolist = (Button) findViewById(R.id.maptolist);
        maptolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapViewActivity.this, ListViewActivity.class));

            }
        });

        Button maptomap = (Button) findViewById(R.id.maptomap);
        maptomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapViewActivity.this, MapViewActivity.class));

            }
        });

//        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        navigationView.setSelectedItemId(R.id.events_page);
//        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.events_page:
//                        startActivity(new Intent(getApplicationContext(), MapViewActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.create_event_page:
//                        startActivity(new Intent(getApplicationContext(), CreateEventActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.manage_event_page:
//                        startActivity(new Intent(getApplicationContext(), ManageEventActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.profile_page:
//                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                }
//                return false;
//            }
//        });
        // Get a handle to the fragment and register the callback.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

//        db.collection("events").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//
//                if (e !=null)
//                {
//                    System.out.println("Error");
//                }
//                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges())
//                {
//                    System.out.println(documentChange.getDocument().getData());
//                }
//            }
//        });

//        ArrayList<String> name_array = new ArrayList<>();
//        ArrayList<String> address_array = new ArrayList<>();
//        db.collection("events")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                String eventName = document.getData().get("eventName").toString();
//                                System.out.println(eventName);
//                                name_array.add(eventName);
//                                String eventLocation =  document.getData().get("location").toString();
//                                System.out.println(eventLocation);
//                                address_array.add(eventLocation);
//                                //System.out.println(document.getId() + " => " + document.getData());
//                            }
////                            //convert to coordinates
////                            ArrayList<LatLng> latLngArrayList = new ArrayList<>();
////                            for(int i=0;i<address_array.size();i++){
////                                LatLng laln = getLocationFromAddress(getApplicationContext(),address_array.get(i));
////                                latLngArrayList.add(laln);
////                            }
////                            //add the markers
////                            for(int i=0;i<name_array.size();i++)
////                            {
////                                googleMap.addMarker(new MarkerOptions().position(latLngArrayList.get(i)).title(name_array.get(i)));
////                            }
//                        } else {
//                            System.out.println("Error");
//                        }
//                    }
//                });
    }
}
    // Get a handle to the GoogleMap object and display marker.
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        LatLng location = new LatLng(34.0349571239793, -118.27581622242603);
//        CameraPosition target = CameraPosition.builder().target(location).zoom(15).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 5000, null);
//    }
//
//    public LatLng getLocationFromAddress(Context context, String strAddress) {
//
//        Geocoder coder = new Geocoder(context);
//        List<Address> address;
//        LatLng p1 = null;
//
//        try {
//            // May throw an IOException
//            address = coder.getFromLocationName(strAddress, 5);
//            if (address == null) {
//                return null;
//            }
//
//            Address location = address.get(0);
//            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
//
//        } catch (IOException ex) {
//
//            ex.printStackTrace();
//        }
//
//        return p1;
//    }
//
//}