package com.conflictwatcher.Activities;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.conflictwatcher.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapActivityTest {

    @Rule
    public ActivityTestRule<MapActivity> mActivityTestRule = new ActivityTestRule<>(MapActivity.class);

    private MapActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

    }


    @Test
    public void mapTest(){

        assertNotNull(mActivity.findViewById(R.id.map));


        //From example test...
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.conflictwatcher", appContext.getPackageName());




    }

    @After
    public void tearDown() throws Exception {

        mActivity = null;


    }
}