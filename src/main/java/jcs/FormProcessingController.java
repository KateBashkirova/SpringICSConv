package jcs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FormProcessingController {
    @RequestMapping(value = "/meeting", method = RequestMethod.GET)
    public ModelAndView meeting(){
        //при загрузке страницы отобразить вьюху с формой для заполнения
        return new ModelAndView("meeting", "meeting", new Meeting());
    }

    @RequestMapping(value = "/createMeeting", method = RequestMethod.POST)
    @ResponseBody
    public String createMeeting(@ModelAttribute("meeting") Meeting meeting, ModelMap model) {
        model.addAttribute("location", meeting.getLocation());
        model.addAttribute("summary", meeting.getSummary());
        model.addAttribute("description", meeting.getDescription());

        String location = meeting.getLocation();
        //инфа о встрече сохраняется в мапе
        HashMap<Integer,Meeting> meetingInfo = new HashMap<>();

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
}

