package arca.util;

import java.text.SimpleDateFormat;

public class DateUtils {
    private static final SimpleDateFormat apiDate = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatFromAPI(final Long date){
        return apiDate.format(date);
    }
}
