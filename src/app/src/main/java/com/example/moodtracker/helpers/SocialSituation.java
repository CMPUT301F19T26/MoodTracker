/*
 * SocialSituation
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
 */

package com.example.moodtracker.helpers;

import com.example.moodtracker.constants;

/**
 * Sets users social situation based on the type of social situation selected
 */
public class SocialSituation {
    private String social_type;

    /**
     * Determines social situation using switch and the hashMap constants
     * @param type integer type to determine social situation
     * @see com.example.moodtracker.constants
     */
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

    /**
     * Getter for social situation type
     * @return social situation type
     */
    public String getSocialType() {
        return social_type;
    }
}
