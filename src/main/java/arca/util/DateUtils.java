package arca.util;

import java.text.SimpleDateFormat;

public class DateUtils {
    private static final SimpleDateFormat apiDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat hours = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat ddMMyy = new SimpleDateFormat("dd-MM-yy");

    public static String formatFromAPI(final Long date){
        return apiDate.format(date);
    }
    public static String currentHours(){
        return hours.format(System.currentTimeMillis());
    }
}
