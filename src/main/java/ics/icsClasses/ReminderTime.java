package ics.icsClasses;

public enum ReminderTime {
    MINUTE {
        @Override
        public String getReminderConfig(String timeValue) {
            return "-PT" + timeValue + "M";
        }
    },
    HOUR {
        @Override
        public String getReminderConfig(String timeValue) {
            return "-PT" + timeValue + "H";
        }
    },
    DAY {
        @Override
        public String getReminderConfig(String timeValue) {
            return "-PT" + timeValue + "D";
        }
    },
    WEEK {
        @Override
        public String getReminderConfig(String timeValue) {
            return "-PT" + timeValue + "W";
        }
    };

    public abstract String getReminderConfig(String timeValue);
}
