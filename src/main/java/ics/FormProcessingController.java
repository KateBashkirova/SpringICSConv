package ics;

import ics.icsClasses.DateAndTime;
import ics.icsClasses.Reminder;
import ics.icsClasses.Timezone;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class FormProcessingController {
    //отображение страницы с формой
    @RequestMapping(value = "/createMeeting", method = RequestMethod.GET)
    public ModelAndView showMeetingForm()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("meeting");
        return modelAndView;
    }

    @RequestMapping(value="/createMeeting", method = RequestMethod.POST, consumes = "application/json")
    //тут как бы meeting, но на деле meeting = пришедшей jsonStr, просто Meeting показывает, что jsonStr распарсить по Meeting.class
    public ResponseEntity createMeeting(@RequestBody Meeting meeting) {
        Timezone tz = new Timezone();
        DateAndTime dateAndTime = new DateAndTime();
        ArrayList <String> currDateAndTime = dateAndTime.currentDateAndTime();
        Reminder reminder = new Reminder();

        ArrayList<String> meetingInfo = new ArrayList<>();
        meetingInfo.add("BEGIN:VCALENDAR\n" +
                        "VERSION:2.0\n" +
                        "PRODID:ktbrv\n" +
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
                        "DTSTART;TZID=" + tz.tzid(meeting.getTimezone()) + ":" + dateAndTime.Date(meeting.getStartDate())
                                        + "T" + dateAndTime.Time(meeting.getStartTime()) + "\n" +
                        "DTEND;TZID=" + tz.tzid(meeting.getTimezone()) + ":" + dateAndTime.Date(meeting.getEndDate())
                                        + "T" + dateAndTime.Time(meeting.getEndTime()) + "\n" +
                        "SUMMARY:" + meeting.getSummary() + "\n");
        if (!meeting.getDescription().trim().isEmpty()){
            meetingInfo.add("DESCRIPTION:" + meeting.getDescription() + "\n");
        }
                        //"DESCRIPTION:" + meeting.getDescription() + "\n" +
        meetingInfo.add("LOCATION:" + meeting.getLocation() + "\n");
        //проверка статуса мероприятия
        if(meeting.getEventStatus().equalsIgnoreCase("Free")){
            meetingInfo.add("TRANSP:TRANSPARENT\n");
        }
        else meetingInfo.add("TRANSP:OPAQUE\n");
        //если нужно напоминание о встрече
        if (!reminder.ReminderTime(meeting.getReminder()).equalsIgnoreCase("null"))
        {
            meetingInfo.add("BEGIN:VALARM\n" + "ACTION:DISPLAY\n" + "DESCRIPTION:reminder\n");
            /*if (!meeting.getDescription().trim().isEmpty()){
                meetingInfo.add("DESCRIPTION:" + meeting.getDescription());
            }*/
            meetingInfo.add("TRIGGER:" + reminder.ReminderTime(meeting.getReminder()) + "\n" +
                            "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR\n");
        }
        else {
            meetingInfo.add("END:VEVENT\n" + "END:VCALENDAR");
        }

        //ArrayList -> String
        String joined = String.join("", meetingInfo);

        //формирование файла и отправка на скачивание
        ByteArrayResource bt = new ByteArrayResource(joined.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/ics"));
        headers.add("Content-Disposition", "attachment;filename=" + meeting.getSummary() + ".ics");
        return new ResponseEntity(bt, headers, HttpStatus.OK);
    }
}

