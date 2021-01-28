/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Defines an exponential backoff retry strategy, where the delay between retries
 * will get progressively larger, limited by the max/min specified.</p>
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExponentialBackoffRetry {
    /**
     * The strategy of retries that will be used internally.
     * @return The strategy of retries.
     */
    String strategy() default "exponentialBackoff";
    /**
     * The maximum number of retries that will be attempted.
     * @return The maximum retry count.
     */
    int maxRetryCount();
    /**
     * The minimum delay interval.
     * @return The minimum retry delay.
     */
    String minimumInterval();
    /**
     * The maximum delay interval.
     * @return The maximum retry delay.
     */
    String maximumInterval();
}
