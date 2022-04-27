package com.example.easyteamup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.easyteamup.databinding.ActivityMapViewBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MapViewActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapViewBinding binding;

    FirebaseFirestore db =  FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
//                //parse firebase data
//        DocumentReference docRef = db.collection("events").document("MuIk7s0lsJQT3G5opAUk");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d("tag", "Location data: " + document.getString("location"));
//                        //parse each field into a string and convert into a integer to put into the google coordinates
//                        String location = document.getString("location");
//                        Double lat = Double.parseDouble(location.split(":")[1].split(",")[0].replace(" (",""));
//                        Log.d("tag","Latitude Coordinates: "+ lat);
//                        Double lng = Double.parseDouble(location.split(":")[1].split(",")[1].replace(" ","").replace(")",""));
//                        Log.d("tag","Longitude Coordinates: "+ lng);
//
//                        //make a LatLng Object and push it to the array list
//                        //LatLng newLatLng = new LatLng(lat,lng);
//                        //LatLngArray.add(newLatLng);
//
//                        //Log.d("tag","array: "+LatLngArray.get(0));
//
//                        mMap = googleMap;
//
//                        // Add a marker in Sydney and move the camera
//                        LatLng sydney = new LatLng(lat, lng);
//                        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//                    } else {
//                        Log.d("tag", "No such document");
//                    }
//                } else {
//                    Log.d("tag", "get failed with ", task.getException());
//                }
//            }
//        });


        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(!document.getBoolean("isPrivateEvent")){
                                    //Log.d("tag", document.getId() + " => " + document.getData());
                                    Log.d("tag", "Location name: " + document.getString("eventName"));
                                    String eventName =  document.getString("eventName");
                                    Log.d("tag", "Location data: " + document.getString("location"));
                                    //parse each field into a string and convert into a integer to put into the google coordinates
                                    String location = document.getString("location");
                                    Double lat = Double.parseDouble(location.split(":")[1].split(",")[0].replace(" (",""));
                                    Log.d("tag","Latitude Coordinates: "+ lat);
                                    Double lng = Double.parseDouble(location.split(":")[1].split(",")[1].replace(" ","").replace(")",""));
                                    Log.d("tag","Longitude Coordinates: "+ lng);


                                    mMap = googleMap;

                                    // Add a marker in Sydney and move the camera
                                    LatLng sydney = new LatLng(lat, lng);
                                    mMap.addMarker(new MarkerOptions().position(sydney).title(eventName));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                }
                            }
                        } else {
                            Log.d("tag", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
