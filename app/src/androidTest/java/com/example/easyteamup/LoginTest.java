package com.example.easyteamup;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
public class LoginTest {
    @Before
    public void setup()
    {
        ActivityScenario.launch(LoginActivity.class);
        // Type email and password
        Espresso.onView(ViewMatchers.withId(R.id.email_text))
                .perform(ViewActions.typeText("zoo@zoo.com"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.password_text))
                .perform(ViewActions.typeText("123456"), ViewActions.closeSoftKeyboard());
    }
    @Test
    public void backbuttonclicked()
    {
        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.back_button)).perform(click());
        onView(withId(R.id.welcomepage)).check(matches(isDisplayed()));
    }

    @Test
    public void emailTextFilled(){
        //onView(withId(R.id.email_text)).check(matches(withText("zoo@zoo.com")));
    }

    @Test
    public void passwordTextFilled(){
        //onView(withId(R.id.email_text)).check(matches(withText("123456")));
    }

    @Test
    public void loginbuttonclicked(){
        // Click login button
        Espresso.onView(ViewMatchers.withId(R.id.logging_button)).perform(ViewActions.click());
        onView(withId(R.id.listviewpage)).check(matches(isDisplayed()));
    }
}