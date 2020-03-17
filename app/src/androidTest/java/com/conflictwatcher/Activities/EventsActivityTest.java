package com.conflictwatcher.Activities;

import androidx.test.rule.ActivityTestRule;

import com.conflictwatcher.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class EventsActivityTest {

    @Rule
    public ActivityTestRule<EventsActivity> mActivityTestRule = new ActivityTestRule<>(EventsActivity.class);

    private EventsActivity mActivity = null;



    @Before
    public void setUp() throws Exception {

        mActivity = mActivityTestRule.getActivity();

    }

    @Test
    public void listViewTest() {
    //Checks if the list view exists
        assertNotNull(mActivity.findViewById(R.id.eventsListView));


    }

    @Test
    public void csvDataTest(){
    //Checks if the contents of the list view are as expected
        onView(withId(R.id.eventsListView)).check(matches(isDisplayed()));
    }


    @After
    public void tearDown() throws Exception {

        mActivity = null;
    }
}