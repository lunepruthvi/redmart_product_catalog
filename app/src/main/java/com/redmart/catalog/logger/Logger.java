package com.redmart.catalog.logger;

import android.text.TextUtils;
import android.util.Log;

public class Logger {
    public static boolean d3 = true;
    private static String D3_TAG = "D3>>";

    public static void d3(String TAG, String argsTag, String... args) {
        if (d3)
            Log.d(D3_TAG + TAG, argsTag + " -- " + TextUtils.join("//", args));
    }
}
