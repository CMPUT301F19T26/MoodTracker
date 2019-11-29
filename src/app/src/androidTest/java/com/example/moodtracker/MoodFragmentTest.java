/*
 * MIT License
 *
 * Copyright (c) 2019 CMPUT301F19T26
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.moodtracker;
/*
 * MIT License
 *
 * Copyright (c) 2019 CMPUT301F19T26
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


import android.app.Activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.moodtracker.adapter.MoodHistoryAdapter;
import com.example.moodtracker.model.MoodEvent;
import com.example.moodtracker.model.MoodHistory;
import com.example.moodtracker.view.FeedActivity;
import com.example.moodtracker.view.LoginActivity;
import com.example.moodtracker.view.MainActivity;
import com.example.moodtracker.view.ProfileViewActivity;
import com.example.moodtracker.view.SignupActivity;
import com.example.moodtracker.view.mood.AddMoodEventActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;


import junit.framework.AssertionFailedError;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeUnit;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static java.security.AccessController.getContext;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MoodFragmentTest {

    private Solo solo;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid = FirebaseAuth.getInstance().getUid();
    MoodHistory moodHistory;



    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);



    @Before
    public void setUp() throws Exception {


        //clear cache
        db.clearPersistence();
        FirebaseAuth.getInstance().signOut();
//
//      //set up login activity
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        Activity activity = rule.getActivity();

        //Login to app
        solo.enterText((EditText) solo.getView(R.id.text_email), "ankush@email.com");
        solo.enterText((EditText) solo.getView(R.id.text_password), "password");

        solo.clickOnText("Login");
        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);

        solo.clickOnText("Profile");
        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);



    }

    //test to add event
    @Test
    public void addEvent() {

        //Add mood event
        View CTButton = solo.getView("ic_Add");
        solo.clickOnView(CTButton);
        solo.assertCurrentActivity("Wrong Activity", AddMoodEventActivity.class);

        //Add Mood
        View mood_dropdown = solo.getView(Spinner.class, 0);
        solo.clickOnView(mood_dropdown);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 3));

        //Add Social Situation
        View social_dropdown = solo.getView(Spinner.class, 1);
        solo.clickOnView(social_dropdown);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 3));

        //click location
//        View switcher = solo.getView(R.id.location_switch);
//        solo.clickOnView(switcher);
//        solo.clickOnText("Allow only while using the app");

        //Add Reason
        solo.enterText((EditText)solo.getView(R.id.reason_edit), "MOOOO");

        //Submit Mood
        solo.clickOnText("Submit");

        //check feed
        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);


    }

    @Test
    public void editEvent() {

        View CTButton = solo.getView("ic_Add");
        solo.clickOnView(CTButton);
//
        solo.assertCurrentActivity("Wrong Activity", AddMoodEventActivity.class);
//
        //getmood
        View mood_dropdown = solo.getView(Spinner.class, 0);
        solo.clickOnView(mood_dropdown);
        solo.scrollToTop(); // I put this in here so that it always keeps the list at start
        // select the 10th item in the spinner
        solo.clickOnView(solo.getView(TextView.class, 3));

        //click submit
        solo.clickOnText("Submit");

        //open event
        ListView view = (ListView) solo.getView(R.id.mood_history);

        solo.clickInList(1);

        View edit = solo.getView("edit");
        solo.clickOnView(edit);

        //get social situation
        View social_dropdown = solo.getView(Spinner.class, 1);
        solo.clickOnView(social_dropdown);
        solo.scrollToTop(); // I put this in here so that it always keeps the list at start
        // select the 10th item in the spinner
        solo.clickOnView(solo.getView(TextView.class, 5));

        //enter reason
        solo.enterText((EditText)solo.getView(R.id.reason_me), "WOW");

        //click submit
//        View done = solo.getView("done_edit");
//        solo.clickOnView(done);

        solo.clickOnText("Done");

        solo.goBack();

        //check feed
        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);

    }

    @Test
    public void Delete(){

        //Add mood event
        View CTButton = solo.getView("ic_Add");
        solo.clickOnView(CTButton);
        solo.assertCurrentActivity("Wrong Activity", AddMoodEventActivity.class);

        //Add Mood
        View mood_dropdown = solo.getView(Spinner.class, 0);
        solo.clickOnView(mood_dropdown);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 3));

        //Add Social Situation
        View social_dropdown = solo.getView(Spinner.class, 1);
        solo.clickOnView(social_dropdown);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 3));

        //click location
//        View switcher = solo.getView(R.id.location_switch);
//        solo.clickOnView(switcher);
//        solo.clickOnText("Allow only while using the app");

        //Add Reason
        solo.enterText((EditText)solo.getView(R.id.reason_edit), "MOOOO");

        //Submit Mood
        solo.clickOnText("Submit");

        //check feed
        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);

        //click event
        solo.clickInList(1);

        solo.clickOnText("Delete");

    }


    @After
    public void reset() {
//
        View navbar = solo.getView(R.id.toggler);
        solo.clickOnView(navbar);
        solo.clickOnText("Logout");

//        System.out.println("AFTER");

//        rule.launchActivity(null);

//        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
//        Activity activity = rule.getActivity();
    }
}
