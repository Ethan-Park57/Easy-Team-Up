package com.example.easyteamup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean isPrivate = false;

    List<String> types = Arrays.asList("Sports", "Academic", "Social", "Food/Drink", "Other");
    Set<String> users = new HashSet<>();

    ArrayList<Date> proposedTimes = new ArrayList<>();

    public void showUserNotFoundAlert(String str){
        System.out.println("made it in showAlertNoUserFound");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning").setMessage(str);
        builder.setPositiveButton("Okay",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di,int i) {

            }
        });
        builder.create().show();
    }

    public void showMustBePrivateAlert(String str){
        System.out.println("made it in showAlertPrivate");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning").setMessage(str);
        builder.setPositiveButton("Okay",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface di,int i) {

            }
        });
        builder.create().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // Get all Users from database for search function
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                users.add(document.getId());
                                Log.d("USERS ", document.getId() + " => " + document.getData());
                                System.out.println("USERS PRINT: " + users);
                            }
                        } else {
                            Log.d("USERS: ", "Error getting documents: ", task.getException());
                        }
                    }
                });




        EditText eventNameInput = (EditText) findViewById(R.id.event_name);
        EditText eventDescriptionInput = (EditText) findViewById(R.id.event_description);
        Button createEventButton = (Button) findViewById(R.id.create_event);
        CheckBox isPrivateCheckBox = (CheckBox) findViewById(R.id.is_private_check_box);
        EditText eventInviteesInput = (EditText) findViewById(R.id.invite_list_text);
        EditText eventLocationInput = (EditText) findViewById(R.id.event_location_text);
        EditText eventHostInput = (EditText) findViewById(R.id.host_id_text);

        // invite users
        Button inviteUsersButton = (Button) findViewById(R.id.invite_users_button);
        inviteUsersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String eventInvitees = eventInviteesInput.getText().toString();
                List<String> eventInviteesList = Arrays.asList(eventInvitees.split(",", -1));
                System.out.println("eventInviteesList: " + eventInviteesList);
                System.out.println("eventInviteesList Size: " + eventInviteesList.size());
                for(int i = 0; i < eventInviteesList.size(); i++){
                    System.out.println("IN FOR LOOPS: eventInviteesList Size: " + eventInviteesList.size());
                    System.out.println("in for loop. current invite is: " + eventInviteesList.get(i));
                    if(!users.contains(eventInviteesList.get(i))){
                        System.out.println(eventInviteesList.get(i) + "doesn't exist");
                        showUserNotFoundAlert(eventInviteesList.get(i) + " doesn't exist");
                    }
                    if(!isPrivate){
                        showUserNotFoundAlert("Your event must be prviate to invite users.");
                    }
                }
            }
        });

        // Choosing event type
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Button proposedTimesButton = (Button) findViewById(R.id.propose_times_button);
        proposedTimesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateTimePickerProposed();
            }
        });

        Button chooseDeadlineButton = (Button) findViewById(R.id.date_time_set);
        chooseDeadlineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateTimePicker();

            }
        });
        isPrivateCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isPrivate = true;
            }
        });

        createEventButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String eventName = eventNameInput.getText().toString();
                System.out.println("Event name: " + eventName);

                String eventDescription = eventDescriptionInput.getText().toString();
                System.out.println("Event description: " + eventDescription);

                String eventInvitees = eventInviteesInput.getText().toString();
                System.out.println("Event invitees: " + eventInvitees);

                String eventType = spinner.getSelectedItem().toString();
                System.out.println("Selected Item: " + eventInvitees);

                String eventLocation = eventLocationInput.getText().toString();
                System.out.println("Location: " + eventLocation);

                String eventHostID = eventHostInput.getText().toString();
                System.out.println("evenHostID: " + eventHostID);

                Map<String, Object> data = new HashMap<>();
                data.put("eventDescription", eventDescription);
                data.put("eventName", eventName);
                String id = db.collection("events").document().getId();
                data.put("EventID", id);
                data.put("isPrivate", isPrivate);
                data.put("eventInvitees", eventInvitees);
                data.put("eventType", eventType);
                data.put("location", eventLocation);
                data.put("dueTime", deadLinedate.getTime());
                data.put("proposedTimes", proposedTimes);
                data.put("hostID", eventHostID);


                db.collection("events").document(id).set(data);

                startActivity(new Intent(CreateEventActivity.this, ManageEventActivity.class));
            }
        });




    }
    Calendar deadLinedate;
    public void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        deadLinedate = Calendar.getInstance();
        Context context = this;
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                deadLinedate.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        deadLinedate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        deadLinedate.set(Calendar.MINUTE, minute);
                        Log.v("tag", "Time: " + deadLinedate.getTime());
                    }
                }, deadLinedate.get(Calendar.HOUR_OF_DAY), deadLinedate.get(Calendar.MINUTE), false).show();
            }
        }, deadLinedate.get(Calendar.YEAR), deadLinedate.get(Calendar.MONTH), deadLinedate.get(Calendar.DATE)).show();
    }

    Calendar proposedTime;
    public void showDateTimePickerProposed() {
        final Calendar currentDate = Calendar.getInstance();
        Calendar proposedDate;
        proposedDate = Calendar.getInstance();
        Context context = this;
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                proposedDate.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        proposedDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        proposedDate.set(Calendar.MINUTE, minute);
                        Log.v("tag", "Time: " + proposedDate.getTime());
                        proposedTime = proposedDate;
                        proposedTimes.add(proposedTime.getTime());
                    }
                }, proposedDate.get(Calendar.HOUR_OF_DAY), proposedDate.get(Calendar.MINUTE), false).show();
            }
        }, proposedDate.get(Calendar.YEAR), proposedDate.get(Calendar.MONTH), proposedDate.get(Calendar.DATE)).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String str = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


