package arca.util;

public class Logger {
    private static final boolean debug = true;
    private static final boolean info = true;

    public static final void debug(final String txt){
        if(debug){
            System.out.println(String.format("{D}[%s]:\n%s\n", DateUtils.currentHours(), txt));
        }
    }

//    public static final void info(final String txt){
//        if(info){
//            System.out.println(String.format("{I}[%s]: %s", DateUtils.currentHours(), txt));
//        }
//    }
}
