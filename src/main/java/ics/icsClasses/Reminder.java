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