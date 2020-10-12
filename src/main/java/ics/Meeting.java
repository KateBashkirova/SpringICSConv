package ics;

import ics.fileBuilders.EventStatus;
import ics.fileBuilders.Reminder;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static ics.fileBuilders.FormatHelper.formatDateWithTime;

public class Meeting implements Serializable {
    private String summary;
    private String location;
    private String description;
    private String timezone;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private Reminder reminder;
    private EventStatus eventStatus;

    public Meeting() {
    }

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

    public String getTimezone() {
        return timezone.replaceAll("[\\s0-9:+-]", "");
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezoneOffset() {
        double offsetInHours = ZonedDateTime.now(ZoneId.of(getTimezone())).getOffset().getTotalSeconds() / 3600.0;
        return String.format("%+05d", (int) offsetInHours * 100);
    }

    private String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    private String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    private String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    private String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventStartParams() {
        return formatDateWithTime(startDate, startTime, getTimezone());
    }

    public String getEventEndParams() {
        return formatDateWithTime(endDate, endTime, getTimezone());
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

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = EventStatus.valueOf(eventStatus.replace(" ", "_").toUpperCase());
    }
}
