package com.example.easyteamup;

import static android.app.Activity.RESULT_OK;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityResult;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyLeftOf;
import static androidx.test.espresso.assertion.PositionAssertions.isLeftOf;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.EasyMock2Matchers.equalTo;
import static java.util.EnumSet.allOf;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileEditTest {

    @Rule
    public IntentsTestRule<ProfileEditActivity> activityRule =
            new IntentsTestRule<>(ProfileEditActivity.class);

    @Rule
    public GrantPermissionRule runtimePermissionRule = GrantPermissionRule
            .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

//    @ClassRule
//    public static DeviceAnimationTestRule deviceAnimationTestRule = new DeviceAnimationTestRule();

    @Rule
    public ActivityScenarioRule<ProfileEditActivity> paeRule = new ActivityScenarioRule<ProfileEditActivity>(ProfileEditActivity.class);
    @Test
    public void checkChangedName(){
        String changedName = "this is the changed name";
        onView(withId(R.id.edit_profile_change_name)).perform(closeSoftKeyboard());
        onView(withId(R.id.edit_profile_change_name)).perform(replaceText(changedName));
        onView(withId(R.id.edit_profile_change_name)).perform(closeSoftKeyboard());
        onView(withId(R.id.save_changes_button)).perform(click());
        onView(withId(R.id.profile_name)).check(matches(withText(changedName)));
    }
    @Test
    public void checkUploadImage(){
        Matcher<Intent> expected = AllOf.allOf(hasAction(Intent.ACTION_OPEN_DOCUMENT), hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI));

        ActivityResult activityResult = galleryResult();
        intending(expected).respondWith(activityResult);

        onView(withId(R.id.change_pic_button)).perform(click());

        intended(expected);
    }

    private ActivityResult galleryResult() {
        Uri uri = Uri.parse("android.resource://" + "com.example.easyteamup" + "/" + R.drawable.ic_launcher_background);
        Intent result = new Intent();
        result.setData(uri);
        return new ActivityResult(RESULT_OK, result);
    }






}
