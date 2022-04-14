package com.example.easyteamup;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
@PowerMockIgnore({"javax.management.*", "com.sun.org.apache.xerces.*", "javax.xml.*",
        "org.xml.*", "org.w3c.dom.*", "com.sun.org.apache.xalan.*", "javax.activation.*"})

public class RegisterActivityTest {
    FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String testemail = "nagaemail@gmail.com";
    String testusername = "nishnaga";
    String testpassword = "1234567";
    String id = auth.getCurrentUser().getUid();

    @Before
    public void setup(){
        Map<String, Object> testdata = new HashMap<>();
        testdata.put("userEmail", testemail);
        testdata.put("userName", testusername);
        testdata.put("userPassword", testpassword);
        testdata.put("hostID", auth.getCurrentUser().getUid());
        System.out.println("USer id: " + auth.getCurrentUser().getUid());

        db.collection("users").document(id).set(testdata);
    }
    @Test
    public void RegisterUserEmailTest()
    {
        //check if each data is equal to the data in the userbase
        assertEquals(db.collection("users").whereEqualTo("hostID", id).get(Source.valueOf("userEmail")), testemail);

        System.out.println("Email Use Test Complete");
    }
    @Test
    public void RegisterUserNameTest()
    {
        //check if each data is equal to the data in the userbase
        assertEquals(db.collection("users").whereEqualTo("hostID", id).get(Source.valueOf("userUsername")), testusername);

        System.out.println("Username Test Complete");
    }
    @Test
    public void RegisterUserPasswordTest()
    {
        //check if each data is equal to the data in the userbase
        assertEquals(db.collection("users").whereEqualTo("hostID", id).get(Source.valueOf("userPassword")), testpassword);

        System.out.println("Password Use Test Complete");
    }

}