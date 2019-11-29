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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest {

    private Solo solo;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);

    @Before
    public void setUp() throws Exception{

        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void astart() throws Exception{
        Activity activity = rule.getActivity();
        db.clearPersistence();
        FirebaseAuth.getInstance().signOut();
    }

    @Test
    public void badLogin(){

        FirebaseAuth.getInstance().signOut();
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText)solo.getView(R.id.text_email), "bob_jones@email.com");
        solo.enterText((EditText)solo.getView(R.id.text_password), "password");
        solo.clickOnButton("Login");
//        solo.clickOnView(solo.getView(R.id.button_login));
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        boolean exists = solo.searchText("Authentication failed.");
        assertEquals(true,exists);
//
//
    }
//
    @Test
    public void createUser() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnText("Signup");
        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);
        solo.enterText((EditText)solo.getView(R.id.text_email), "bob_jones@email.com");
        solo.enterText((EditText)solo.getView(R.id.text_username), "bob_jones");
        solo.enterText((EditText)solo.getView(R.id.text_password), "password");
        solo.clickOnButton("Signup");
        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);

    }
//
    @Test
    public void dLogout() {

        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);
//        solo.clickOnButton("Profile");
//        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);
//        solo.clickOnButton("Logout");
//        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

    }

    @Test
    public void ecreateExistingUser() {

        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnText("Signup");
        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);

        solo.enterText((EditText)solo.getView(R.id.text_email), "bob_jones@email.com");
        solo.enterText((EditText)solo.getView(R.id.text_username), "bob_jones");
        solo.enterText((EditText)solo.getView(R.id.text_password), "password");

        solo.clickOnButton("Signup");

        boolean exists = solo.searchText("Username taken!");

        assertEquals(true,exists);

    }
//
    @Test
    public void fLogin() {

        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);
        solo.clickOnText("Login");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        solo.enterText((EditText)solo.getView(R.id.text_email), "bob_jones@email.com");
        solo.enterText((EditText)solo.getView(R.id.text_password), "password");
        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);

    }
//
    @After
    public void cleanUp(){
//        db.collection("usernames").document("bob_jones").delete();
//        db.collection("users").document(FirebaseAuth.getInstance().getUid()).delete();
//        FirebaseAuth.getInstance().getCurrentUser().delete();


//
//        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);
//        solo.clickOnButton("Profile");
//        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);
//        solo.clickOnButton("Logout");
//        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);


    }
//
//





}
