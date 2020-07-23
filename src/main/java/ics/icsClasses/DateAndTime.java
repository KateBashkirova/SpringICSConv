package ics.icsClasses;

import ics.Meeting;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

//меняем формат времени и даты
public class DateAndTime {
    //подключаем классы для работы
    MeetingProcessor mt = new MeetingProcessor();
    Meeting meeting = mt.processJson();

    public DateAndTime() throws IOException {}

    public ArrayList<String> currentDateAndTime(){
        //дату и время будем хранить отдельно (только строки!)
        ArrayList<String> currDateAndTimeList = new ArrayList();

        LocalDate currDate = LocalDate.now(); //получаем текущую дату и время
        String icsCurrDate = String.valueOf(currDate).replaceAll("[\\-]", ""); //убираем лишние знаки

        long curTime = System.currentTimeMillis(); //текущее системное время
        String currTime = new SimpleDateFormat("HHMMSS").format(curTime); //приводим к нужному формату

        //в список
        currDateAndTimeList.add(icsCurrDate);
        currDateAndTimeList.add(currTime);

        return currDateAndTimeList;
    }

    public String Date(String eventStage){
        String date;

        //проверяем, какую дату запрашивают - начала или конца события
        if (eventStage.equalsIgnoreCase("start")) date = meeting.getStartDate();
        else date = meeting.getEndDate();

        //приводим к виду YYYYMMDD без знаков
        String dateWithoutDots = date.replaceAll("[\\.]", ""); //убираем точки
        String icsDate = dateWithoutDots.substring(4,8) + dateWithoutDots.substring(2,4) + dateWithoutDots.substring(0,2);

        return icsDate;
    }

    public String Time(String eventStage){
        String time;

        //проверяем, какое время запрашивают - начала или конца события
        if (eventStage.equalsIgnoreCase("start")) time = meeting.getStartTime();
        else time = meeting.getEndTime();

        //приводим к виду HHMMSS без знаков
        String icsTime = time.replaceAll(":", "");

        return icsTime;
    }
}
