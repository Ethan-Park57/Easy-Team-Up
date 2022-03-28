package com.example.easyteamup;

import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Event {

    int eventID;
    String eventName;
    int hostID;
    DateTime confirmedTime;
    String description;
    boolean isTimeSet;
//    Map<DateTime, Integer> proposedTimes;
//    ArrayList<DateTime> proposedTimesList;
    boolean isPrivateEvent;
    DateTime dueTime;
    String location;
    Set<Integer> participants;
    Set<Integer> invitees;

//    public ArrayList<DateTime> getProposedTimesList() { return proposedTimesList; }
//    public void setProposedTimesList(ArrayList<DateTime> aList) {
//        this.proposedTimesList = aList;
//    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getHostID() {
        return hostID;
    }

    public void setHostID(int hostID) {
        this.hostID = hostID;
    }

    public DateTime getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(DateTime confirmedTime) {
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

//    public Map<DateTime, Integer> getProposedTimes() {
//        return proposedTimes;
//    }
//
//    public void setProposedTimes(Map<DateTime, Integer> proposedTimes) {
//        this.proposedTimes = proposedTimes;
//    }

    public boolean isPrivateEvent() {
        return isPrivateEvent;
    }

    public void setPrivateEvent(boolean privateEvent) {
        isPrivateEvent = privateEvent;
    }

    public DateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(DateTime dueTime) {
        this.dueTime = dueTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Integer> participants) {
        this.participants = participants;
    }

    public Set<Integer> getInvitees() {
        return invitees;
    }

    public void setInvitees(Set<Integer> invitees) {
        this.invitees = invitees;
    }
}
