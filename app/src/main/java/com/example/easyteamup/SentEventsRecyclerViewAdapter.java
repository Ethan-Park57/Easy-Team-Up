package com.example.easyteamup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SentEventsRecyclerViewAdapter extends RecyclerView.Adapter<SentEventsRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<Event> data;
    private Context context;

    public SentEventsRecyclerViewAdapter(Context ct, ArrayList<Event> data) {
        context = ct;
        this.data = data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventNameTextView, locationTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameTextView = itemView.findViewById(R.id.event_name_text_view);
            locationTextView = itemView.findViewById(R.id.location_text_view);
        }
    }
}