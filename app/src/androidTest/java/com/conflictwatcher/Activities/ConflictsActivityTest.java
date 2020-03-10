package com.conflictwatcher.Activities;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import com.conflictwatcher.Activities.LoginReg.LoginActivity;
import com.conflictwatcher.Conflicts.ConflictAfghan;
import com.conflictwatcher.Conflicts.ConflictIraq;
import com.conflictwatcher.Conflicts.ConflictKurdTurk;
import com.conflictwatcher.Conflicts.ConflictLibya;
import com.conflictwatcher.Conflicts.ConflictMexico;
import com.conflictwatcher.Conflicts.ConflictSomali;
import com.conflictwatcher.Conflicts.ConflictSyria;
import com.conflictwatcher.Conflicts.ConflictYemen;
import com.conflictwatcher.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class ConflictsActivityTest {

    @Rule
    public ActivityTestRule<ConflictsActivity> mActivityTestRule = new ActivityTestRule<>(ConflictsActivity.class);

    private ConflictsActivity mActivity = null;

    @Before
    public void setUp() throws Exception {

        mActivity = mActivityTestRule.getActivity();

    }

    @Test
    public void syriaButtonTest(){

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ConflictSyria.class.getName(), null, false);

        assertNotNull(mActivity.findViewById(R.id.act_conflict_syria));

        onView(withId(R.id.act_conflict_syria)).perform(click());

        Activity SyriaActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 4500);

        assertNotNull(SyriaActivity);

        SyriaActivity.finish();
    }

    @Test
    public void afghanButtonTest(){

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ConflictAfghan.class.getName(), null, false);

        assertNotNull(mActivity.findViewById(R.id.act_conflict_afghan));

        onView(withId(R.id.act_conflict_afghan)).perform(click());

        Activity AfghanActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 4500);

        assertNotNull(AfghanActivity);

        AfghanActivity.finish();
    }

    @Test
    public void yemenButtonTest(){

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ConflictYemen.class.getName(), null, false);

        assertNotNull(mActivity.findViewById(R.id.act_conflict_yemen));

        onView(withId(R.id.act_conflict_yemen)).perform(click());

        Activity YemenActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 4500);

        assertNotNull(YemenActivity);

        YemenActivity.finish();
    }

    @Test
    public void mexicoButtonTest(){

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ConflictMexico.class.getName(), null, false);

        assertNotNull(mActivity.findViewById(R.id.act_conflict_mexico));

        onView(withId(R.id.act_conflict_mexico)).perform(click());

        Activity MexicoActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 4500);

        assertNotNull(MexicoActivity);

        MexicoActivity.finish();
    }

    @Test
    public void somaliButtonTest(){

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ConflictSomali.class.getName(), null, false);

        assertNotNull(mActivity.findViewById(R.id.act_conflict_somali));

        onView(withId(R.id.act_conflict_somali)).perform(click());

        Activity SomaliActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 4500);

        assertNotNull(SomaliActivity);

        SomaliActivity.finish();
    }

    @Test
    public void iraqButtonTest(){

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ConflictIraq.class.getName(), null, false);

        assertNotNull(mActivity.findViewById(R.id.act_conflict_iraq));

        onView(withId(R.id.act_conflict_iraq)).perform(click());

        Activity IraqConflict = getInstrumentation().waitForMonitorWithTimeout(monitor, 4500);

        assertNotNull(IraqConflict);

        IraqConflict.finish();
    }

    @Test
    public void libyaButtonTest(){

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ConflictLibya.class.getName(), null, false);

        assertNotNull(mActivity.findViewById(R.id.act_conflict_libya));

        onView(withId(R.id.act_conflict_libya)).perform(click());

        Activity LibyaConflict = getInstrumentation().waitForMonitorWithTimeout(monitor, 4500);

        assertNotNull(LibyaConflict);

        LibyaConflict.finish();
    }

    @Test
    public void kurdturkButtonTest(){

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(ConflictKurdTurk.class.getName(), null, false);

        assertNotNull(mActivity.findViewById(R.id.act_conflict_kurd_turk));

        onView(withId(R.id.act_conflict_kurd_turk)).perform(click());

        Activity KurdTurkConflict = getInstrumentation().waitForMonitorWithTimeout(monitor, 4500);

        assertNotNull(KurdTurkConflict);

        KurdTurkConflict.finish();
    }




    @After
    public void tearDown() throws Exception {


        mActivity = null;
    }
}