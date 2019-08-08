package com.android.assessment.currencycalculator;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onCreate() {
        onView((withId(R.id.convert)));
        onView((withId(R.id.fromEditText)));
        onView((withId(R.id.toEditText)));
        onView((withId(R.id.fromCurrency)));
        onView((withId(R.id.toCurrency)));

        //Hit convert button
        onView((withId(R.id.convert))).perform(click());
    }
}