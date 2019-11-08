package com.example.moodtracker.model;
import com.example.moodtracker.R;
import com.example.moodtracker.constants;

public class Mood {
    // Should not need db logic in mood
    private int color;
    private int icon;
    private int moodName;
    private int moodNum;

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

    public int getColor() {
        return color;
    }

    public int getIcon() {
        return icon;
    }

    public int getMoodName() {
        return moodName;
    }

    public int getMoodNum() {
        return moodNum;
    }
}


