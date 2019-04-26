package com.app.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Date utility class for generating and comparing timestamps
 */
public class DateUtil {

    /**
     * Get current timestamp of the system
     * @return
     */
    public static String getCurrentTime () {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }


    /**
     * See if time1 is newer than time2, and time difference is less than 24 hours
     * @param time1 newer time
     * @param time2 older time
     * @return
     */
    public static boolean isTimeDiffLessThanOneDay (String time1, String time2) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(false);
        boolean isLegalDiff = false;

        try {
            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);
            long diff = date1.getTime() - date2.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diff >= 0 && diffDays == 0) {
                isLegalDiff = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Illegal Time format.");
        }
        finally {
            return isLegalDiff;
        }
    }


}
