/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions.annotation;

/**
 * <p>
 * Strategy of the Retry annotation. Choose 'EXPONENTIAL' if you use  the exponential back off strategy or 'FIXED'
 * if you use the fixed delay strategy. For more details, refer to https://docs.microsoft.com/en-us/azure/azure-functions/functions-bindings-error-pages?tabs=csharp#function-level-configuration .
 * </p>
 *
 * @since 1.0.0
 */
public enum RetryStrategy {
    /**
     * Defines an exponential backoff retry strategy, where the delay between retries
     * will get progressively larger, limited by the max/min specified.
     */
    EXPONENTIAL,
    /**
     * Defines a retry strategy where a fixed delay is used between retries.
     */
    FIXED;
}
