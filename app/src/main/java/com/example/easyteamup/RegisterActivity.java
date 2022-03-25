package com.example.easyteamup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.easyteamup.ui.create.CreateFragment;
import com.example.easyteamup.ui.events.EventsFragment;
import com.example.easyteamup.ui.events.EventsViewModel;

public class RegisterActivity  extends AppCompatActivity {
    Button registrationbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, WelcomeActivity.class));

            }
        });

        Button registerButton = (Button) findViewById(R.id.registration_button);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(RegisterActivity.this, ListViewActivity.class));

            }
        });

    }

}
