package com.app.utility;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static String getCurrentTime () {
        // get current timestamp
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

    public static String timeToString (String y, String mm, String d, String h, String mi, String s) {
        return new String("'" + y + "-" + mm + "-" + d + " " + h + ":" + mi + ":" + s);
    }

    public static String timeToString (int y, int mm, int d, int h, int mi, int s) throws Exception {
        String year = String.format("%04d", y);
        String month = String.format("%02d", mm);
        String day = String.format("%02d", d);
        String hour = String.format("%02d", h);
        String minute = String.format("%02d", mi);
        String second = String.format("%02d", s);
        String strTime = "'" + year + "-" + month + "-" + day + " "
            + hour + ":" + minute + ":" + second;

        boolean isLegalTime = true;
        try {
//            String strTime = "2019-12-10 23:59:59";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setLenient(false);
            Date date = format.parse(strTime);
        }
        catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Illegal date format.");
            isLegalTime = false;
        }
        return isLegalTime ? strTime : null;
    }
}
