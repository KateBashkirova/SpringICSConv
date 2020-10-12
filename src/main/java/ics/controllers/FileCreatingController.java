package ics.controllers;

import ics.Meeting;
import ics.fileBuilders.MeetingBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class FileCreatingController {

    @GetMapping(value = "/createMeeting")
    public ModelAndView showMeetingForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("meeting");
        return modelAndView;
    }

    /**
     * Method calls for isc file builders methods and returns ics file as a result
     * @param meeting class with event parameters
     * @return ics file
     */
    @PostMapping(value = "/createMeeting", consumes = "application/json")
    public ResponseEntity<ByteArrayResource> createMeeting(@RequestBody Meeting meeting) {
        //build file
        MeetingBuilder builder = new MeetingBuilder();
        String fileConfig = builder
                .timezone(meeting.getTimezone(), meeting.getTimezoneOffset())
                .range(meeting.getEventStartParams(), meeting.getEventEndParams())
                .eventInfo(meeting.getSummary(), meeting.getDescription(), meeting.getLocation())
                .eventStatus(meeting.getEventStatus())
                .reminder(meeting.getReminder())
                .build();
        //return file
        ByteArrayResource bt = new ByteArrayResource(fileConfig.getBytes());
        HttpHeaders headers = getHttpHeaders(meeting.getSummary());
        return new ResponseEntity<>(bt, headers, HttpStatus.OK);
    }

    /***
     * Method sets up http headers for server's answer
     * @param filename name of returned file
     * @return headers
     */
    private HttpHeaders getHttpHeaders(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/ics"));
        headers.add("Content-Disposition", "attachment;filename=" + filename + ".ics");
        return headers;
    }
}


