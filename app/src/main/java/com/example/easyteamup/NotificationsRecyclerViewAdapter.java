package com.example.easyteamup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.MyViewHolder> {
    private  String eventName;
    private  String hostName;
    private  Map<String, String> data;
    private Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Event> eventNames = new ArrayList<>();
    boolean done = false;

    public NotificationsRecyclerViewAdapter(Context context,  Map<String, String> data){
        this.context = context;
        this.data = data;

    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notification_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        System.out.println("notifc onBindViewHolder: " + data);
        List<String> eventIDList = new ArrayList<String>(data.values());
        for(int i = 0; i < eventIDList.size(); i++){
            System.out.println("i: " + i + ": " +  eventIDList.get(i));
        }
        ArrayList<String> notifTypeAndNames = new ArrayList<>();

        for (Map.Entry<String, String> entry : data.entrySet()) {
            Integer key = Integer.parseInt(entry.getKey());
            Object value = entry.getValue();
            String KV = null;
            if(key == 645){
                KV = "CASE4" + value.toString();
            }else if(key < 0){
                KV = "CASE1" + value.toString();
            }else if(key % 2 == 0){
                KV = "CASE2" + value.toString();
            }else{
                KV = "CASE3" + value.toString();
            }
            notifTypeAndNames.add(KV);
        }

        System.out.println("position :   " + position);
        String eventID = eventIDList.get(position);
        System.out.println("eventId: " + eventID);
        db.collection("events").document(eventID)
                .get().addOnCompleteListener(task -> {
            System.out.println("I'm here..");
            DocumentSnapshot document = task.getResult();
            eventNames.add(document.toObject(Event.class));
                    if(task.isSuccessful()){
                        if(notifTypeAndNames.get(position).substring(0, 5).equals("CASE1")){
                            holder.notificationTextView.setText("case 1  " + eventNames.get(position).eventName);
                        }else if(notifTypeAndNames.get(position).substring(0, 5).equals("CASE2")){
                            holder.notificationTextView.setText("case 2 " + eventNames.get(position).eventName);
                        }else if(notifTypeAndNames.get(position).substring(0, 5).equals("CASE3")){
                            holder.notificationTextView.setText("case 3 " + eventNames.get(position).eventName);
                        }else{
                            holder.notificationTextView.setText("deadline test " + eventNames.get(position).eventName);
                        }
                        System.out.println("didnt finish");
                    }

        });
        //String eventName = null;
        //eventName = eventNames.get(position).eventName;
       // System.out.println("event name is: " + eventName);
       // System.out.println("eventNames size  is: " + eventNames.size());
        //System.out.println("eventNames item at 2 is: " + eventNames.get(2));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView notificationTextView;
        ConstraintLayout notificationsLayout;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            notificationTextView = itemView.findViewById(R.id.notification_text_view);
            notificationsLayout = itemView.findViewById(R.id.notifications_layout);
        }
    }



}
