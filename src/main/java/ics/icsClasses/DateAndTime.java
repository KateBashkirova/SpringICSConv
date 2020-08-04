package ics.icsClasses;

import ics.Meeting;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

//меняем формат времени и даты
public class DateAndTime {

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

    public String Date(String date){
        //приводим к виду YYYYMMDD без знаков
        return date.replaceAll("[\\-]", ""); //убираем точки;
    }

    public String Time(String time){
        //приводим к виду HHMM без знаков + добавляем SS
        return time.replaceAll(":", "") + "00";
    }
}
