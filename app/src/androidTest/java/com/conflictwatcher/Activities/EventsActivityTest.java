package com.conflictwatcher.Activities;

import androidx.test.rule.ActivityTestRule;

import com.conflictwatcher.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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

        assertNotNull(mActivity.findViewById(R.id.eventsListView));



    }


    @After
    public void tearDown() throws Exception {

        mActivity = null;
    }
}