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
 * Defines Retry Policy</p>
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retry {
    /**
     * The strategy of retry
     * @return The retry strategy
     */
    RetryStrategy strategy();
    /**
     * The maximum number of retries that will be attempted.
     * @return The maximum retry count.
     */
    int maxRetryCount();
    /**
     * The minimum delay interval.
     * @return The minimum retry delay.
     */
    String minimumInterval() default "";
    /**
     * The maximum delay interval.
     * @return The maximum retry delay.
     */
    String maximumInterval() default "";
    /**
     * The delay between retries.
     * @return The delay interval.
     */
    String delayInterval() default "";
}
