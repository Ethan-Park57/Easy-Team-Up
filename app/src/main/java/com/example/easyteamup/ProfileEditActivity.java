package com.example.easyteamup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileEditActivity extends AppCompatActivity {
    private static String newName;
    Button changeProfilePic;
    FirebaseUser user;
    StorageReference storageReference;
    ImageView profilePic;
    FirebaseAuth auth;
    Uri pictureURI;
    EditText changeNameInput;
    EditText changePasswordInput;
    private static Uri newURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

//        Button changeProfilePic;
//        changeProfilePic = findViewById(R.id.change_profile_pic);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        storageReference = FirebaseStorage.getInstance().getReference();
        changeProfilePic = findViewById(R.id.change_pic_button);
        profilePic = (ImageView) findViewById(R.id.image_view_edit);

        changeNameInput = (EditText) findViewById(R.id.edit_profile_change_name);
        changePasswordInput = (EditText) findViewById(R.id.edit_profile_change_password);

        changeProfilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1);
            }
        });

        StorageReference profileReference = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profilepic.jpg");
        profileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePic);
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(Uri.parse(pictureURI.toString()))
                        .build();

                System.out.println("display name!!!!!!!!!!!! " + profileUpdates.getDisplayName());


                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    profilePic.setImageURI(pictureURI);
                                    Log.d("tag2", "User profile update55d.");
                                }
                            }
                        });
                System.out.println("uri of pic is!!!!!!!!@@@@: " + user.getPhotoUrl());

            }
        });

        Button cancelChangesButton = (Button) findViewById(R.id.cancel_changes_button);
        cancelChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileEditActivity.this, ProfileActivity.class));

            }
        });

        Button saveChangesButton = (Button) findViewById(R.id.save_changes_button);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = changePasswordInput.getText().toString();
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("tag", "User password updated.");
                                }
                            }
                        });
                newName = changeNameInput.getText().toString();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newName)
                        .build();

                newURI = pictureURI;
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("tag", "User name display profile updated.");
                                }
                            }
                        });
                UserProfileChangeRequest updates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(Uri.parse(pictureURI.toString()))
                        .build();
                startActivity(new Intent(ProfileEditActivity.this, ProfileActivity.class));
            }
        });

//        changeProfilePic.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGallery, 1);
//            }
//        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                pictureURI = data.getData();
                profilePic.setImageURI(pictureURI);
                uploadtoFB(pictureURI);
            }
        }
    }
    private void uploadtoFB(Uri pictureURI){
        StorageReference fileReference = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profilepic.jpg");
        fileReference.putFile(pictureURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ProfileEditActivity.this, "Profile picture successfuly uploaded", Toast.LENGTH_SHORT).show();
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profilePic);

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileEditActivity.this, "Upload failed. Try a smaller file size.", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public static String getNewName(){
        return newName;
    }

    public static Uri getURI(){
        return newURI;
    }
}
