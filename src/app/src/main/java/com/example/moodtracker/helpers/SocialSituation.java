/**
 * SocialSituation
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

package com.example.moodtracker.helpers;

import com.example.moodtracker.constants;

public class SocialSituation {
    private String social_type;

    /**
     * The social
     *
     * @param type type to determine social situation
     */
    public SocialSituation(int type) {
        switch (type) {
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
            default:
                social_type = "Alone";
                break;
        }
    }

    /**
     * Get the social situation type
     *
     * @return social situation type
     */
    public String getSocialType() {
        return social_type;
    }
}
