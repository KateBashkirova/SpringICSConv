package ics.icsClasses;

import ics.Meeting;

import java.io.IOException;

public class Reminder {
    //подключаем классы для работы
    MeetingProcessor mt = new MeetingProcessor();
    Meeting meeting = mt.processJson();

    public Reminder() throws IOException {}

    public String ReminderTime(){
        String desiredTime = meeting.getReminder();
        return timeDefinition(desiredTime);
    }

    private String timeDefinition(String desiredTime){
        String reminderTime = "null";
        String splittedTime[] = desiredTime.split(" "); //{число}, {мера}

        //нестандартные ситуации
        if (desiredTime.equalsIgnoreCase("At time of event")) {
            reminderTime = "PT0M";
        }
        else if (desiredTime.equalsIgnoreCase("None")) reminderTime = "null";
        else {
            //убираем возможную "s" на конце слова
            if (splittedTime[1].contains("s")) splittedTime[1] = splittedTime[1].substring(0, splittedTime[1].length() - 1);
            switch(splittedTime[1]){
                case ("minute"):
                    reminderTime = "-PT" + splittedTime[0] + "M";
                    break;
                case ("hour"):
                    reminderTime = "-PT" + splittedTime[0] + "H";
                    break;
                case ("day"):
                    reminderTime = "-PT" + splittedTime[0] + "D";
                    break;
                case ("week"):
                    reminderTime = "-PT" + splittedTime[0] + "W";
                    break;
                default:
                    System.out.println("Error with time definition. Reminder will not be setted");
                    break;
            }
        }
        return reminderTime;
    }
}
