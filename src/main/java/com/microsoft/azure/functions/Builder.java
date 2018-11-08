/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions;

/**
 * A builder to create an instance of HttpResponseMessage 
 */
public interface Builder {

    /**
     * Sets the status code to be used in the HttpResponseMessage object.
     * 
     * You can provide standard HTTP Status using enum values from {@link HttpStatus}, or you can
     * create a custom status code using {@link HttpStatusType#custom(int)}.
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
