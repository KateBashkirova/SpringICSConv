package ics.icsClasses;

import ics.Meeting;
import org.springframework.web.bind.annotation.RequestBody;

public class Reminder {

    public static String getReminderTime(@RequestBody Meeting meeting) {
        return reminderTime(meeting.getReminder());
    }

    private static String reminderTime(String reminder) {
        String reminderTimeConfig;

        //нестандартные ситуации
        if (reminder.equalsIgnoreCase("None")) reminderTimeConfig = "null";
        else if (reminder.equalsIgnoreCase("At time of event")) reminderTimeConfig = "PT0M";

            //стандартные ситуации
        else {
            String[] splittedTime = reminder.split(" "); //{число}, {мера}
            String timeValue = splittedTime[0];
            String timeUnit = splittedTime[1];
            //убираем возможную "s" на конце слова
            if (timeUnit.contains("s")) timeUnit = timeUnit.substring(0, timeUnit.length() - 1);

            ReminderTime reminderTime = ReminderTime.valueOf(timeUnit.toUpperCase());
            reminderTimeConfig = reminderTime.getReminderConfig(timeValue);
        }
        return reminderTimeConfig;
    }
}