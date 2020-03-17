package com.conflictwatcher.Activities.LoginReg;

import android.app.Activity;
import android.app.Instrumentation;
import android.widget.EditText;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;


import com.conflictwatcher.Activities.MapActivity;
import com.conflictwatcher.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    private LoginActivity mActivity = null;





    @Before
    public void setUp() throws Exception {

        mActivity = mActivityTestRule.getActivity();


    }

    //@Test
    public void loginTest(){

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MapActivity.class.getName(), null, false);

         String email = "b.roland@ymail.com";
         String password = "123Hello";

        //Enters email and password into textboxes

        //onView(withId(R.id.loginEmail)).perform(typeText("b.roland@ymail.com"), closeSoftKeyboard());



        onView(withId(R.id.loginEmail)).perform(click(), replaceText(email));
        onView(withId(R.id.loginEmail)).check(matches(withText(email)));
        //Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginEmail)).perform(closeSoftKeyboard());
        //onView(withId(R.id.loginPassword)).perform(typeText("123Hello"), closeSoftKeyboard());


        onView(withId(R.id.loginPassword)).perform(click(), replaceText(password));
        onView(withId(R.id.loginPassword)).check(matches(withText(password)));
        closeSoftKeyboard();


        //Closes soft keyboard
        //Espresso.closeSoftKeyboard();

        //performs click on login button
        onView(withId(R.id.loginButton)).perform(click());

        Activity MapActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 4500);
        assertNotNull(MapActivity);
        MapActivity.finish();


    }


    @After
    public void tearDown() throws Exception {

        mActivity = null;


    }
}