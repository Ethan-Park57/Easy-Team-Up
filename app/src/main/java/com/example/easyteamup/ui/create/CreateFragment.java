package com.example.easyteamup.ui.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.easyteamup.R;
import com.example.easyteamup.databinding.FragmentCreateBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FragmentCreateBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreateViewModel createViewModel =
                new ViewModelProvider(this).get(CreateViewModel.class);

        binding = FragmentCreateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        EditText eventNameInput = (EditText) binding.eventName;
        EditText eventDescriptionInput = (EditText) binding.eventDescription;


        Button submitButton = (Button) binding.createEvent;
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

                String eventName = eventNameInput.getText().toString();
                System.out.println("Event name: " + eventName);
                //String eventType = eventTypeInput.getText().toString();
                //System.out.println("Event type: " + eventType);
                String eventDescription = eventDescriptionInput.getText().toString();
                System.out.println("Event description: " + eventDescription);

                Map<String, Object> data = new HashMap<>();
                //data.put("eventType", eventType);
                data.put("eventDescription", eventDescription);
                data.put("eventName", eventName);
                String id = db.collection("events").document().getId();
                data.put("EventID", id);
                db.collection("events").document(id).set(data);
            }
        });

//        final TextView textView = binding.textCreate;
//        createViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}