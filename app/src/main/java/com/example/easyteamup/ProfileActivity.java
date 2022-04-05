package com.example.easyteamup;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    protected BottomNavigationView navigationView;
    FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //FirebaseUser currentUser;
    FirebaseUser user;
    StorageReference storageReference;
    ImageView profilePic;
    Button changeProfilePic;
    Uri pictureURI;
    String newName;
    Uri newURI;
    boolean picChanged;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button editProfileButton = (Button) findViewById(R.id.profile_edit_button);
        auth = FirebaseAuth.getInstance();

        //currentUser = auth.getCurrentUser();
        user = FirebaseAuth.getInstance().getCurrentUser();

        TextView emailText = (TextView) findViewById(R.id.profile_email);
        TextView nameText = (TextView) findViewById(R.id.profile_name);
        profilePic = (ImageView) findViewById(R.id.imageView);
        //emailText.setText(currentUser.());

        newName = ProfileEditActivity.getNewName();
        newURI = user.getPhotoUrl();
        picChanged = ProfileEditActivity.getIsChanged();
        System.out.println("newURI: " + newURI);
        if(picChanged && newName != null){
            Picasso.get().load(user.getPhotoUrl()).into(profilePic);
            nameText.setText(user.getDisplayName());
            emailText.setText(user.getEmail());
            System.out.println("if picture is changed and name is not changed: " + user.getDisplayName());
            System.out.println("if picture is changed and name is not changed" + user.getPhotoUrl());
        } else if(newName != null && !picChanged){
            Picasso.get().load(user.getPhotoUrl()).into(profilePic);
            nameText.setText(user.getDisplayName());
            emailText.setText(user.getEmail());
            System.out.println("if picture is not changed but name is changed: " + user.getDisplayName());
            System.out.println("if picture is not changed but name is changed: " + user.getPhotoUrl());


        }else if(newName == null && !picChanged){
            nameText.setText(user.getDisplayName());
            emailText.setText(user.getEmail());
            Picasso.get().load(user.getPhotoUrl()).into(profilePic);
            System.out.println("if nothing is changed" + user.getDisplayName());
            System.out.println("if nothing is changed" + user.getPhotoUrl());
        }else{
            nameText.setText(user.getDisplayName());
            emailText.setText(user.getEmail());
            Picasso.get().load(user.getPhotoUrl()).into(profilePic);
        }

        System.out.println("nameText:  " + user.getDisplayName());
        System.out.println("from global:  " + ProfileEditActivity.getNewName());
        System.out.println("from global URI :  " + ProfileEditActivity.getURI());


        //Uri defaultURI = Uri.parse("https://firebasestorage.googleapis.com/v0/b/easyteamup-3c633.appspot.com/o/users%2F25Xn74CpUScPvJonjaD6MDUndhp2%2FblankPic.jpg?alt=media&token=b30ce3a1-452b-48e8-bf64-e1f072fbea85");
        //profilePic.setImageURI(defaultURI);
//        Uri photoUrl = user.getPhotoUrl();
//        profilePic.setImageURI(photoUrl);
        storageReference = FirebaseStorage.getInstance().getReference();
        //changeProfilePic = findViewById(R.id.change_profile_picture);
//        changeProfilePic.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGallery, 1);
//            }
//        });



//        auth = FirebaseAuth.getInstance();
//        db.collection("users").whereEqualTo("hostID", id)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("USERS ", document.getId() + " => " + document.getData());
//                                System.out.println("document data: " + document.getData());
//                            }
//                        } else {
//                            Log.d("USERS: ", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

//        StorageReference profileReference = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profilepic.jpg");
//        profileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(profilePic);
//                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                        .setPhotoUri(Uri.parse(pictureURI.toString()))
//                        .build();
//
//                System.out.println("display name!!!!!!!!!!!! " + profileUpdates.getDisplayName());
//
//
//                user.updateProfile(profileUpdates)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    profilePic.setImageURI(pictureURI);
//                                    Log.d("tag2", "User profile update55d.");
//                                }
//                            }
//                        });
//                System.out.println("uri of pic is!!!!!!!!@@@@: " + user.getPhotoUrl());
//
//            }
//        });



        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileEditActivity.class));
                System.out.println("uri of pic is!!!!!!!!@@@@: " + user.getPhotoUrl());

            }
        });

        Button logoutButton = (Button) findViewById(R.id.profile_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, WelcomeActivity.class));

            }
        });



        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.profile_page);
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
                        startActivity(new Intent(getApplicationContext(),ManageEventActivity.class));
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
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1){
//            if(resultCode == Activity.RESULT_OK){
//                pictureURI = data.getData();
//                profilePic.setImageURI(pictureURI);
//                uploadtoFB(pictureURI);
//                System.out.println("This is called in profile activity");
//            }
//        }
//    }
//
//    private void uploadtoFB(Uri pictureURI){
//        StorageReference fileReference = storageReference.child(user.getPhotoUrl().toString());
//        fileReference.putFile(pictureURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        Picasso.get().load(uri).into(profilePic);
//
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//            }
//        });
//
//
//    }

}