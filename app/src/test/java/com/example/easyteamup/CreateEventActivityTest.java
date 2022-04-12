package com.example.easyteamup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.when;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import static junit.framework.TestCase.assertEquals;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
@PowerMockIgnore({"javax.management.*", "com.sun.org.apache.xerces.*", "javax.xml.*",
        "org.xml.*", "org.w3c.dom.*", "com.sun.org.apache.xalan.*", "javax.activation.*"})

public class CreateEventActivityTest{

    @Test
    public void isValidDescriptionTest() {
        assertEquals(false, Event.isValidDescription("t"));
        System.out.println("isValidDescriptionTest DONE");

    }

    @Test
    public void validDeadlineTest(){
        ArrayList<Date> proposedTimes = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2022, 5, 31, 31, 31, 4);
        Date time1 = calendar1.getTime();
        System.out.println("time1: " + time1);
        System.out.println("time1 year: " + time1.getYear());
        System.out.println("time1 month: " + time1.getMonth());
        assertEquals(true, Event.isValidDeadline(time1));
        System.out.println("isValidDeadlineTest DONE");
    }

    @Test
    public void validEventNameTest(){
        assertEquals(false, Event.isValidEventName(""));
        System.out.println("isValidEventNameTest DONE");
    }

    @Test
    public void validTimeProposalsTest(){
        ArrayList<Date> proposedTimes = new ArrayList<>();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2022, 5, 31, 31, 31, 4);
        Date time1 = calendar1.getTime();
        proposedTimes.add(time1);
        assertEquals(true, Event.isValidTimeProposal(proposedTimes));
        System.out.println("validTimeProposalsTest DONE");
    }

    @Test
    public void validInviteTest(){
        assertEquals(true, Event.isValidInvite(true));
        System.out.println("validInviteTest DONE");
    }

    @Test
    public void validFormatInviteTest(){
        assertEquals(true, Event.isValidFormatInvite("test@test.com,test2@test2.com"));
        System.out.println("validFormatInviteTest DONE");
    }
}