package ics.icsClasses;

import ics.Meeting;
import java.io.IOException;

//меняем формат таймзоны на понятный календарю
public class Timezone {
    //подключаем классы для работы
    MeetingProcessor mt = new MeetingProcessor();
    Meeting meeting = mt.processJson();

    public Timezone() throws IOException {}

    //расшифровка кода таймзоны
    public String tzid(){
        //tzid - это "пояснительная бригада" на случай, если код таймзоны ни о чём тебе не говорит
        String timezone = meeting.getTimezone();
        String tzid = timezone.replaceAll("[\\s0-9:+]", ""); //удаляем все цифры, знаки и пробелы
        return tzid;
    }

    //смещение относительно UTC
    public String tzOffSet(){
        //таймзона всегда имеет вид знакчч:чч. Для tzOffSet нужно удалить всё, кроме цифр и знака
        String tzOffSet = meeting.getTimezone().replaceAll("[\\sa-zA-Z:\\/]", "");
        return tzOffSet;
    }

    //две первые цифры кода таймзоны
    public String tzName(){
        String fullTzCode = tzOffSet();
        return fullTzCode.substring(0,3); //берём только знак и первые 2 цифры
    }
}
