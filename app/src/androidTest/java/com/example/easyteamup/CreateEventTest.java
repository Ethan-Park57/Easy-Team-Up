package com.example.easyteamup;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
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
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateEventTest {

    @Rule
    public GrantPermissionRule runtimePermissionRule = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityScenarioRule<CreateEventActivity> paRule = new ActivityScenarioRule<CreateEventActivity>(CreateEventActivity.class);
    public ActivityScenarioRule<ManageEventActivity> maRule = new ActivityScenarioRule<ManageEventActivity>(ManageEventActivity.class);

    @Test
    public void createEventTest(){
        String stringInput = "testinputs";
        onView(withId(R.id.event_name)).perform(closeSoftKeyboard());
        onView(withId(R.id.event_name)).perform(replaceText(stringInput));
        onView(withId(R.id.event_name)).perform(closeSoftKeyboard());

        onView(withId(R.id.event_description)).perform(closeSoftKeyboard());
        onView(withId(R.id.event_description)).perform(replaceText(stringInput));
        onView(withId(R.id.event_description)).perform(closeSoftKeyboard());

        onView(withId(R.id.invite_list_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.invite_list_text)).perform(replaceText(stringInput));
        onView(withId(R.id.invite_list_text)).perform(closeSoftKeyboard());

        onView(withId(R.id.event_location_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.event_location_text)).perform(replaceText(stringInput));
        onView(withId(R.id.event_location_text)).perform(closeSoftKeyboard());

        onView(withId(R.id.is_private_check_box)).perform(click());

        String inviteList = "rec@rec.com,jj@jj.com";
        onView(withId(R.id.invite_list_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.invite_list_text)).perform(replaceText(inviteList));
        onView(withId(R.id.invite_list_text)).perform(closeSoftKeyboard());

        onView(withId(R.id.create_event)).perform(click());
        onView(withId(R.id.invisible_text)).check(matches(withText("TextView")));
    }
}
