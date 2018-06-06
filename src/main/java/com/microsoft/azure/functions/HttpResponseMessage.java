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
 * @param <T> The type of the body, as determined by the return type specified
 *        on the function itself.
 * @since 1.0.0
 */
public interface HttpResponseMessage<T> {

    /**
     * Returns the status code set on the HttpResponseMessage instance.
     * 
     * @return the status code set on the HttpResponseMessage instance.
     */
    HttpStatus getStatus();

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
    T getBody();

    /**
     * A builder to create an instance of HttpResponseMessage
     * 
     * @author Bruno Borges
     * @since 1.0
     */
    public static interface Builder<T> {

        /**
         * Sets the status code to be used in the HttpResponseMessage object.
         * 
         * @param status An HTTP status code representing the outcome of the HTTP
         *               request.
         * 
         * @return this builder
         */
        Builder<T> status(HttpStatus status);

        /**
         * Adds a (key, value) header to the response.
         * 
         * @param key   The key of the header value.
         * @param value The value of the header value.
         * @return this builder
         */
        Builder<T> header(String key, String value);

        /**
         * Sets the body of the HTTP response.
         * 
         * @param body The body of the HTTP response
         * @return this builder
         */
        Builder<T> body(T body);

        /**
         * Creates an instance of HttpMessageResponse with the values configured
         * in this builder.
         * 
         * @return an HttpMessageResponse object
         */
        HttpResponseMessage<T> build();
    }
}
