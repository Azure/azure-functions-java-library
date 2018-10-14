/**
 * Copyright (c) Microsoft Corporation. All rights reserved. Licensed under the MIT License. See
 * License.txt in the project root for license information.
 */

package com.microsoft.azure.functions;

/**
 * An HttpResponseMessage instance is returned by Azure Functions methods that are triggered by an
 * {@link com.microsoft.azure.functions.annotation.HttpTrigger}.
 *
 * @see com.microsoft.azure.functions.annotation.HttpTrigger
 * @see HttpRequestMessage
 * @since 1.0.0
 */
public interface HttpResponseMessage {

    /**
     * Returns the HTTP status code set on the HttpResponseMessage instance.
     * 
     * @return the status code set on the HttpResponseMessage instance.
     */
    HttpStatusType getHttpStatus();

    /**
     * Returns the HTTP status code set on the HttpResponseMessage instance.
     * 
     * @return the status code set on the HttpResponseMessage instance.
     */
    default int getStatus() {
        return getHttpStatus().getCode();
    }

    /**
     * Returns a header value for the given key.
     * 
     * @param key The key for which the header value is sought.
     * @return Returns the value if the key has previously been added, or null if it has not.
     */
    String getHeader(String key);

    /**
     * Returns the body of the HTTP response.
     * 
     * @return the body of the HTTP response.
     */
    Object getBody();

    /**
     * A builder to create an instance of HttpResponseMessage
     * 
     * @author Bruno Borges
     * @since 1.0
     */
    public static interface Builder {

        /**
         * Sets the status code to be used in the HttpResponseMessage object. 
         * 
         * You can provide standard HTTP Status using enum values from {@link HttpStatus}, or 
         * you can create a custom status code using {@link HttpStatusType#custom(int, String)}.
         * 
         * @param status An HTTP status code representing the outcome of the HTTP request.
         * @return this builder
         */
        Builder status(HttpStatusType status);

        /**
         * Adds a (key, value) header to the response.
         * 
         * @param key   The key of the header value.
         * @param value The value of the header value.
         * @return this builder
         */
        Builder header(String key, String value);

        /**
         * Sets the body of the HTTP response.
         * 
         * @param body The body of the HTTP response
         * @return this builder
         */
        Builder body(Object body);

        /**
         * Creates an instance of HttpMessageResponse with the values configured in this builder.
         * 
         * @return an HttpMessageResponse object
         */
        HttpResponseMessage build();
    }
}
