package com.example.easyteamup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.easyteamup.ui.create.CreateFragment;
import com.example.easyteamup.ui.events.EventsFragment;
import com.example.easyteamup.ui.events.EventsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity  extends AppCompatActivity implements View.OnClickListener {
    Button registrationbutton;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText emailText = (EditText) findViewById(R.id.register_email);
        EditText passwordText = (EditText) findViewById(R.id.register_password);
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
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Empty username or password.", Toast.LENGTH_SHORT).show();
                }else if(password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must be at least 3 letters.", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(email, password);
                }
            }
        });

    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Succesfuly registered user. Welcome!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, ListViewActivity.class));
                }else{
                    Toast.makeText(RegisterActivity.this, "Failed to registered user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
