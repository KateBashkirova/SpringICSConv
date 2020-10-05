package ics.icsClasses;

/**
 * Class contains options for meeting busy status
 */
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
            return "TRANSP:TRANSPARENT\r\n" +
                    "X-MICROSOFT-CDO-BUSYSTATUS:TENTATIVE\r\n";
        }
    },
    OUT_OF_OFFICE() {
        @Override
        public String getConfig() {
            return "TRANSP:TRANSPARENT\r\n" +
                    "X-MICROSOFT-CDO-BUSYSTATUS:OOF\r\n";
        }
    };

    /**
     * Method of generating meeting's busy status depending on user's choice
     * @return String which value is meeting's busy status
     */
    public abstract String getConfig();
}
