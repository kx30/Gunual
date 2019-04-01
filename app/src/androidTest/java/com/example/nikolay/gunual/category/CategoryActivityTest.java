package com.example.nikolay.gunual.category;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.example.nikolay.gunual.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class CategoryActivityTest {

    @Rule
    public ActivityTestRule<CategoryActivity> mActivity = new ActivityTestRule<>(CategoryActivity.class);

    @Test
    public void userScenario() throws InterruptedException {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        boolean isVisible = false;
        while (!isVisible) {
            isVisible = checkIsVisible();
        }
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.add_to_favorite)).perform(click());
        onView(withId(R.id.buy_gun_button)).perform(click());
        toWait(5000);
        goToBack();
        onView(withId(R.id.buy_ammo_button)).perform(click());
        toWait(5000);
        goToBack();
        goToBack();
        clickOnFilterButton();
        clickerToCountrySpinner("India");
        clickOnAcceptButton();
        toWait(1000);
        clickOnFilterButton();
        clickerToAmmoSpinner("5.56");
        clickOnAcceptButton();
        toWait(1000);
        clickOnFilterButton();
        clickerToCountrySpinner("Dominican Republic");
        clickerToAmmoSpinner(".30");
        clickOnAcceptButton();
        goToBack();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.favorite)).perform(click());
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.remove_from_favorites)).perform(click());
        goToBack();
        toWait(1000);
        goToBack();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.favorite)).perform(click());
        toWait(1000);
        goToBack();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText(R.string.about_us)).perform(click());
        toWait(1000);
    }

    private void clickOnAcceptButton() {
        onView(withId(R.id.accept_button)).perform(click());
    }

    private void goToBack() {
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    private void clickOnFilterButton() {
        onView(withId(R.id.filter_button)).perform(click());
    }

    private void clickerToCountrySpinner(String text) {
        onView(withId(R.id.country_spinner)).perform(click());
        onView(withText(text)).perform(click());
    }

    private void clickerToAmmoSpinner(String text) {
        onView(withId(R.id.ammunition_spinner)).perform(click());
        onView(withText(text)).perform(click());
    }

    private void toWait(int duration) throws InterruptedException {
        Thread.sleep(duration);
    }

    private boolean checkIsVisible() {
        try {
            onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
            return false;
        } catch (Throwable throwable) {
            return true;
        }
    }
}

