package ics;

import ics.icsClasses.DateAndTime;
import ics.icsClasses.MeetingProcessor;
import ics.icsClasses.Reminder;
import ics.icsClasses.Timezone;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.ArrayList;

@Controller
public class FormProcessingController {
    //отображаем страницу с формой
    @GetMapping("/do")
    public ModelAndView doMeeting(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("meeting");
        return modelAndView;
    }

    //сообщаем об успешном формировании файла
    @PostMapping("/do")
    @ResponseBody
    public String answerMeeting(){
        return "Your meeting had been setted";
    }

    //формирование файла
    @RequestMapping(value = "/readFile")
    @ResponseBody
    public String readFile() throws IOException {
        String response;

        //подключаем классы для работы
        MeetingProcessor mt = new MeetingProcessor();
        Meeting meeting = mt.processJson();
        Timezone tz = new Timezone();
        DateAndTime dateAndTime = new DateAndTime();
        ArrayList <String> currDateAndTime = dateAndTime.currentDateAndTime();
        Reminder reminder = new Reminder();

        //пробуем создать файл
        File dir = new File("D://Универчик//Практика//newVersion//file");
        File meetingFile = new File(dir, meeting.getSummary() + ".ics");
        if (!meetingFile.createNewFile())
        {
            response = "Cannot create file";
        }
        else response = "File with your meeting has been created";

        //заполняем файл
        FileWriter writer = new FileWriter(meetingFile);
        writer.write("BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "PRODID:somerandomgirl\n" +
                "CALSCALE:GREGORIAN\n" +
                "BEGIN:VTIMEZONE\n" +
                "TZID=" + tz.tzid() + "\n" +
                "TZURL:http://tzurl.org/zoneinfo-outlook/" + tz.tzid() + "\n" +
                "X-LIC-LOCATION:" + tz.tzid() + "\n" +
                "BEGIN:STANDARD\n" +
                "TZOFFSETFROM:" + tz.tzOffSet() + "\n" +
                "TZOFFSETTO:" + tz.tzOffSet() + "\n" +
                "TZNAME:" + tz.tzName() + "\n" +
                "DTSTART:19700101T000000\n" +
                "END:STANDARD\n" +
                "END:VTIMEZONE\n" +
                "BEGIN:VEVENT\n" +
                "DTSTAMP:" + currDateAndTime.get(0) + "T" + currDateAndTime.get(1) + "Z" + "\n" +
                "DTSTART;TZID=" + tz.tzid() + ":" + dateAndTime.Date("start") + "T" + dateAndTime.Time("start") + "\n" +
                "DTEND;TZID=" + tz.tzid() + ":" + dateAndTime.Date("end") + "T" + dateAndTime.Time("end") + "\n" +
                "SUMMARY:" + meeting.getSummary() + "\n" +
                "DESCRIPTION:" + meeting.getDescription() + "\n" +
                "LOCATION:" + meeting.getLocation() + "\n");
        if(meeting.getEventStatus().equalsIgnoreCase("Out Of Office")){
            writer.write("X-MICROSOFT-CDO-BUSYSTATUS:" + "OOF" + "\n");
        }
        else writer.write("X-MICROSOFT-CDO-BUSYSTATUS:" + meeting.getEventStatus().toUpperCase() + "\n");
        //если нужно напоминание о встрече
        if (!reminder.ReminderTime().equalsIgnoreCase("null"))
        {
            writer.write("BEGIN:VALARM\n" +
                            "ACTION:DISPLAY\n" +
                            "DESCRIPTION:" + meeting.getDescription() +"\n" +
                            "TRIGGER:" + reminder.ReminderTime() + "\n" +
                            "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR\n");
        }
        else {
            writer.write("END:VEVENT\n" + "END:VCALENDAR\n");
        }
        writer.close();
        return response;
    }
}

