package ics.icsClasses;

import ics.Meeting;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Class contains methods for forming event's reminder time
 */
public class Reminder {

    /**
     * Method of forming the string with event's reminder time
     * @param meeting class with event parameters
     * @return String which value is event's reminder time
     */
    public static String getReminderTime(@RequestBody Meeting meeting) {
        return reminderTime(meeting.getReminder());
    }

    /**
     * Method of generating event's reminder time depending on user's choice in correct form
     * @param reminder String value of desired reminder time
     * @return String which value is event's reminder time
     */
    private static String reminderTime(String reminder) {
        String reminderTimeConfig;

        //нестандартные ситуации
        if (reminder.equalsIgnoreCase("None")) reminderTimeConfig = "null";
        else if (reminder.equalsIgnoreCase("At time of event")) reminderTimeConfig = "PT0M\r\n";

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