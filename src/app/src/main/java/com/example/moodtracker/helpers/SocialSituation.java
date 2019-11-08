package com.example.moodtracker.helpers;

import com.example.moodtracker.constants;

public class SocialSituation {
    private String social_type;
    public SocialSituation(int type) {
        switch (type) {
            case constants.ALONE:
                social_type = "Alone";
            case constants.ONE_OTHER:
                social_type = "One Other";
                break;
            case constants.TWO_OTHER:
                social_type = "Two Others";
                break;
            case constants.SEVERAL:
                social_type = "Several People";
                break;
            case constants.CROWD:
                social_type = "Crowd";
                break;
            case constants.NONE:
                social_type = "None";
                break;
            default:
                social_type = "Nothing rn";
                break;
        }
    }
    public String getSocialType() {
        return social_type;
    }
}
