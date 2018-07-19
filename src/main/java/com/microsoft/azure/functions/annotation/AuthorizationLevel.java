/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions.annotation;

/**
 * <p>Azure HTTP authorization level, Determines what keys, if any, need to be present on the request in order to
 * invoke the function.</p>
 *
 * @since 1.0.0
 */
public enum AuthorizationLevel {
    /**
     * No API key is required.
     */
    ANONYMOUS,

    /**
     * A function-specific API key is required. This is the default value if none is provided.
     */
    FUNCTION,

    /**
     * The master key is required.
     */
    ADMIN
}
