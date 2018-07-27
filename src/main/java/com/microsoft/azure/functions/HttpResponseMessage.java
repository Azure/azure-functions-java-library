/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions;

/**
 * An HttpResponseMessage instance is returned by Azure Functions methods that
 * are triggered by an
 * {@link com.microsoft.azure.functions.annotation.HttpTrigger}.
 *
 * @see com.microsoft.azure.functions.annotation.HttpTrigger
 * @see HttpRequestMessage
 * @since 1.0.0
 */
public interface HttpResponseMessage {

    /**
     * Returns the status code set on the HttpResponseMessage instance.
     * 
     * @return the status code set on the HttpResponseMessage instance.
     */
    int getHttpStatus();

    /**
     * Returns a header value for the given key.
     * 
     * @param key The key for which the header value is sought.
     * @return Returns the value if the key has previously been added, or null if it
     *         has not.
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
         * @param status An HTTP status code representing the outcome of the HTTP
         *               request.
         * 
         * @return this builder
         */
        Builder status(HttpStatus status);

        /**
         * Sets the status code to be used in the HttpResponseMessage object. 
         * Custom values may be provided as long they respect the classes 
         * (1xx, 2xx, 3xx, 4xx, 5xx) in accordance to RFC 2616 Section 6.1.1.
         * 
         * @param httpStatus A valid HTTP status code representing the outcome of the HTTP request. 
         * 
         * @return this builder
         */
        Builder status(int httpStatus);

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
         * Creates an instance of HttpMessageResponse with the values configured
         * in this builder.
         * 
         * @return an HttpMessageResponse object
         */
        HttpResponseMessage build();
    }
}
