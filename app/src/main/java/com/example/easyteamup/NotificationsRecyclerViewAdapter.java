package com.example.easyteamup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.MyViewHolder> {
    private  Map<String, String> data;
    private Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Event> eventNames = new ArrayList<>();

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

        System.out.println("position :   " + position);


            String eventID = eventIDList.get(position);
            db.collection("events").document(eventID)
                    .get().addOnCompleteListener(task -> {
                String eventName = null;
                DocumentSnapshot document = task.getResult();
                eventNames.add(document.toObject(Event.class));
                eventName = eventNames.get(0).getEventName();
                System.out.println("event name is: " + eventName);
                holder.notificationTextView.setText(eventNames.get(position).eventName);
            });



//        int index = 0;
//        System.out.println("data size: " + data.size());
//        for(Object key : data.keySet()){
//            Object str = data.get(key);
//            holder.notificationTextView.setText(data.get(key));
//            ++index;
//        }
//            if(data.get("0") != null){
//                System.out.println("in 0 ");
//            }
//            if(data.get("1") != null){
//                holder.notificationTextView.setText(data.get("1"));
//                System.out.println("in 1 ");
//
//            }
//            if(data.get("2") != null){
//                holder.notificationTextView.setText(data.get("2"));
//            }
//        }


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
