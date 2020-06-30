package arca.util;

public class ThreadUtils {
    public static final void sleep(long mili) {
        try {
            Thread.sleep(mili);
        } catch (final Exception e) {
        }
    }
}