package ics;

import ics.icsClasses.EventStatus;
import ics.icsClasses.Reminder;

import java.util.Date;

import static ics.icsClasses.FormatHelper.*;

public class MeetingBuilder {

    private String timezoneID;
    private String timezoneOffset;
    private String eventStartParams;
    private String eventEndParams;
    private String summary;
    private String description;
    private String location;
    private EventStatus eventStatus;
    private Reminder reminder;

    private StringBuilder stringBuilder = new StringBuilder();
    final String delimiter = "\r\n";

    public String build() {
        validate();
        getFileInfo();
        getTimezoneConfig();
        getRangeConfig();
        getEventInfoConfig();
        getEventStatusConfig();
        getReminderConfig();
        return stringBuilder.toString();
    }

    private void validate() {
        if (timezoneID == null ||
                timezoneOffset == null ||
                eventStartParams == null ||
                eventEndParams == null ||
                summary == null ||
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

    public MeetingBuilder range(String eventStartParams, String eventEndParams) {
        this.eventStartParams = eventStartParams;
        this.eventEndParams = eventEndParams;
        return this;
    }

    public MeetingBuilder eventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
        return this;
    }

    public MeetingBuilder eventInfo(String summary, String description, String location) {
        this.summary = summary;
        this.description = description;
        this.location = location;
        return this;
    }

    public MeetingBuilder reminder(Reminder reminder) {
        this.reminder = reminder;
        return this;
    }

    private void getFileInfo() {
        stringBuilder.append("BEGIN:VCALENDAR").append(delimiter)
                .append("VERSION:2.0").append(delimiter)
                .append("PRODID:companyName").append(delimiter)
                .append("CALSCALE:GREGORIAN").append(delimiter);
    }

    private void getTimezoneConfig() {
        stringBuilder.append("BEGIN:VTIMEZONE").append(delimiter)
                .append("TZID=").append(timezoneID).append(delimiter)
                .append("X-LIC-LOCATION:").append(timezoneID).append(delimiter)
                .append("BEGIN:STANDARD").append("\r\n")
                .append("TZOFFSETFROM:").append(timezoneOffset).append(delimiter)
                .append("TZOFFSETTO:").append(timezoneOffset).append(delimiter)
                .append("DTSTART:19700101T000000").append(delimiter)
                .append("END:STANDARD").append(delimiter)
                .append("END:VTIMEZONE").append(delimiter);
    }

    private void getRangeConfig() {
        stringBuilder.append("BEGIN:VEVENT").append(delimiter)
                .append("DTSTAMP:").append(formatDate(DATE_T_TIME_Z, new Date())).append(delimiter)
                .append("DTSTART;TZID=").append(eventStartParams).append(delimiter)
                .append("DTEND;TZID=").append(eventEndParams).append(delimiter);
    }

    private void getEventInfoConfig() {
        stringBuilder.append("SUMMARY:").append(summary).append(delimiter);
        //если у мероприятия есть описание
        if (!description.trim().isEmpty()) {
            stringBuilder.append("DESCRIPTION:").append(description).append(delimiter);
        }
        stringBuilder.append("LOCATION:").append(location).append(delimiter);
    }

    private void getEventStatusConfig() {
        stringBuilder.append(eventStatus.getConfig());
    }

    private void getReminderConfig() {
        //добавить напоминание о встрече (если нужно)
        if (reminder.isOn()) {
            stringBuilder.append("BEGIN:VALARM").append(delimiter)
                    .append("ACTION:DISPLAY").append(delimiter)
                    .append("TRIGGER:").append(reminder.getReminderText())
                    .append("END:VALARM").append(delimiter);
        }
        stringBuilder.append("END:VEVENT").append(delimiter)
                    .append("END:VCALENDAR");
    }
}
