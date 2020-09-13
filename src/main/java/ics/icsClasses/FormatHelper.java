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
        //получить смещение на текущую дату в миллисекундах (учитывает летнее время)
        int offsetInMs = tz.getOffset(calendar.get(Calendar.ERA), calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            calendar.get(Calendar.DAY_OF_WEEK), calendar.get(Calendar.MILLISECOND));
        //перевести в часы
        int offsetInHours = offsetInMs/3600000;

        //чтобы не ругался google calendar
        StringBuffer sb = new StringBuffer();
        if(offsetInHours >= 0) sb.append("+");
        String offset;
        if (Math.abs(offsetInHours) < 10){
            sb.append("0").append(offsetInHours).append("00");
            offset = sb.toString();
        }
        else offset = String.valueOf(offsetInHours).concat("00");
        return offset;
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
