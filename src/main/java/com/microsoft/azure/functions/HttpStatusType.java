package com.microsoft.azure.functions;

/**
 * Defines an HTTP Status Type
 * 
 * @since 1.0
 */
public interface HttpStatusType {

    public static enum StatusClass {
        INFORMATIONAL(1), SUCCESS(2), REDIRECTION(3), CLIENT_ERROR(4), SERVER_ERROR(5), OTHER(0);
        int value = 0;

        StatusClass(int _class) {
            this.value = _class;
        }

        public static StatusClass statusClassOf(int code) {
            int computed = code / 100;
            for (StatusClass sc : StatusClass.values()) {
                if (computed == sc.value) {
                    return sc;
                }
            }

            return OTHER;
        }
    }

    public int getCode();

    public String getReason();

    /**
     * Returns the class of this status code.
     * 
     * @return int value for status class.
     */
    default public StatusClass getStatusClass() {
        return StatusClass.statusClassOf(getCode());
    }

    /**
     * Creates a custom (non-standard) HTTP Status code. Reason will be null.
     */
    public static HttpStatusType custom(final int code) {
        return custom(code, null);
    }

    /**
     * Creates a custom (non-standard) HTTP Status code. Reason can be null.
     */
    public static HttpStatusType custom(final int code, final String reason) {
        if (code <= 0) {
            throw new IllegalArgumentException("A positive integer must be provided.");
        }

        return new HttpStatusType() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getReason() {
                return reason;
            }
        };
    }

}
