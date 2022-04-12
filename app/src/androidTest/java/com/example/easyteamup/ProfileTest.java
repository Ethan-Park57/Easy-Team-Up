package com.example.easyteamup;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isLeftOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileTest {
    @Rule
    public ActivityScenarioRule<ProfileActivity> paRule = new ActivityScenarioRule<ProfileActivity>(ProfileActivity.class);
    public ActivityScenarioRule<ProfileEditActivity> paeRule = new ActivityScenarioRule<ProfileEditActivity>(ProfileEditActivity.class);

    @Test
    public void checkEmail(){
        onView(withText("Your email:")).check(matches(isDisplayed()));
        onView(withText("Your email:")).check(isCompletelyLeftOf(withId(R.id.profile_email)));
    }

    @Test
    public void checkName(){
        onView(withText("Your name:")).check(matches(isDisplayed()));
        onView(withText("Your name:")).check(isCompletelyLeftOf(withId(R.id.profile_name)));
    }

}
