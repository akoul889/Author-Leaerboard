package com.quintype.autholeaderboards.utils;

import android.content.res.Resources;
import android.util.TypedValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import timber.log.Timber;

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


    public static String buildImageUrlWH(String imageKey, int width, int height, String
            emptyMessage, boolean isGif, boolean useEntropy,String baseUrl) {
        try {
            if(imageKey != null) {
                if(isGif) {
                    return String.format(Locale.ENGLISH, "http://%s/%s?w=%d&h=%d&fit=crop&fm=gif&q=40", new Object[]{baseUrl, URLEncoder.encode(imageKey, "UTF-8"), Integer.valueOf((int)Math.min((double)width, 200.0D)), Integer.valueOf((int)Math.min((double)height, 200.0D))});
                } else {
                    String e = String.format(Locale.ENGLISH,
                            "http://%s/%s?w=%d&h=%d&q=%d&fm=webp&fit=crop", new Object[]{baseUrl,
                                    URLEncoder.encode(imageKey, "UTF-8"), Integer.valueOf(width), Integer.valueOf(height), Integer.valueOf(40)});
                    if(useEntropy) {
                        e = e + "&crop=entropy";
                    }

                    return e;
                }
            } else {
                Timber.e("Hero image key is null for story  %s", new Object[]{emptyMessage});
                return "qs://noImage";
            }
        } catch (UnsupportedEncodingException var8) {
            Timber.e(var8, "Image url building failed for %s", new Object[]{imageKey});
            return "qs://noImage";
        }
    }

}
