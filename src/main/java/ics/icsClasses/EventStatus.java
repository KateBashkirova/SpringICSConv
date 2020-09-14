package ics.icsClasses;

public enum EventStatus {
    FREE {
        @Override
        public String getConfig() {
            return "TRANSP:TRANSPARENT\r\n";
        }
    },
    BUSY {
        @Override
        public String getConfig() {
            return null;
        }
    },
    TENTATIVE {
        @Override
        public String getConfig() {
            return null;
        }
    },
    OUT_OF_OFFICE("out of office") {
        @Override
        public String getConfig() {
            return null;
        }
    };

    private final String statusName;

    EventStatus(String statusName) {
        this.statusName = statusName;
    }

    EventStatus() {
        this.statusName = this.name().toLowerCase();
    }

    public abstract String getConfig();
}
