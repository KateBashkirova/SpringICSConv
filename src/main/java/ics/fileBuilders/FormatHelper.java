package ics.fileBuilders;

import ics.Meeting;
import org.springframework.validation.DefaultMessageCodesResolver;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class FormatHelper {

    public static final String DATE_T_TIME_Z = "yyyyMMdd'T'hhmmss'Z'";

    /***
     * Method formats dates by set patterns
     * @param dateFormat pattern for formatting
     * @param date date for formatting
     * @return String formatted date
     */
    public static String formatDate(String dateFormat, Date date) {
        return new SimpleDateFormat(dateFormat).format(date);
    }

    /***
     * Method formats date and time (with timezone) into one following string: timezone':'date'T'time
     * @param date date
     * @param time time
     * @param timezone timezone
     * @return String which value is timezone + date + time
     */
    public static String formatDateWithTime(String date, String time, String timezone) {
        String formattedDate = date.replaceAll("[\\s\\-.]", "");
        System.out.println(formattedDate);
        String formattedTime = time.replaceAll("[\\-:.]", "") + "00";
        return timezone + ":" + formattedDate + "T" + formattedTime;
    }
}
