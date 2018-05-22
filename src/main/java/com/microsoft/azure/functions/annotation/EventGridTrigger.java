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
 * <p>The following example shows an event grid trigger which prints out the object:</p>
 *
 * <pre>{@literal @}FunctionName("egprocessor")
 * public void eventGridProcessor(
 *    {@literal @}EventGridTrigger(name = "obj") MyModel obj,
 *     final ExecutionContext context
 * ) {
 *     context.getLogger().info(obj.toString());
 * }</pre>
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface EventGridTrigger {
    String name();

    String dataType() default "";
}
