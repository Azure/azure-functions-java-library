/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions;

import java.net.URI;
import java.util.Map;

/**
 * An HttpRequestMessage instance is provided to Azure functions that use
 * {@link com.microsoft.azure.functions.annotation.HttpTrigger HTTP Triggers}. For an example of how to use
 * the http functionality of Azure Functions, refer to the example in the
 * {@link com.microsoft.azure.functions.annotation.HttpTrigger}
 *
 * @see com.microsoft.azure.functions.annotation.HttpTrigger
 * @see HttpResponseMessage
 * @param <T> The type of the body content that is expected to be received as part of this HTTP request.
 * @since 1.0.0
 */
public interface HttpRequestMessage<T> {
    /**
     * Returns the URI that was called that resulted in this HTTP request being submitted.
     * @return the URI that was called that resulted in this HTTP request being submitted.
     */
    URI getUri();

    /**
     * Returns the HTTP method name as Enum
     * @return type of HttpMethod 
     */
    HttpMethod getHttpMethod();

    /**
     * Returns a map of headers that were contained within this HTTP request.
     * @return a map of headers that were contained within this HTTP request.
     */
    Map<String, String> getHeaders();

    /**
     * Returns a map of query parameters that were included with this HTTP request.
     * @return a map of query parameters that were included with this HTTP request.
     */
    Map<String, String> getQueryParameters();

    /**
     * Returns any body content that was included with this HTTP request.
     * @return any body content that was included with this HTTP request.
     */
    T getBody();

    /**
     * Returns a {@link HttpResponseMessage.Builder} instance to build a HttpResponseMessage with standard HTTP status code and no response body.
     *
     * @param status The HTTP status code to return to the caller of the function.
     * @return An {@link HttpResponseMessage.Builder} instance containing the provided status and empty body.
     */
    HttpResponseMessage.Builder createResponseBuilder(HttpStatus status);

    /**
     * Returns a {@link HttpResponseMessage.Builder} instance to build a HttpResponseMessage with custome HTTP status code and no response body.
     *
     * @param status The HTTP status code to return to the caller of the function.
     * @return An {@link HttpResponseMessage.Builder} instance containing the provided status and empty body.
     */
    HttpResponseMessage.Builder createResponseBuilder(HttpStatusType status);

}
