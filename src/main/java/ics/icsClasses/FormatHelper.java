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

    /**
     * Method of forming the string with event's time zone, start date and time
     * @param meeting class with event parameters
     * @return String which value is event's time zone, start date and time
     */
    public static String getStartDateString(@RequestBody Meeting meeting) {
        return timezoneId(meeting.getTimezone()) + ":" + date(meeting.getStartDate())
                + "T" + time(meeting.getStartTime());
    }

    /**
     * Method of forming the string with event's time zone, end date and time
     * @param meeting class with event parameters
     * @return String which value is event's time zone, end date and time
     */
    public static String getEndDateString(@RequestBody Meeting meeting) {
        return timezoneId(meeting.getTimezone()) + ":" + date(meeting.getEndDate())
                + "T" + time(meeting.getEndTime());
    }

    /**
     * Method of forming the string with user's time zone ID
     * @param meeting class with event parameters
     * @return String which value is user's time zone ID
     */
    public static String getTimezoneId(@RequestBody Meeting meeting) {
        return timezoneId(meeting.getTimezone());
    }

    /**
     * Method of forming the string which contains offset of the user's time zone
     * @param meeting class with event parameters
     * @return String which value is offset of the user's time zone
     */
    public static String getTimezoneOffSet(@RequestBody Meeting meeting) {
        double offsetInHours = ZonedDateTime.now(ZoneId.of(getTimezoneId(meeting))).getOffset().getTotalSeconds() / 3600.0;
        return String.format("%+05d", (int) offsetInHours * 100);
    }

    /**
     * Method removes unnecessary chars from user-entered date value, leaving only numbers
     * @param date String value of date
     * @return String which contains date value without any dividing marks
     */
    private static String date(String date) {
        return date.replaceAll("[\\-.]", "");
    }

    /**
     * Method removes unnecessary chars from user-entered time value and adds zero value of seconds
     * for correct time display
     * @param time String value of time
     * @return String which contains time value without any dividing marks
     */
    private static String time(String time) {
        return time.replaceAll("[\\-:.]", "") + "00";
    }

    /**
     * Method forms user's time zone ID by removing unnecessary chars from time zone value.
     * It's important to mention that time zone ID is <i>a name of place</i> on the map (such as America/Los_Angeles),
     * which means it contains no numbers
     * @param timezone String value of time zone
     * @return String which value is user's time zone ID
     */
    private static String timezoneId(String timezone) {
        return timezone.replaceAll("[\\s0-9:+-]", "");
    }
}
