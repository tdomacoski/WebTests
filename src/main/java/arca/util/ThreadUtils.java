package arca.util;

import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    public static final void sleep(long mili) {
        try {
            Thread.sleep(mili);
        } catch (final Exception e) {
        }
    }
    public static void invoqueGargageCollector(){
        synchronized (Thread.currentThread()){
            System.out.println("Clean Thread");
            System.gc();
        }
    }

    public static final void sleepOneSecond(){
        sleep(TimeUnit.SECONDS.toMillis(1));
    }
    public static final void sleepTreeSecond(){
        sleep(TimeUnit.SECONDS.toMillis(3));
    }
    public static final void sleepOneMinute(){
        sleep(TimeUnit.MINUTES.toMillis(1));
    }
    public static final void sleepTreeMinutes(){
        sleep(TimeUnit.MINUTES.toMillis(3));
    }
}