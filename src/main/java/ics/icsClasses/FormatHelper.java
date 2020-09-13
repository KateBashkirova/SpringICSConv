package ics.icsClasses;

import ics.Meeting;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//меняем формат времени и даты
public class FormatHelper {

    public static final String DATE_T_TIME_Z = "yyyyMMdd'T'hhmmss'Z'";

    public static String formatDate(String dateFormat, Date date) {
        return new SimpleDateFormat(dateFormat).format(date);
    }

    public static String getStartDateString(@RequestBody Meeting meeting) {
        return timezoneId(meeting.getTimezone()) + ":" + date(meeting.getStartDate())
                + "T" + time(meeting.getStartTime());
    }

    public static String getEndDateString(@RequestBody Meeting meeting) {
        return timezoneId(meeting.getTimezone()) + ":" + date(meeting.getEndDate())
                + "T" + time(meeting.getEndTime());
    }

    public static String getTimezoneId(@RequestBody Meeting meeting){
        return timezoneId(meeting.getTimezone());
    }

    public static String getTimezoneOffSet(@RequestBody Meeting meeting){
        TimeZone tz = TimeZone.getTimeZone(getTimezoneId(meeting));
        Calendar calendar = Calendar.getInstance(tz);
        Date date = new Date();
        calendar.setTime(date); //установить на текущую дату
        int offset = tz.getOffset(calendar.get(Calendar.ERA), calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.MILLISECOND));
        String offsetInHours = String.valueOf(offset/3600000).concat("00");
        return offsetInHours;
    }

    private static String date(String date){
        //приводим к виду YYYYMMDD без знаков
        return date.replaceAll("[\\-.]", ""); //убираем точки;
    }

    private static String time(String time){
        //приводим к виду HHMM без знаков + добавляем SS
        return time.replaceAll("[\\-:.]", "") + "00";
    }

    private static String timezoneId(String timezone) {
        return timezone.replaceAll("[\\s0-9:+-]", ""); //удаляем все цифры, знаки и пробелы
    }
}
