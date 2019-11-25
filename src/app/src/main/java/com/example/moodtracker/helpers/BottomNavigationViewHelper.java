/*
 * BottomNavigationViewHelper
 *
 * Version 1.0
 *
 * 11/8/2019
 *
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
 *
 */

package com.example.moodtracker.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.moodtracker.R;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.FeedActivity;
import com.example.moodtracker.view.FindActivity;
import com.example.moodtracker.view.MapActivity;
import com.example.moodtracker.view.mood.AddMoodEventActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

/**
 * Handles navigation using the bottom bar
 */
public class BottomNavigationViewHelper {

    /**
     * Disables Shift mode
     * @param view the view on the navigation bar
     */
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
            //noinspection RestrictedApi
            item.setShifting(false);
            item.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

            // set once again checked value, so view will be updated
            //noinspection RestrictedApi
            item.setChecked(item.getItemData().isChecked());
        }
    }

    /**
     * Determines where to navigate to based on what button has been selected
     * @param context the context of the button
     * @param bnv the view for the bottom navigation
     */
    public static void enableNavigation(final Context context, BottomNavigationView bnv) {
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ic_Feed:
                        Intent feedIntent = new Intent(context, FeedActivity.class);
                        context.startActivity(feedIntent);
                        break;

                    case R.id.ic_Profile:

                        break;

                    case R.id.ic_Search:
                        Intent intent1 = new Intent(context, FindActivity.class);
                        context.startActivity(intent1);
                        break;

                    case R.id.ic_Add:
                        Intent intent2 = new Intent(context, AddMoodEventActivity.class);
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_Map:
                        Intent mapIntent = new Intent(context, MapActivity.class);

                        User Jared = new User("21");
                        mapIntent.putExtra("USER", Jared);
                        mapIntent.putExtra("MODE", 0);
                        context.startActivity(mapIntent);
//                        Intent intent3 = new Intent(ProfileViewActivity.this, MapActivity.class);
//                        startActivity(intent3);
                        break;



                }

                return false;
            }
        });

    }
}
