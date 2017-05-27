package com.quintype.autholeaderboards.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Contains common utility methods used by the different classes in the app
 * <p>
 */

public class Utilities {

    /**
     * Converts dp values to pixel values depending upon the device's displayMetrics
     *
     * @param r   app resources
     * @param val dp value to be converted
     * @return resulting pixel value
     */
    public static int dpToPx(Resources r, int val) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, r
                .getDisplayMetrics()));
    }


}
