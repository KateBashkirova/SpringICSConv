package jcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

@Controller
public class FormProcessingController {
    @RequestMapping(value = "/meeting", method = RequestMethod.GET)
    public ModelAndView meeting(){
        //при загрузке страницы отобразить вьюху с формой для заполнения
        return new ModelAndView("example", "meetingForm", new Meeting());
    }

    @RequestMapping(value = "/createMeeting", method = RequestMethod.POST)
    @ResponseBody
    public String createMeeting(@ModelAttribute("meetingForm") Meeting meeting, ModelMap model) {
        model.addAttribute("location", meeting.getLocation());
        model.addAttribute("summary", meeting.getSummary());
        model.addAttribute("description", meeting.getDescription());

        return "location";
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

    //обрабатываем json
    public Meeting processJson() throws IOException {
        //читаем JSON из файла
        //TODO: пофиксить заполнение формы и генерировать json непосредственно её заполнением
        BufferedReader br = new BufferedReader(new FileReader("D://Универчик//Практика//newVersion//file//json.txt"));
        String jsonStr = br.readLine();
        br.close();

        //парсим JSON в Java объект
        ObjectMapper mapper = new ObjectMapper();
        Meeting meeting = mapper.readValue(jsonStr, Meeting.class);
        return meeting;
    }


    //подготовка информации для сборки ics файла

    //меняем формат таймзоны на понятный календарю
    public class Timezone {
        public Timezone() throws IOException {}

        Meeting meeting = processJson();

        /*
        //tzid - это "пояснительная бригада" на случай, если код таймзоны ни о чём тебе не говорит
        public String tzid() {
            String timezone = meeting.getTimezone();
            String tzid =
        }*/

        //смещение относительно UTC
        public String tzOffSet(){
            //таймзона всегда имеет вид знакчч:чч. Для tzOffSet нужно убрать :
            //FIXME: инфа о Asia/Omsk, например, лишняя, но вщ и так работает
            String tzOffSet = meeting.getTimezone().replace(":", "");
            return tzOffSet;
        }
    }








    //формирование файла
    @RequestMapping(value = "/readFile")
    @ResponseBody
    public String readFile() throws IOException {
        String response;

        Meeting meeting = processJson();

        //TODO: в название файла-встречи заложить имя клиента или другую уникальную инфу
        File meetingFile = new File("D://Универчик//Практика//newVersion//file//meeting.ics");
        //пробуем создать файл
        if (!meetingFile.createNewFile())
        {
            response = "Cannot create file";
        }
        else response = "File with your meeting has been created";

        //заполняем файл
        Timezone tz = new Timezone();

        FileWriter writer = new FileWriter(meetingFile);
        writer.write("BEGIN:VCALENDAR\n" +
                "VERSION:2.0\n" +
                "PRODID:somerandomgirl\n" +
                "CALSCALE:GREGORIAN\n" +
                "BEGIN:VTIMEZONE\n" +
                "TZID=Asia/Omsk\n" +
                "TZURL:http://tzurl.org/zoneinfo-outlook/Asia/Omsk\n" +
                "X-LIC-LOCATION:Asia/Omsk\n" +
                "BEGIN:STANDARD\n" +
                "TZOFFSETFROM:" + tz.tzOffSet() + "\n" +
                "TZOFFSETTO:" + tz.tzOffSet() + "\n" +
                "TZNAME:+06\n" +
                "DTSTART:19700101T000000\n" +
                "END:STANDARD\n" +
                "END:VTIMEZONE\n" +
                "BEGIN:VEVENT\n" +
                "DTSTAMP:20200721T144803Z\n" +
                "DTSTART;TZID=Asia/Omsk:20200721T120000\n" +
                "RRULE:FREQ=WEEKLY;BYDAY=SU,TU,TH,SA\n" +
                "DTEND;TZID=Asia/Omsk:20200721T140000\n" +
                "SUMMARY:" + meeting.getSummary() + "\n" +
                "DESCRIPTION:" + meeting.getDescription() + "\n" +
                "LOCATION:" + meeting.getLocation() + "\n" +
                "BEGIN:VALARM\n" +
                "ACTION:DISPLAY\n" +
                "DESCRIPTION:" + meeting.getDescription() +"\n" +
                "TRIGGER:-PT5M\n" + "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR\n");
        writer.close();
        return response;
    }
}

