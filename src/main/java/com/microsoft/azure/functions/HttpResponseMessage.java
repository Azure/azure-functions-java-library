/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
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
    HttpStatusType getStatus();

    /**
     * Returns the HTTP status code set on the HttpResponseMessage instance.
     * 
     * @return the status code set on the HttpResponseMessage instance.
     */
    default int getStatusCode() {
        return getStatus().value();
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
}
