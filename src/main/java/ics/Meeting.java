package ics;

import ics.icsClasses.Reminder;

import java.io.Serializable;

/**
 * Class exists for storing information about the meeting
 */
public class Meeting implements Serializable {
    private String summary;
    private String location;
    private String description;
    private String timezone;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    //todo change to object Reminder with 3 attributes (on/off, Reminder value, Reminder measurement Unit)
//    private String reminderText;
    private Reminder reminder;
    private String eventStatus;

    public Meeting() {}

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimezone() { return timezone; }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Reminder getReminder() {
        if (reminder == null) {
            return Reminder.NULL_REMINDER;
        } else {
            return reminder;
        }
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }
}
