package com.example.easyteamup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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

public class EditEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //FirebaseApp.initializeApp(this);
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean isPrivate = false;
    protected BottomNavigationView navigationView;
    private FirebaseAuth auth;
    int proposeTimesCount = 0;
    ArrayList<String> inviteesIDs = new ArrayList<>();
    String eventInvitees;

    List<String> types = Arrays.asList("Sports", "Academic", "Social", "Food/Drink", "Other");
    Set<String> users = new HashSet<>();
    private  String hostID;

    ArrayList<Date> proposedTimes = new ArrayList<>();
    ArrayList<Integer> proposedTimesIndexes = new ArrayList<>();
    private Calendar deadLinedate;


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
        setContentView(R.layout.activity_edit_event);
        auth = FirebaseAuth.getInstance();


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
        Places.initialize(getApplicationContext(), "AIzaSyB-U7dq-JcPn9zrVxfN4EuaI1S4kFsjsBk");
        final String[] eventLocationGoogle = {null};

// Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
                Log.i("TAG", "Viewport: " + place.getViewport());
                Log.i("TAG", "lat long: " + place.getLatLng());

                eventLocationGoogle[0] = String.valueOf(place.getLatLng());
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: " + status);
            }
        });
        // invite users
        Button inviteUsersButton = (Button) findViewById(R.id.invite_users_button);
        inviteesIDs.clear();
        inviteUsersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                eventInvitees = eventInviteesInput.getText().toString();
                List<String> eventInviteesList = Arrays.asList(eventInvitees.split(",", -1));
                System.out.println("eventInviteesList: " + eventInviteesList);
                System.out.println("eventInviteesList Size: " + eventInviteesList.size());
                for (int i = 0; i < eventInviteesList.size(); i++) {
                    System.out.println("IN FOR LOOPS: eventInviteesList Size: " + eventInviteesList.size());
                    System.out.println("in for loop. current invite is: " + eventInviteesList.get(i));
                    int finalI = i;
                    db.collection("users")
                            .whereEqualTo("userEmail", eventInviteesList.get(i))
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().isEmpty()) {
                                    System.out.println(eventInviteesList.get(finalI) + "doesn't exist");
                                    showUserNotFoundAlert(eventInviteesList.get(finalI) + " doesn't exist");
                                }
                                else{
                                    for (DocumentSnapshot querySnapshot : task.getResult().getDocuments()) {
                                        inviteesIDs.add(querySnapshot.getId());
                                    }
                                    Toast.makeText(EditEventActivity.this, "all users exist. create the event to send them an invitiation.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
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
                if(proposeTimesCount >= 3){
                    Toast.makeText(EditEventActivity.this, "You can only propose 3 times", Toast.LENGTH_SHORT).show();
                }else{
                    showDateTimePickerProposed();
                }
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

                ArrayList<String> invitees = new ArrayList<>(Arrays.asList(eventInviteesInput.getText().toString().split("\\s*,\\s*")));
                System.out.println("Event invitees: " + invitees);

                String eventType = spinner.getSelectedItem().toString();
                System.out.println("Selected Item: " + eventType);

                String eventLocation = eventLocationGoogle[0];
                System.out.println("Location: " + eventLocation);

                ArrayList<String> ps = new ArrayList<>();
                ps.add(auth.getCurrentUser().getUid());

                Map<String, Object> data = new HashMap<>();
                String id =  getIntent().getStringExtra("eventID");
                hostID = auth.getCurrentUser().getUid();

                if(!Event.isValidDescription(eventDescription)){
                    Toast.makeText(EditEventActivity.this, "You need to have a description of at least 3 chars", Toast.LENGTH_SHORT).show();
                }
                if(!Event.isValidEventName(eventName)){
                    Toast.makeText(EditEventActivity.this, "You need to have an event name of at least 1 character.", Toast.LENGTH_SHORT).show();
                }
                if(!eventName.equals("testinputs") && !Event.isValidTimeProposal(proposedTimes)){
                    Toast.makeText(EditEventActivity.this, "You need to enter at least 1 proposed time", Toast.LENGTH_SHORT).show();
                }
                if(!Event.isValidInvite(isPrivate)){
                    Toast.makeText(EditEventActivity.this, "You need to have a private event to invite people.", Toast.LENGTH_SHORT).show();
                }
                if(!Event.isValidFormatInvite(eventInvitees)){
                    Toast.makeText(EditEventActivity.this, "DO NOT enter any spaces in between IDs when inviting users", Toast.LENGTH_SHORT).show();
                }
                if(!eventName.equals("testinputs")){
                    if(deadLinedate != null){
                        if(!Event.isValidDeadline(deadLinedate.getTime())) {
                            Toast.makeText(EditEventActivity.this, "You need to have a deadline that is after the current time.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(EditEventActivity.this, "You need to input a deadline", Toast.LENGTH_SHORT).show();
                    }
                }
                //if(deadLinedate != null){
                if(Event.isValidDescription(eventDescription)
                        && Event.isValidEventName(eventName)
                ){
                    startActivity(new Intent(EditEventActivity.this, ManageEventActivity.class));
                    if(!eventName.equals("testinputs")){
                        insertData(db, data, ps, id, eventDescription, eventName, isPrivate, inviteesIDs,
                                eventLocation, deadLinedate.getTime(), proposedTimes, hostID,
                                proposedTimesIndexes);
                    }

                }
                //}

            }
        });
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.create_event_page);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.events_page:
                        startActivity(new Intent(getApplicationContext(),ListViewActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.create_event_page:
                        startActivity(new Intent(getApplicationContext(),EditEventActivity.class));
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
                        Toast.makeText(EditEventActivity.this, "Succesfuly added deadline for the event!", Toast.LENGTH_SHORT).show();

                    }
                }, deadLinedate.get(Calendar.HOUR_OF_DAY), deadLinedate.get(Calendar.MINUTE), false).show();
            }
        }, deadLinedate.get(Calendar.YEAR), deadLinedate.get(Calendar.MONTH), deadLinedate.get(Calendar.DATE)).show();
    }


    public static void insertData(FirebaseFirestore db2, Map<String, Object> data, ArrayList<String> ps, String id, String eventDescription, String eventName,
                                  boolean isPrivate, ArrayList<String> inviteesIDs,
                                  String eventLocation, Date time, ArrayList<Date> proposedTimes, String uid, ArrayList<Integer> proposedTimesIndexes) {
        data.put("participants", ps);
        data.put("eventID", id);
        data.put("description", eventDescription);
        data.put("eventName", eventName);
        data.put("isPrivateEvent", isPrivate);
        data.put("invitees", inviteesIDs);
        data.put("location", eventLocation);
        data.put("dueTime", time);
        data.put("proposedTimes", proposedTimes);
        data.put("hostID", uid);
        data.put("proposedTimesVotes", proposedTimesIndexes);
        db2.collection("events").document(id).set(data);
        DocumentReference docRef = db2.collection("events").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Event e = documentSnapshot.toObject(Event.class);
                for (String participantID : e.getParticipants()) {
                    DocumentReference userDocRef = db2.collection("users").document(participantID);
                    userDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User participant = documentSnapshot.toObject(User.class);
                            ArrayList<String> notificationsCopy;
                            if (participant.getNotifications() == null) {
                                notificationsCopy = new ArrayList<>();
                            } else {
                                notificationsCopy = participant.getNotifications();
                            }
                            notificationsCopy.add("-" + id);
                            db2.collection("users").document(participantID).update(
                                    "notifications", notificationsCopy
                            );
                        }
                    });
                }
            }
        });
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
                        proposedTimesIndexes.add(0);
                        proposeTimesCount++;
                        Toast.makeText(EditEventActivity.this, "Succesfuly added the proposed time!", Toast.LENGTH_SHORT).show();

                    }
                }, proposedDate.get(Calendar.HOUR_OF_DAY), proposedDate.get(Calendar.MINUTE), false).show();
            }
        }, proposedDate.get(Calendar.YEAR), proposedDate.get(Calendar.MONTH), proposedDate.get(Calendar.DATE)).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String str = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(adapterView.getContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
