package com.example.easyteamup;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegisteredEventsActivityTest {
/*
    I created and added an event to db which invites a particular user. These are the details:

        description: "test"
dueTime: May 7, 2022 at 4:49:35 AM UTC-7
eventID: "yFXiVFiBUMxYf5fIO4cp"
eventName: "registeredtest"
hostID: "D5R59KZ3dbZZzuMkodeLVP5kyZ63"
invitees:
    0: "dFDZ9Oj9TgbQI5Bp9bR4poQxBNP2"
isPrivateEvent: true
location: "test"
participants:
    0: "D5R59KZ3dbZZzuMkodeLVP5kyZ63"
proposedTimes:
    0: April 30, 2022 at 4:49:31 AM UTC-7
proposedTimesVotes:
    0: 0


    this is the event I will be referring to in order to test Received Events
    */

    @Rule
    public ActivityScenarioRule activityScenarioRule
            = new ActivityScenarioRule(WelcomeActivity.class);

    @Before
    public void setUp() {
        onView(withId(R.id.login_button)).perform(click());

        // enter invitee email
        onView(withId(R.id.email_text)).perform(typeText("registeredeventstest@gmail.com"),
                closeSoftKeyboard());
        // enter invitee password
        onView(withId(R.id.password_text)).perform(typeText("123456"),
                closeSoftKeyboard());
        // press login button
        onView(withId(R.id.logging_button)).perform(click());

        /* !!! - USER HAS BEEN LOGGED IN - WE ARE NOW ON HOME PAGE - !!! */
        // go to manage events page
        onView(withId(R.id.manage_event_page)).perform(click());
        // go to received events page
        onView(withId(R.id.to_registered_button)).perform(click());
    }

    // recycler view comes into view
    @Test
    public void recyclerViewIsDisplayed() {
        onView(withId(R.id.registered_recycler_view)).check(matches(isDisplayed()));
    }

    /* test that the event shown is the correct one by checking event name
     * ideally, we would check the IDs but that isn't possible with current built.
     * In future, add ID to details page to test this more thoroughly
     */
    @Test
    public void correctEventShownTest() {
        // go to registered events details page by clicking on first (and only) item
        onView(withId(R.id.registered_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.event_name_text_view_details_page)).check(matches(withText("registeredtest")));
    }

    // check pressing back causes recycler view to come back into view
    @Test
    public void onBackButtonPressRecyclerViewIsDisplayed() {
        onView(withId(R.id.registered_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        pressBack();
        onView(withId(R.id.registered_recycler_view)).check(matches(isDisplayed()));
    }
}