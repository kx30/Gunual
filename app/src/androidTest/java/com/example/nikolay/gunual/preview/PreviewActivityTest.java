package com.example.nikolay.gunual.preview;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.example.nikolay.gunual.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PreviewActivityTest {

    @Rule
    public ActivityTestRule<PreviewActivity> mActivity = new ActivityTestRule<>(PreviewActivity.class);

    @Test
    public void userScenario() {
        Espresso.onView(withId(R.id.skip_button)).perform(click());
    }

}