package ics.fileBuilders;

/**
 * Class contains methods for forming event's reminder time
 */
public class Reminder {

    public static final Reminder NULL_REMINDER = new Reminder() {
        @Override
        public boolean isOn() {
            return false;
        }
    };

    private int value;
    private String measurementUnit;

    public Reminder() {
    }

    public boolean isOn() {
        return true;
    }

    public String getReminderText() {
        return "-PT" + value + measurementUnit + "\r\n";
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }
}