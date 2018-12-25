package com.example.a.demo_news;

import android.util.Log;

public class L {
    private static boolean bool = true;
    static void d(String tag,String msg){
        if(bool){
            Log.d(tag,msg);
        }
    }
}
