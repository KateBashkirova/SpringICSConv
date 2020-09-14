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
            return "TRANSP:OPAQUE\r\n";
        }
    },
    TENTATIVE {
        @Override
        public String getConfig() {
            return "TRANSP:TRANSPARENT\r\nX-MICROSOFT-CDO-BUSYSTATUS:TENTATIVE\r\n";
        }
    },
    OUT_OF_OFFICE("out of office") {
        @Override
        public String getConfig() {
            return "TRANSP:TRANSPARENT\r\nX-MICROSOFT-CDO-BUSYSTATUS:OOF\r\n";
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
