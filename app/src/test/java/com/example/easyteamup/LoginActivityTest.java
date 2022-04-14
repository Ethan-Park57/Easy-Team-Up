package com.example.easyteamup;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;
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
import java.util.Objects;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({ FirebaseDatabase.class})
@PowerMockIgnore({"javax.management.*", "com.sun.org.apache.xerces.*", "javax.xml.*",
        "org.xml.*", "org.w3c.dom.*", "com.sun.org.apache.xalan.*", "javax.activation.*"})

public class LoginActivityTest {
    FirebaseAuth auth= FirebaseAuth.getInstance();;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String testemail = "true@true.com";
    String testpassword = "123456";
    String id = auth.getCurrentUser().getUid();

    @Test
    public void loggingUserTestEmail(){
        assertEquals(db.collection("users").whereEqualTo("hostID", id).get(Source.valueOf("userEmail")), testemail);
    }

    @Test
    public void logginUserTestPassword(){
        assertEquals(db.collection("users").whereEqualTo("hostID", id).get(Source.valueOf("userPassword")), testpassword);
    }
}