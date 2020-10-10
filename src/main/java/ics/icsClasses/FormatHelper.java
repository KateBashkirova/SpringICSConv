package ics.icsClasses;

import ics.Meeting;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Class contains format types for meeting's timezone and start/end dates
 */
public class FormatHelper {

    public static final String DATE_T_TIME_Z = "yyyyMMdd'T'hhmmss'Z'";

    public static String formatDate(String dateFormat, Date date) {
        return new SimpleDateFormat(dateFormat).format(date);
    }

    public static String formatDateWithTime(String date, String time, String timezone) {
        String formattedDate = date.replaceAll("[\\s\\-.]", "");
        String formattedTime = time.replaceAll("[\\-:.]", "") + "00";
        return timezone + ":" + formattedDate + "T" + formattedTime;
    }
    /**
     * Method of forming the string which contains offset of the user's time zone
     * @param meeting class with event parameters
     * @return String which value is offset of the user's time zone
     */
    public static String getTimezoneOffSet(Meeting meeting) {
        double offsetInHours = ZonedDateTime.now(ZoneId.of(meeting.getTimezoneID())).getOffset().getTotalSeconds() / 3600.0;
        return String.format("%+05d", (int) offsetInHours * 100);
    }
}
