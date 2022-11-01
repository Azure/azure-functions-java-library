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
 * <p>Place this on a parameter whose value would be written to a storage queue.
 * The parameter type should be OutputBinding&lt;T&gt;, where T could be one of:</p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 * <p>The following example shows a Java function that creates a queue message for each HTTP request received.</p>
 * 
 * <pre>{@literal @}FunctionName("httpToQueue")
 *{@literal @}QueueOutput(name = "item", queueName = "myqueue-items", connection = "AzureWebJobsStorage")
 * public String pushToQueue(
 *    {@literal @}HttpTrigger(name = "request", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS)
 *     final String message,
 *    {@literal @}HttpOutput(name = "response") final OutputBinding&lt;String&gt; result
 * ) {
 *     result.setValue(message + " has been added.");
 *     return message;
 * }</pre>
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface QueueOutput {
    /**
     * The variable name used in function.json.
     * @return The variable name used in function.json.
     */
    String name();

    /**
     * <p>Defines how Functions runtime should treat the parameter value. Possible values are:</p>
     * <ul>
     *     <li>"" or string: treat it as a string whose value is serialized from the parameter</li>
     *     <li>binary: treat it as a binary data whose value comes from for example OutputBinding&lt;byte[]&gt;</li>
     * </ul>
     * @return The dataType which will be used by the Functions runtime.
     */
    String dataType() default "";

    /**
     * Defines the name of the storage queue to which to write.
     * @return The queue name string.
     */
    String queueName();

    /**
     * Defines the app setting name that contains the Azure Storage connection string.
     * @return The app setting name of the connection string.
     */
    String connection() default "";
}
