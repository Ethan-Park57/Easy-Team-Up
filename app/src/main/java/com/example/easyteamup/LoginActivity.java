package com.example.easyteamup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailText = (EditText) findViewById(R.id.email_text);
        EditText passwordText = (EditText) findViewById(R.id.password_text);

        auth = FirebaseAuth.getInstance();

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));

            }
        });



        Button loginButton = (Button) findViewById(R.id.logging_button);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Please enter an email and password.", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(email, password);
                }
            }
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id = auth.getCurrentUser().getUid();
                    Toast.makeText(LoginActivity.this, "Login Succesful. Welcome Back!", Toast.LENGTH_SHORT).show();
                    db.collection("users").whereEqualTo("hostID", id).get().addOnCompleteListener(task2 -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task2.getResult()) {
                                currentUser = document.toObject(User.class);
                            }
                        }
                        else {
                            Log.d("USERS: ", "Error getting documents: ", task.getException());
                        }
                    });
                    startActivity(new Intent(LoginActivity.this, ListViewActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
