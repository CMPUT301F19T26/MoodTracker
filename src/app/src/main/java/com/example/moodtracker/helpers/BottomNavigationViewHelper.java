package com.example.moodtracker.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.moodtracker.R;
import com.example.moodtracker.model.User;
import com.example.moodtracker.view.FindActivity;
import com.example.moodtracker.view.MapActivity;
import com.example.moodtracker.view.ProfileFragment;
import com.example.moodtracker.view.mood.AddMoodEventActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.lang.reflect.Field;

public class BottomNavigationViewHelper {
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

    public static void enableNavigation(final Context context, BottomNavigationView bnv) {
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
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
//                        Intent intent3 = new Intent(ProfileFragment.this, MapActivity.class);
//                        startActivity(intent3);
                        break;

                    case R.id.ic_Feed:
//                        Intent moodHistoryIntent = new Intent(ProfileFragment.this, MoodHistoryActivity.class);
//                        moodHistoryIntent.putExtra("userID", fAuth.getCurrentUser().getUid());
//                        startActivity(moodHistoryIntent);
                        break;

                }

                return false;
            }
        });

    }
}
