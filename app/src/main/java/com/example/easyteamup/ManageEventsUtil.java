package com.example.easyteamup;

import java.util.ArrayList;

public class ManageEventsUtil {
    public static boolean hasValidID(String id) {
        return !id.isEmpty();
    }

    public static boolean hasValidName(String name) {
        return !name.isEmpty();
    }

//    public static boolean hasValidLocation(String location) {
//        return !location.isEmpty();
//    }

    public static boolean hasValidDescription(String description) {
        return !description.isEmpty();
    }

    public static boolean hasValidDueTime(String dueTime) {
        return !dueTime.isEmpty();
    }

    public static boolean hasValidFields(String id, String name, String location, String description,
                                         String dueTime) {
        return hasValidID(id) && hasValidName(name) &&
                hasValidDescription(description) && hasValidDueTime(dueTime);
    }
}
