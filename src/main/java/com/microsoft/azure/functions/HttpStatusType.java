/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions;

/**
 * Defines an HTTP Status Type
 * 
 * @since 1.0
 */
public interface HttpStatusType {

    int value();

    /**
     * Creates a custom (non-standard) HTTP Status code. 
     * @param code for HttpStatusCode
     * @return HttpStatusType 
     */
    static HttpStatusType custom(final int code) {
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
