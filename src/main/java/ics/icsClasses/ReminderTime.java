package ics.icsClasses;

/**
 * Class contains options for meeting reminder time
 */
public enum ReminderTime {
    MINUTE {
        @Override
        public String getReminderConfig(String timeValue) { return "-PT" + timeValue + "M\r\n"; }
    },
    HOUR {
        @Override
        public String getReminderConfig(String timeValue) {
            return "-PT" + timeValue + "H\r\n";
        }
    },
    DAY {
        @Override
        public String getReminderConfig(String timeValue) {
            return "-PT" + timeValue + "D\r\n";
        }
    },
    WEEK {
        @Override
        public String getReminderConfig(String timeValue) {
            return "-PT" + timeValue + "W\r\n";
        }
    };

    /**
     * Method of generating meeting's reminder time depending on user's choice
     * @param timeValue String value of numeric part of the reminder time
     * @return String which value is event's reminder time
     */
    public abstract String getReminderConfig(String timeValue);
}
