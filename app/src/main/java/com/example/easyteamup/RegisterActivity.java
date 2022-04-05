package com.example.easyteamup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.easyteamup.ui.create.CreateFragment;
import com.example.easyteamup.ui.events.EventsFragment;
import com.example.easyteamup.ui.events.EventsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity  extends AppCompatActivity implements View.OnClickListener {
    Button registrationbutton;
    FirebaseAuth auth;

    FirebaseFirestore firestore;
    FirebaseUser user;
    ImageView profilePic;
    Button changeProfilePic;
    StorageReference storageReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText emailText;
    EditText passwordText;
    EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        profilePic = findViewById(R.id.image_view);
//        changeProfilePic = findViewById(R.id.change_profile_pic);
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

//        StorageReference profileReference = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profilepic.jpg");
//        profileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(profilePic);
//            }
//        });



//        changeProfilePic.setOnClickListener(new View.OnClickListener(){
//          @Override
//          public void onClick(View view){
//              Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//              startActivityForResult(openGallery, 1);
//          }
//        });

         emailText = (EditText) findViewById(R.id.register_email);
         passwordText = (EditText) findViewById(R.id.register_password);
         nameText = (EditText) findViewById(R.id.register_name);
         auth = FirebaseAuth.getInstance();


        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));
            }
        });

        Button registration_button = (Button) findViewById(R.id.registration_button);
        registration_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                System.out.println("email: " + email);
                System.out.println("password: " + password);
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(RegisterActivity.this, "Must use correct form (xxx@xxx.xxx) and unused email. Try again", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Empty username or password.", Toast.LENGTH_SHORT).show();
                }else if(password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 letters.", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(email, password);
                }
            }
        });

    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1){
//            if(resultCode == Activity.RESULT_OK){
//                Uri pictureURI = data.getData();
//                profilePic.setImageURI(pictureURI);
//                uploadtoFB(pictureURI);
//            }
//        }
//    }
//    private void uploadtoFB(Uri pictureURI){
//        StorageReference fileReference = storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profilepic.jpg");
//        fileReference.putFile(pictureURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(RegisterActivity.this, "Profile picture successfuly uploaded", Toast.LENGTH_SHORT).show();
//                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                       Picasso.get().load(uri).into(profilePic);
//
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(RegisterActivity.this, "Upload failed. Try a smaller file size.", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String  userName = nameText.getText().toString();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(userName)
                            .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/easyteamup-3c633.appspot.com/o/users%2F25Xn74CpUScPvJonjaD6MDUndhp2%2FblankPic.jpg?alt=media&token=b30ce3a1-452b-48e8-bf64-e1f072fbea85"))
                            .build();
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("tag", "User profile updated.");
                                    }
                                }
                            });


                    String  userEmail = emailText.getText().toString();
                    String  userPassword = passwordText.getText().toString();

                    System.out.println("useremail: " + userEmail);
                    System.out.println("userName: " + userName);
                    System.out.println("userPassword: " + userPassword);


                    Map<String, Object> data = new HashMap<>();
                    data.put("userEmail", userEmail);
                    data.put("userName", userName);
                    data.put("userPassword", userPassword);
                    data.put("hostID", auth.getCurrentUser().getUid());
                    System.out.println("USer id: " + auth.getCurrentUser().getUid());
                    String id = auth.getCurrentUser().getUid();

                    db.collection("users").document(id).set(data);
                    Toast.makeText(RegisterActivity.this, "Succesfuly registered user. Welcome!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, ListViewActivity.class));
                }else{
                    Toast.makeText(RegisterActivity.this, "Failed to registered user. Try an email that has not been used.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
