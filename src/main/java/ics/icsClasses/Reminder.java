package ics.icsClasses;

import ics.Meeting;
import org.springframework.web.bind.annotation.RequestBody;

public class Reminder {

    public static String getReminderTime(@RequestBody Meeting meeting) {
        return reminderTime(meeting.getReminder());
    }

    private static String reminderTime(String reminder) {
        String reminderTime = "null";

        //нестандартные ситуации
        if (reminder.equalsIgnoreCase("None")) reminderTime = "null";
        else if (reminder.equalsIgnoreCase("At time of event")) reminderTime = "PT0M";

        //стандартные ситуации
        else {
            String[] splittedTime = reminder.split(" "); //{число}, {мера}
            String timeValue = splittedTime[0];
            String timeUnit = splittedTime[1];
            //убираем возможную "s" на конце слова
            if (timeUnit.contains("s")) timeUnit = timeUnit.substring(0, timeUnit.length() - 1);
            switch (timeUnit) {
                case ("minute"):
                    reminderTime = "-PT" + timeValue + "M";
                    break;
                case ("hour"):
                    reminderTime = "-PT" + timeValue + "H";
                    break;
                case ("day"):
                    reminderTime = "-PT" + timeValue + "D";
                    break;
                case ("week"):
                    reminderTime = "-PT" + timeValue + "W";
                    break;
                default:
                    System.out.println("Error with time definition. Reminder will not be setted");
                    break;
            }
        }
        return reminderTime;
    }
}



















  /*  public String ReminderTime(String reminderTime){
        return timeDefinition(reminderTime);
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
    }*/
