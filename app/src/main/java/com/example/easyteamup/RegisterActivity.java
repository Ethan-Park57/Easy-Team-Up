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

public class RegisterActivity  extends AppCompatActivity implements View.OnClickListener {
    Button registrationbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registrationbutton = findViewById(R.id.registration_button);
        registrationbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        if(v.getId()==R.id.registration_button){
//            getSupportFragmentManager().beginTransaction().replace(R.id.register,new EventsFragment()).commit();
//            R.id.register
//        }
    }

}
