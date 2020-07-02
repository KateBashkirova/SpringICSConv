/*package jcs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FormProcessingController {
    @RequestMapping(value = "/createMeeting", method = RequestMethod.POST, consumes = {"application/json"})
    public @ResponseBody Meeting createMeeting(@RequestBody Meeting jsonStr) {
        Meeting meeting = new Meeting();
        meeting.savedata(jsonStr);
        //парсим JSON строку в объект с помощью POJO класса CourseInfo
        /*Gson gson = new Gson();
        Meeting meetingInfo = gson.fromJson(jsonStr, Meeting.class);

        return location;
    }

    @GetMapping("/talk")
    @ResponseBody
    public String talk() {
        return "Something";
    }

    @GetMapping("/showResult")
    public String result() {
        return "result";
    }
} */

