package com.microsoft.azure.functions;

/**
 * Defines an HTTP Status Type
 * 
 * @since 1.0
 */
public interface HttpStatusType {

    public int value();

    /**
     * Creates a custom (non-standard) HTTP Status code. Reason can be null.
     * @param code for HttpStatusCode
     * @return HttpStatusType 
     */
    public static HttpStatusType custom(final int code) {
        if (code <= 0) {
            throw new IllegalArgumentException("A positive integer must be provided.");
        }

        return new HttpStatusType() {
            @Override
            public int value() {
                return code;
            }
        };
    }
}
