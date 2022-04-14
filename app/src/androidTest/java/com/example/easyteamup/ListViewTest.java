package com.example.easyteamup;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ListViewTest {
    @Test
    public void ListtoListPageTest(){
        ActivityScenario.launch(ListViewActivity.class);
        onView(withId(R.id.listtolist)).perform(click());
        onView(withId(R.id.listviewpage)).check(matches(isDisplayed()));
    }

    @Test
    public void ListtoMapPageTest(){
        ActivityScenario.launch(ListViewActivity.class);
        onView(withId(R.id.listtomap)).perform(click());
        onView(withId(R.id.mappage)).check(matches(isDisplayed()));
    }
}