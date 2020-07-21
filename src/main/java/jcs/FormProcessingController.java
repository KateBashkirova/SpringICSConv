package jcs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    //формирование файла
    @RequestMapping(value = "/readFile")
    @ResponseBody
    public String readFile() throws IOException {
        String response;

        //читаем JSON из файла
        //TODO: пофиксить заполнение формы и генерировать json непосредственно её заполнением
        BufferedReader br = new BufferedReader(new FileReader("D://Универчик//Практика//newVersion//file//json.txt"));
        String jsonStr = br.readLine();
        br.close();

        //парсим JSON в Java объект
        ObjectMapper mapper = new ObjectMapper();
        Meeting meeting = mapper.readValue(jsonStr, Meeting.class);


        /*
        //заполняем файл стандартной инфой
        String stanStr = new String(Files.readAllBytes(Paths.get("D://Универчик//Практика//newVersion//file//standartInfo.txt")));
        StringBuffer st = new StringBuffer(stanStr);
        //FIXME: он не добавляет сразу все строки
        //ищем индекс места, в которое вставим сгенерированную пользователем инфу
        String newStr = String.valueOf(st.insert(stanStr.indexOf("DTEND"), "DESCRIPTION:" + meeting.getDescription()));
        String infoStr =
                String.valueOf(st.insert(stanStr.indexOf("PRIORITY:"), "LOCATION:" + meeting.getLocation())) +
                String.valueOf(st.insert(stanStr.indexOf("TRANSP:"), "SUMMARY;LANGUAGE=ru:" + meeting.getSummary()));
        //ну или так
        String doneStr = newStr.replaceAll("\n", "");

        //собираем новый файл
        File meetingFile = new File("D://Универчик//Практика//newVersion//file//meeting.ics");
        //пробуем создать файл
        if (!meetingFile.createNewFile())
        {
            response = "Cannot create file";
        }

        FileWriter wr = new FileWriter(meetingFile);

        //записываем всё в файл
        wr.write(doneStr);
        wr.flush();
        wr.close(); */

        //TODO: в название файла-встречи заложить имя клиента или другую уникальную инфу
        File meetingFile = new File("D://Универчик//Практика//newVersion//file//meeting.ics");
        //пробуем создать файл
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
                "TZID=Asia/Omsk\n" +
                "TZURL:http://tzurl.org/zoneinfo-outlook/Asia/Omsk\n" +
                "X-LIC-LOCATION:Asia/Omsk\n" +
                "BEGIN:STANDARD\n" +
                "TZOFFSETFROM:+0600\n" +
                "TZOFFSETTO:+0600\n" +
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

