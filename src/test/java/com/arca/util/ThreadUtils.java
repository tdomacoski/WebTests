package com.arca.util;

public class ThreadUtils {

    public static void sleep(long milis){
        try{
            Thread.sleep(milis);
        }catch (final Exception e){
            e.printStackTrace();
        }
    }
}
