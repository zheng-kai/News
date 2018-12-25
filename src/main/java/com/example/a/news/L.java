package com.example.a.news;

import android.util.Log;

public class L {
    private static boolean b = true;

    public static void d(String tag, String msg) {
        if (b)
            Log.d(tag, msg);
    }
}
