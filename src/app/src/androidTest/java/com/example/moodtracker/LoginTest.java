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

import android.app.Activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.moodtracker.view.FeedActivity;
import com.example.moodtracker.view.LoginActivity;
import com.example.moodtracker.view.MainActivity;
import com.example.moodtracker.view.ProfileViewActivity;
import com.example.moodtracker.view.SignupActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(AndroidJUnit4.class)
public class LoginTest {

    private Solo solo;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);

    @Before
    public void setUp() throws Exception{

        //set up
        db.clearPersistence();
        FirebaseAuth.getInstance().signOut();
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        Activity activity = rule.getActivity();

    }


    @Test
    public void createUserandLogIn() {

        //log in with invalid authentication
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText)solo.getView(R.id.text_email), "jared_jhonson@email.com");
        solo.enterText((EditText)solo.getView(R.id.text_password), "very_cool");
        solo.clickOnText("Login");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        boolean exists = solo.searchText("Authentication failed.");
        assertEquals(true,exists);

        //sign up user
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnText("Signup");
        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);
        solo.enterText((EditText)solo.getView(R.id.text_email), "david_jhonson@test.com");
        solo.enterText((EditText)solo.getView(R.id.text_username), "david_jhonson");
        solo.enterText((EditText)solo.getView(R.id.text_password), "password");
        solo.clickOnButton("Signup");
        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);

        //log out
        solo.clickOnText("Profile");
        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);
        View navbar = solo.getView(R.id.toggler);
        solo.clickOnView(navbar);
        solo.clickOnText("Logout");

        //Login to app
        solo.enterText((EditText) solo.getView(R.id.text_email), "david_jhonson@test.com");
        solo.enterText((EditText) solo.getView(R.id.text_password), "password");
        solo.clickOnText("Login");
        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);
        solo.clickOnText("Profile");
        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);


    }


    @Test
    public void CreateUser_and_Test_bad_signup() {

        //signup
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnText("Signup");
        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);
        solo.enterText((EditText)solo.getView(R.id.text_email), "jared@test.com");
        solo.enterText((EditText)solo.getView(R.id.text_username), "jared");
        solo.enterText((EditText)solo.getView(R.id.text_password), "password");
        solo.clickOnButton("Signup");
        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);

        //logout
        solo.clickOnText("Profile");
        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);
        View navbar = solo.getView(R.id.toggler);
        solo.clickOnView(navbar);
        solo.clickOnText("Logout");

        //signup
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnText("Signup");
        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);
        solo.enterText((EditText)solo.getView(R.id.text_email), "jared@test.com");
        solo.enterText((EditText)solo.getView(R.id.text_username), "jared");
        solo.enterText((EditText)solo.getView(R.id.text_password), "password");
        solo.clickOnButton("Signup");

        //assert username is taken
        boolean exists = solo.searchText("Username taken!");
        assertEquals(true,exists);

        //Test Login to app
        solo.clickOnText("Login");
        solo.enterText((EditText) solo.getView(R.id.text_email), "jared@test.com");
        solo.enterText((EditText) solo.getView(R.id.text_password), "password");
        solo.clickOnText("Login");
        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);
        solo.clickOnText("Profile");
        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);


    }



    @After
    public void reset() {

        View navbar = solo.getView(R.id.toggler);
        solo.clickOnView(navbar);
        solo.clickOnText("Logout");

    }


}







