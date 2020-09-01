package ics.icsClasses;

import ics.Meeting;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

//меняем формат времени и даты
public class FormatHelper {

    public static final String DATE_T_TIME_Z = "yyyyMMdd'T'hhmmss'Z'";

    public static String formatDate(String dateFormat, Date date) {
        return new SimpleDateFormat(dateFormat).format(date);
    }

    public static String getStartDateString(@RequestBody Meeting meeting) {
        Timezone tz = new Timezone();
        return tz.tzid(meeting.getTimezone()) + ":" + date(meeting.getStartDate())
                + "T" + time(meeting.getStartTime());
    }

    public ArrayList<String> currentDateAndTime(){
        //дату и время будем хранить отдельно (только строки!)
        ArrayList<String> currDateAndTimeList = new ArrayList();

        LocalDate currDate = LocalDate.now(); //получаем текущую дату и время
        String icsCurrDate = String.valueOf(currDate).replaceAll("[\\-]", ""); //убираем лишние знаки

        long curTime = System.currentTimeMillis(); //текущее системное время
        String currTime = new SimpleDateFormat("hhmmss").format(curTime); //приводим к нужному формату

        //в список
        currDateAndTimeList.add(icsCurrDate);
        currDateAndTimeList.add(currTime);


        return currDateAndTimeList;
    }

    public static String date(String date){
        //приводим к виду YYYYMMDD без знаков
        return date.replaceAll("[\\-]", ""); //убираем точки;
    }

    public static String time(String time){
        //приводим к виду HHMM без знаков + добавляем SS
        return time.replaceAll(":", "") + "00";
    }
}
