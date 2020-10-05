package ics;

import ics.icsClasses.EventStatus;
import ics.icsClasses.Reminder;

import java.util.Date;

import static ics.icsClasses.FormatHelper.*;

public class MeetingBuilder {


    private String timezoneID;
    private String timezoneOffset;
    private String eventStartDate;
    private String eventEndDate;
    private String summary;
    private String description;
    private String location;
    private EventStatus eventStatus;
    private Reminder reminder;

    private StringBuilder stringBuilder = new StringBuilder();

    public String build() {
        validate();
        getFileInfo();
        getTimezoneConfig();
        getEventDatesConfig(eventStartDate, eventEndDate);
        getEventInfoConfig(summary, description, location);
        getEventStatusConfig();
        getReminderConfig(reminder);
        return stringBuilder.toString();
    }

    private void validate() {
        if (timezoneID == null ||
                timezoneOffset == null ||
                eventStartDate == null ||
                eventEndDate == null ||
                summary == null ||
                description == null ||
                location == null ||
                eventStatus == null) {
            throw new IllegalArgumentException();
        }

    }

    public MeetingBuilder timezone(String timezoneID, String timezoneOffset) {
        this.timezoneID = timezoneID;
        this.timezoneOffset = timezoneOffset;
        return this;
    }

    public MeetingBuilder range(String eventStartDate, String eventEndDate) {
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        return this;
    }
    public MeetingBuilder eventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
        return this;
    }

    private void getFileInfo() {
        stringBuilder.append("BEGIN:VCALENDAR\r\n").append("VERSION:2.0\r\n").append("PRODID:companyName\r\n")
                .append("CALSCALE:GREGORIAN\r\n");
    }

    private void getTimezoneConfig() {
        stringBuilder.append("BEGIN:VTIMEZONE\r\n")
                .append("TZID=").append(timezoneID).append("\r\n")
                .append("X-LIC-LOCATION:").append(timezoneID).append("\r\n")
                .append("BEGIN:STANDARD\r\n")
                .append("TZOFFSETFROM:").append(timezoneOffset).append("\r\n")
                .append("TZOFFSETTO:").append(timezoneOffset).append("\r\n")
                .append("DTSTART:19700101T000000\r\n")
                .append("END:STANDARD\r\n")
                .append("END:VTIMEZONE\r\n");
    }

    private void getEventDatesConfig(String eventStartDate, String eventEndDate) {
        stringBuilder.append("BEGIN:VEVENT\r\n" + "DTSTAMP:").append(formatDate(DATE_T_TIME_Z, new Date()))
                .append("\r\n").append("DTSTART;TZID=").append(eventStartDate).append("\r\n").append("DTEND;TZID=")
                .append(eventEndDate).append("\r\n");
    }

    private void getEventInfoConfig(String summary, String description, String location) {
        stringBuilder.append("SUMMARY:").append(summary).append("\r\n");
        //если у мероприятия есть описание
        if (!description.trim().isEmpty()) {
            stringBuilder.append("DESCRIPTION:").append(description).append("\r\n");
        }
        stringBuilder.append("LOCATION:").append(location).append("\r\n");
    }

    private void getEventStatusConfig() {
        //добавить статус мероприятия
        stringBuilder.append(eventStatus.getConfig());
    }

    private void getReminderConfig(Reminder reminder) {
        //добавить напоминание о встрече (если нужно)
        if (reminder.isOn()) {
            stringBuilder.append("BEGIN:VALARM\r\n" + "ACTION:DISPLAY\r\n" + "TRIGGER:")
                    .append(reminder.getReminderText()).append("END:VALARM\r\n");
        }
        stringBuilder.append("END:VEVENT\r\n" + "END:VCALENDAR");
    }
}