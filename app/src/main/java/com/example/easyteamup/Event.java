package com.example.easyteamup;

import android.content.Context;

import com.google.firebase.Timestamp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class Event {

    String eventID;
    String eventName;
    String hostID;
    Timestamp confirmedTime;
    String description;
    boolean isTimeSet;
    public ArrayList<Date> getProposedTimes() {
        return proposedTimes;
    }

    public void setProposedTimes(ArrayList<Date> proposedTimes) {
        this.proposedTimes = proposedTimes;
    }

    ArrayList<Date> proposedTimes;
    ArrayList<Long> proposedTimesVotes;

    public boolean isPrivateEvent() {
        return isPrivateEvent;
    }

    public void setPrivateEvent(boolean privateEvent) {
        isPrivateEvent = privateEvent;
    }

    boolean isPrivateEvent;
    Timestamp dueTime;
    String location;
    ArrayList<String> participants;
    ArrayList<String> invitees;

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public Timestamp getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(Timestamp confirmedTime) {
        this.confirmedTime = confirmedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTimeSet() {
        return isTimeSet;
    }

    public void setTimeSet(boolean timeSet) {
        isTimeSet = timeSet;
    }

    public ArrayList<Long> getProposedTimesVotes() { return proposedTimesVotes; }
    public void setProposedTimesVotes(ArrayList<Long> aList) {
        this.proposedTimesVotes = aList;
    }

    public Timestamp getDueTime() {
        return dueTime;
    }

    public void setDueTime(Timestamp dueTime) {
        this.dueTime = dueTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public ArrayList<String> getInvitees() {
        return invitees;
    }

    public void setInvitees(ArrayList<String> invitees) {
        this.invitees = invitees;
    }

    public static boolean isValidDescription(String desc){
        int count = 0;
        for(int i = 0; i < desc.length(); i++) {
            count++;
        }
        if(count < 3){
            return false;
        }else{
            return true;
        }
    }

    public static boolean isValidDeadline(Date time1) {
        Calendar current = Calendar.getInstance();
        System.out.println("current time: " + current.getTime());
        if(current.getTime().after(time1)){
            return false;
        }else{
            return true;
        }
    }

    public static boolean isValidEventName(String s) {
        if(s == null || s.length() < 1){
            return false;
        }else{
            return true;
        }
    }

    public static boolean isValidTimeProposal(ArrayList<Date> proposedTimes) {
        if(proposedTimes == null || proposedTimes.size() < 1){
            return false;
        }else{
            return true;
        }
    }

    public static boolean isValidInvite(boolean b) {
        if(b){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isValidFormatInvite(String s) {
        if(s==null) return false;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == ' '){
                return false;
            }
        }
        return true;
    }


}