package ics;

import ics.icsClasses.FormatHelper;
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
import java.util.Date;

import static ics.icsClasses.FormatHelper.*;
import static ics.icsClasses.Reminder.*;
//import static ics.icsClasses.FormatHelper.formatDate;

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
        Reminder reminder = new Reminder();
        ArrayList<String> meetingInfo = new ArrayList<>();

        meetingInfo.add("BEGIN:VCALENDAR\r\n" +
                "VERSION:2.0\r\n" +
                "PRODID:ktbrv\r\n" +
                "CALSCALE:GREGORIAN\r\n" +
                "BEGIN:VTIMEZONE\r\n" +
                "TZID=" + getTimezoneId(meeting) + "\r\n" +
                "X-LIC-LOCATION:" + getTimezoneId(meeting) + "\r\n" +
                "BEGIN:STANDARD\r\n" +
                "TZOFFSETFROM:" + getTimezoneOffSet(meeting) + "\r\n" +
                "TZOFFSETTO:" + getTimezoneOffSet(meeting) + "\r\n" +
                "DTSTART:19700101T000000\r\n" +
                "END:STANDARD\r\n" +
                "END:VTIMEZONE\r\n" +
                "BEGIN:VEVENT\r\n" +
                "DTSTAMP:" + formatDate(DATE_T_TIME_Z, new Date()) + "\r\n" +
                "DTSTART;TZID=" + getStartDateString(meeting) + "\r\n" +
                "DTEND;TZID=" + getEndDateString(meeting) + "\r\n" +
                "SUMMARY:" + meeting.getSummary() + "\r\n");
        if (!meeting.getDescription().trim().isEmpty()){
            meetingInfo.add("DESCRIPTION:" + meeting.getDescription() + "\r\n");
        }
        meetingInfo.add("LOCATION:" + meeting.getLocation() + "\r\n");

        //проверка статуса мероприятия
        switch (meeting.getEventStatus().toLowerCase()){
            case "free":
                meetingInfo.add("TRANSP:TRANSPARENT\r\n");
                break;
            case "busy":
                meetingInfo.add("TRANSP:OPAQUE\r\n");
                break;
            case "tentative":
                meetingInfo.add("TRANSP:TRANSPARENT\r\n");
                meetingInfo.add("X-MICROSOFT-CDO-BUSYSTATUS:TENTATIVE\r\n");
                break;
            case "out of office":
                meetingInfo.add("TRANSP:TRANSPARENT\r\n");
                meetingInfo.add("X-MICROSOFT-CDO-BUSYSTATUS:OOF\r\n");
        }

        //если нужно напоминание о встрече
        if (!reminder.ReminderTime(meeting.getReminder()).equalsIgnoreCase("null"))
        {
            meetingInfo.add("BEGIN:VALARM\r\n" + "ACTION:DISPLAY\r\n");
            if (!meeting.getDescription().trim().isEmpty()){
                meetingInfo.add("DESCRIPTION:" + meeting.getDescription() + "\r\n");
            }
            else meetingInfo.add("DESCRIPTION:Reminder\r\n");
            meetingInfo.add("TRIGGER:" + reminder.ReminderTime(meeting.getReminder()) + "\r\n" + "END:VALARM\r\n");
        }
        meetingInfo.add("END:VEVENT\r\n" + "END:VCALENDAR");


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

