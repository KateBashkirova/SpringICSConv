package ics;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static ics.icsClasses.FormatHelper.*;

/**
 * The main controller
 */
@Controller
public class FormProcessingController {

    /**
     * Method displays a page with a form
     * @return page with a form
     */
    @RequestMapping(value = "/createMeeting", method = RequestMethod.GET)
    public ModelAndView showMeetingForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("meeting");
        return modelAndView;
    }

    /**
     * Method processes information about event and generates ics file as a result
     * @param meeting class with event parameters
     * @return ics file
     */
    @RequestMapping(value = "/createMeeting", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity createMeeting(@RequestBody Meeting meeting) {
        //todo extract to Builder
        //сформировать и вернуть файл
        Builder builder = new Builder();
        String fileConfig = builder.buildFileConfig(getTimezoneId(meeting), getTimezoneOffSet(meeting),
                getStartDateString(meeting), getEndDateString(meeting), meeting.getSummary(), meeting.getDescription(),
                meeting.getLocation(), meeting.getEventStatus(), meeting.getReminder());

        ByteArrayResource bt = new ByteArrayResource(fileConfig.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/ics"));
        headers.add("Content-Disposition", "attachment;filename=" + meeting.getSummary() + ".ics");
        return new ResponseEntity(bt, headers, HttpStatus.OK);
    }
}


