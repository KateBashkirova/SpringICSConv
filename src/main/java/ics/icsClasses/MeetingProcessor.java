package ics.icsClasses;

import com.fasterxml.jackson.databind.ObjectMapper;
import ics.Meeting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MeetingProcessor {
    //обрабатываем json
    public Meeting processJson() throws IOException {
        //читаем JSON из файла
        BufferedReader br = new BufferedReader(new FileReader("D://Универчик//Практика//newVersion//file//json.txt"));
        String jsonStr = br.readLine();
        br.close();

        //парсим JSON в Java объект
        ObjectMapper mapper = new ObjectMapper();
        Meeting meeting = mapper.readValue(jsonStr, Meeting.class);
        return meeting;
    }
}
