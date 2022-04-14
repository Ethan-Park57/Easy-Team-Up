package com.example.easyteamup;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterTest {
    @Before
    public void setup()
    {
        ActivityScenario.launch(RegisterActivity.class);
        // Type email and password
        Espresso.onView(ViewMatchers.withId(R.id.register_email))
                .perform(ViewActions.typeText("testy22@testy.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.register_name))
                .perform(ViewActions.typeText("Testy692"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.register_password))
                .perform(ViewActions.typeText("123456"), ViewActions.closeSoftKeyboard());
    }

    @Test
    public void backbuttonTest(){
        ActivityScenario.launch(RegisterActivity.class);
        onView(withId(R.id.back_button)).perform(click());
        onView(withId(R.id.welcomepage)).check(matches(isDisplayed()));
    }

//    @Test
//    public void RegisterButtonTest(){
//        Espresso.onView(ViewMatchers.withId(R.id.registration_button)).perform(ViewActions.click());
//        onView(withId(R.id.listviewpage)).check(matches(isDisplayed()));
//    }
}