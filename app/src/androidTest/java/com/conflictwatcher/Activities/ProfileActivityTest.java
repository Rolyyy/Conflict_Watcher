package com.conflictwatcher.Activities;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.conflictwatcher.Activities.LoginReg.LoginActivity;
import com.conflictwatcher.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Set;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class ProfileActivityTest {

    @Rule
    public ActivityTestRule<ProfileActivity> mActivityTestRule = new ActivityTestRule<>(ProfileActivity.class);

    private ProfileActivity mActivity = null;





    @Before
    public void setUp() throws Exception {

        mActivity = mActivityTestRule.getActivity();
    }



    /**
    @Test
    public void testLogoutButtonClick() {

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LoginActivity.class.getName(), null, false);

    //asserts that the Logout Button exists, due to being declared
    assertNotNull(mActivity.findViewById(R.id.logout_btn));


    //Performs click on the button,
    onView(withId(R.id.logout_btn)).perform(click());

    //Waits until timeout, at 4500 milliseconds
    Activity LoginActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 4500);

    //Asserts that LoginActivity exists, meaning activity change was successful
    assertNotNull(LoginActivity);

    LoginActivity.finish();
    }
    */

    @Test
    public void userEmailTest(){

        assertNotNull(mActivity.findViewById(R.id.profile_email));

    }

    @Test
    public void settingsButtonTest(){

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(SettingsActivity.class.getName(), null, false);

        assertNotNull(mActivity.findViewById(R.id.settings_btn));

        onView(withId(R.id.settings_btn)).perform(click());

        Activity SettingsActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 4500);

        assertNotNull(SettingsActivity);


        SettingsActivity.finish();


    }


    @After
    public void tearDown() throws Exception {

        mActivity = null;
    }
}