package arca.util;

import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    public static final void sleep(long mili) {
        try {
            Thread.sleep(mili);
        } catch (final Exception e) {
        }
    }

    public static final void sleepOneSecond(){
        sleep(TimeUnit.SECONDS.toMillis(1));
    }
    public static final void sleepTreeSecond(){
        sleep(TimeUnit.SECONDS.toMillis(3));
    }
}