package com.example.easyteamup;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WelcomeTest {
    //@Rule
    //public ActivityScenarioRule<WelcomeActivity> wRule = new ActivityScenarioRule<WelcomeActivity>(WelcomeActivity.class);

    @Test
    public void registerbuttonclicked(){
        ActivityScenario.launch(WelcomeActivity.class);
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.activityregister)).check(matches(isDisplayed()));
    }

    @Test
    public void loginbuttonclicked(){
        ActivityScenario.launch(WelcomeActivity.class);
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.activitylogin)).check(matches(isDisplayed()));
    }
}