package com.example.easyteamup;

import java.util.ArrayList;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String hostID;
    private String userEmail;
    private String userName;
    private String userPassword;
    private ArrayList<String> notifications;

    public void setNotifications(ArrayList<String> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }



}
