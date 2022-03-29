package com.example.easyteamup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewRecyclerViewAdapter extends RecyclerView.Adapter<ListViewRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<Event> data;
    private Context context;

    public ListViewRecyclerViewAdapter(Context ct, ArrayList<Event> data) {
        context = ct;
        this.data = data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_view_recycler_view_adapter);
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.eventNameTextView.setText(data.get(position).getEventName());
        holder.locationTextView.setText(data.get(position).getLocation());

        holder.list_events_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetailsActivity.class);
                intent.putExtra("id", data.get(position).getEventID());
                intent.putExtra("name", data.get(position).getEventName());
                intent.putExtra("location", data.get(position).getLocation());
                intent.putExtra("description", data.get(position).getDescription());
                intent.putExtra("dueTime", data.get(position).getDueTime().toDate());
                System.out.println("Due time is " + data.get(position).getDueTime().toDate());
                System.out.println("proposed time size is " + data.get(position).getProposedTimes().size());

                intent.putExtra("proposed1", data.get(position).getProposedTimes().get(0).toString());
                if (data.get(position).getProposedTimes().size() > 1) {
                    System.out.println(data.get(position).getProposedTimes().get(1).toString());
                    intent.putExtra("proposed2", data.get(position).getProposedTimes().get(1).toString());
                }
                if (data.get(position).getProposedTimes().size() > 2) {
                    System.out.println(data.get(position).getProposedTimes().get(2).toString());
                    intent.putExtra("proposed3", data.get(position).getProposedTimes().get(2).toString());
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventNameTextView, locationTextView;
        ConstraintLayout list_events_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.event_name_text_view);
            locationTextView = itemView.findViewById(R.id.location_text_view);
            list_events_layout = itemView.findViewById(R.id.events_layout);
        }
    }
}