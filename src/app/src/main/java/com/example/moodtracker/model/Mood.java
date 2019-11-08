/**
 * Mood
 *
 * Version 1.0
 *
 * 11/8/2019
 *
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.moodtracker.model;
import com.example.moodtracker.R;
import com.example.moodtracker.constants;

public class Mood {
    // Should not need db logic in mood
    private int color;
    private int icon;
    private int moodName;
    private int moodNum;

    /**
     * Mood of mood event
     *
     * @param selected_mood the mood selected for the mood event
     */
    public Mood(int selected_mood) {
        this.moodNum = selected_mood;
        switch(selected_mood) {
            case constants.HAPPY:
                this.color = R.color.color_happy;
                this.moodName = R.string.happy_mood;
                this.icon = R.drawable.happy_icon;
                break;
            // Todo: Add more moods after
        }
    }

    /**
     * get color
     *
     * @return color
     */
    public int getColor() {
        return color;
    }

    /**
     * get icon
     *
     * @return icon
     */
    public int getIcon() {
        return icon;
    }

    /**
     * get name of mood
     *
     * @return mood name
     */
    public int getMoodName() {
        return moodName;
    }

    /**
     * Get the mood number
     *
     * @return mood number
     */
    public int getMoodNum() {
        return moodNum;
    }
}


