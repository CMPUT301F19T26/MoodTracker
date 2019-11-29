///*
// * MIT License
// *
// * Copyright (c) 2019 CMPUT301F19T26
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in all
// * copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// * SOFTWARE.
// */
//
//
//package com.example.moodtracker;
//
//import android.app.Activity;
//
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.platform.app.InstrumentationRegistry;
//import androidx.test.rule.ActivityTestRule;
//
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//
//import com.example.moodtracker.view.FeedActivity;
//import com.example.moodtracker.view.LoginActivity;
//import com.example.moodtracker.view.MainActivity;
//import com.example.moodtracker.view.ProfileViewActivity;
//import com.example.moodtracker.view.SignupActivity;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.robotium.solo.Solo;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//
//import java.util.concurrent.TimeUnit;
//
//import static junit.framework.TestCase.assertTrue;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//
//@RunWith(AndroidJUnit4.class)
//public class FollowTest {
//
//    private Solo solo;
//    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//    @Rule
//    public ActivityTestRule<LoginActivity> rule =
//            new ActivityTestRule<>(LoginActivity.class, true, true);
//
//    @Before
//    public void setUp() throws Exception{
//
//        //set up
//        db.clearPersistence();
//        FirebaseAuth.getInstance().signOut();
//        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
//        Activity activity = rule.getActivity();
//
//        //create two users
//        //sign up user 1
//        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
//        solo.clickOnText("Signup");
//        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);
//        solo.enterText((EditText)solo.getView(R.id.text_email), "follow1@test.com");
//        solo.enterText((EditText)solo.getView(R.id.text_username), "follow1");
//        solo.enterText((EditText)solo.getView(R.id.text_password), "password");
//        solo.clickOnButton("Signup");
//        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);
//
//        //log out
//        solo.clickOnText("Profile");
//        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);
//        View navbar = solo.getView(R.id.toggler);
//        solo.clickOnView(navbar);
//        solo.clickOnText("Logout");
//
//        //sign up user 2
//        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
//        solo.clickOnText("Signup");
//        solo.assertCurrentActivity("Wrong Activity", SignupActivity.class);
//        solo.enterText((EditText)solo.getView(R.id.text_email), "follow2@test.com");
//        solo.enterText((EditText)solo.getView(R.id.text_username), "follow2");
//        solo.enterText((EditText)solo.getView(R.id.text_password), "password");
//        solo.clickOnButton("Signup");
//        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);
//
//        //log out
//        solo.clickOnText("Profile");
//        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);
//        solo.clickOnView(navbar);
//        solo.clickOnText("Logout");
//
//    }
//
//    @Test
//    public void testFollow() {
//
//        View navbar = solo.getView(R.id.toggler);
//
//        //login and follow another user
//        //login
//        //Login to app
//        solo.enterText((EditText) solo.getView(R.id.text_email), "follow1@email.com");
//        solo.enterText((EditText) solo.getView(R.id.text_password), "password");
//        solo.clickOnText("Login");
//        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);
//
//        //search for other user
//        View search = solo.getView("ic_Search");
//        solo.clickOnView(search);
//        solo.enterText((EditText) solo.getView(R.id.search_bar), "follow2");
//        solo.clickInList(1);
//        solo.clickOnText("Follow");
//
//        //logout
//        solo.clickOnText("Profile");
//        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);
//        solo.clickOnView(navbar);
//        solo.clickOnText("Logout");
//
//        //login to other user and accept request
//        solo.enterText((EditText) solo.getView(R.id.text_email), "follow2@email.com");
//        solo.enterText((EditText) solo.getView(R.id.text_password), "password");
//        solo.clickOnText("Login");
//        solo.assertCurrentActivity("Wrong Activity", FeedActivity.class);
//
//        //acccept request
//        solo.clickOnText("Profile");
//        solo.assertCurrentActivity("Wrong Activity", ProfileViewActivity.class);
//        solo.clickOnView(navbar);
//        solo.clickOnText("Follow Requests");
//
//
//
//
//
//
//    }
//
//    @After
//    public void reset() {
////
//        View navbar = solo.getView(R.id.toggler);
//        solo.clickOnView(navbar);
//        solo.clickOnText("Logout");
//
//    }
//
//
//
//}
