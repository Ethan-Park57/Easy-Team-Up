package com.example.easyteamup;

import static org.junit.Assert.*;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.contrib.RecyclerViewActions;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.Before;

public class ReceivedEventsActivityTest {
    /*
    I created and added an event to db which invites a particular user. These are the details:

        description: "r"
        dueTime: April 29, 2022 at 12:27:06 AM UTC-7
        eventID: "XpoCu4ZiX6qmfFe0IAbz"
        eventName: "testreceived"
        hostID: "qtl8SeoKvbU1psqQ0ZfWrxU4rad2"
        invitees:
          0: "qdRFRlLoaOX4KJUR9joUrvfJLxz1"
        isPrivateEvent: false
        location: "fr"
        participants:
          0: "qtl8SeoKvbU1psqQ0ZfWrxU4rad2"
        proposedTimes:
          0: April 12, 2022 at 5:27:02 AM UTC-7
        proposedTimesVotes:
          0: 0

    this is the event I will be referring to in order to test Received Events
    */

    @Rule
    public ActivityScenarioRule activityScenarioRule
            = new ActivityScenarioRule(WelcomeActivity.class);

    @Before
    public void setUp() {
        // enter invitee email
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.email_text)).perform(typeText("receivedeventstest@gmail.com"),
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
        onView(withId(R.id.to_received_button)).perform(click());
    }

    // recycler view comes into view
    @Test
    public void recyclerViewIsDisplayed() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    /* test that the event shown is the correct one by checking event name
     * ideally, we would check the IDs but that isn't possible with current built.
     * In future, add ID to details page to test this more thoroughly
     */
    @Test
    public void correctEventShownTest() {
        // go to received events details page by clicking on first (and only) item
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.event_name_text_view_details_page)).check(matches(withText("testreceived")));
    }

    // check pressing back causes recycler view to come back into view
    @Test
    public void onBackButtonPressRecyclerViewIsDisplayed() {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        pressBack();
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }
}