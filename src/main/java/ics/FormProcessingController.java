package ics;

import com.fasterxml.jackson.databind.ObjectMapper;
import ics.icsClasses.DateAndTime;
import ics.icsClasses.MeetingProcessor;
import ics.icsClasses.Reminder;
import ics.icsClasses.Timezone;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

@RestController
public class FormProcessingController {
    //статические ресурсы??
    @RequestMapping(value = "/staticResourceTest")
    public String staticResource(Model model) {
        return "staticResourcesTest";
    }

    //отображаем страницу с формой
    @GetMapping("/formView")
    public ModelAndView doMeeting(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("meeting");
        return modelAndView;
    }

    @RequestMapping(value = "/createMeeting", method = RequestMethod.GET)
    public ModelAndView showMeetingForm()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("meeting");
        return modelAndView;
    }

  /*  //формирование файла
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
    } */

    //FIXME: error 415 при том, что meeting благополучно парсит
    @RequestMapping(value="/createMeeting", method = RequestMethod.POST, consumes = "application/json", produces = "plain/text")
    //тут как бы meeting, но на деле meeting = пришедшей jsonStr, просто Meeting показывает, что jsonStr распарсить по Meeting.class
    public void createMeeting(@RequestBody Meeting meeting) throws IOException {
        System.out.println(meeting.getSummary());
    }

    //формируем и отдаём файл "на ходу"
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity download() throws IOException {
        String meetingInfo = "BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "PRODID:betterNotToKnow\n" +
                "CALSCALE:GREGORIAN\n" +
                "BEGIN:VTIMEZONE\n" +
                "TZID:Asia/Singapore\n" +
                "TZURL:http://tzurl.org/zoneinfo-outlook/Asia/Singapore\n" +
                "X-LIC-LOCATION:Asia/Singapore\n" +
                "BEGIN:STANDARD\n" +
                "TZOFFSETFROM:+0800\n" +
                "TZOFFSETTO:+0800\n" +
                "TZNAME:+08\n" +
                "DTSTART:19700101T000000\n" +
                "END:STANDARD\n" +
                "END:VTIMEZONE\n" +
                "BEGIN:VEVENT\n" +
                "DTSTAMP:20200726T192050Z\n" +
                "UID:20200726T192050Z-1431052642@marudot.com\n" +
                "DTSTART;TZID=Asia/Singapore:20200713T120000\n" +
                "DTEND;TZID=Asia/Singapore:20200713T120000\n" +
                "SUMMARY:Some meeting\n" +
                "LOCATION:Omsk\n" +
                "X-MICROSOFT-CDO-BUSYSTATUS:OOF\n" +
                "END:VEVENT\n" +
                "END:VCALENDAR";
       // InputStream is = new ByteArrayInputStream(meetingInfo.getBytes()); //преобразуем строку в поток ввода
        //InputStreamResource isResource = new InputStreamResource(is);
        ByteArrayResource bt = new ByteArrayResource(meetingInfo.getBytes()); //предпочтительней, судя по spring.io
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/ics"));
        headers.add("Content-Disposition", "attachment;filename=meeting.ics");
        return new ResponseEntity(bt, headers, HttpStatus.OK);
    }

       /* Timezone tz = new Timezone();
        DateAndTime dateAndTime = new DateAndTime();
        ArrayList <String> currDateAndTime = dateAndTime.currentDateAndTime();
        Reminder reminder = new Reminder();

        //формирование файла "на лету"
        Document icsDoc = new Document();
        try{
            icsDoc.open();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("application/ics"));
            headers.add("Content-Disposition", "attachment;filename=" + meeting.getSummary()+".ics");
            icsDoc.add(new Paragraph("Hello World!"));
            OutputStream os = headers.getOutputStream();

        } catch (DocumentException e) {
            e.printStackTrace();
        }


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
                "TZID=" + tz.tzid(meeting.getTimezone()) + "\n" +
                "TZURL:http://tzurl.org/zoneinfo-outlook/" + tz.tzid(meeting.getTimezone()) + "\n" +
                "X-LIC-LOCATION:" + tz.tzid(meeting.getTimezone()) + "\n" +
                "BEGIN:STANDARD\n" +
                "TZOFFSETFROM:" + tz.tzOffSet(meeting.getTimezone()) + "\n" +
                "TZOFFSETTO:" + tz.tzOffSet(meeting.getTimezone()) + "\n" +
                "TZNAME:" + tz.tzName(meeting.getTimezone()) + "\n" +
                "DTSTART:19700101T000000\n" +
                "END:STANDARD\n" +
                "END:VTIMEZONE\n" +
                "BEGIN:VEVENT\n" +
                "DTSTAMP:" + currDateAndTime.get(0) + "T" + currDateAndTime.get(1) + "Z" + "\n" +
                "DTSTART;TZID=" + tz.tzid(meeting.getTimezone()) + ":" + dateAndTime.Date(meeting.getStartDate()) + "T" + dateAndTime.Time(meeting.getStartTime()) + "\n" +
                "DTEND;TZID=" + tz.tzid(meeting.getTimezone()) + ":" + dateAndTime.Date(meeting.getEndDate()) + "T" + dateAndTime.Time(meeting.getEndTime()) + "\n" +
                "SUMMARY:" + meeting.getSummary() + "\n" +
                "DESCRIPTION:" + meeting.getDescription() + "\n" +
                "LOCATION:" + meeting.getLocation() + "\n");
        if(meeting.getEventStatus().equalsIgnoreCase("Out Of Office")){
            writer.write("X-MICROSOFT-CDO-BUSYSTATUS:" + "OOF" + "\n");
        }
        else writer.write("X-MICROSOFT-CDO-BUSYSTATUS:" + meeting.getEventStatus().toUpperCase() + "\n");
        //если нужно напоминание о встрече
        if (!reminder.ReminderTime(meeting.getReminder()).equalsIgnoreCase("null"))
        {
            writer.write("BEGIN:VALARM\n" +
                    "ACTION:DISPLAY\n" +
                    "DESCRIPTION:" + meeting.getDescription() +"\n" +
                    "TRIGGER:" + reminder.ReminderTime(meeting.getReminder()) + "\n" +
                    "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR\n");
        }
        else {
            writer.write("END:VEVENT\n" + "END:VCALENDAR\n");
        }
        writer.close();
        System.out.println(tz.tzid(meeting.getTimezone()));
        System.out.println(meeting.getSummary());
    } */
}

