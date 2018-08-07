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
 * <p>Place this on a parameter whose value would come from event hub, and causing the method to run when a new event is
 * arrived. The parameter type can be one of the following:</p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Nullable values using Optional&lt;T&gt;</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 * <p>The following example shows a Java function that logs the message body of the event hub trigger:</p>
 *
 * <pre>{@literal @}FunctionName("eventHubMonitor")
 * public void logEventHubMessage(
 *    {@literal @}EventHubTrigger(name = "event", eventHubName = "samples-workitems", connection = "AzureEventHubConnection") String message,
 *     final ExecutionContext context
 * ) {
 *     context.getLogger().info("Event hub message received: " + message);
 * }</pre>
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface EventHubTrigger {
    /**
     * The variable name used in function.json.
     * @return The variable name used in function.json.
     */
    String name();

    /**
     * <p>Defines how Functions runtime should treat the parameter value. Possible values are:</p>
     * <ul>
     *     <li>"": get the value as a string, and try to deserialize to actual parameter type like POJO</li>
     *     <li>string: always get the value as a string</li>
     *     <li>binary: get the value as a binary data, and try to deserialize to actual parameter type byte[]</li>
     * </ul>
     * @return The dataType which will be used by the Functions runtime.
     */
    String dataType() default "";

    /**
     * Defines the name of the event hub to which to bind.
     * @return The event hub name string.
     */
    String eventHubName();

    /**
     * Cardinality of the trigger input. Choose 'One' if the input is a single message or 'Many' if the input is an array of messages. 'Many' is the default if unspecified
     * @return An {@link Cardinality} value representing the Cardinality
     */
    Cardinality cardinality() default Cardinality.MANY;


    /**
     * Defines the consumer group of the event hub to which to bind.
     * @return The event hub consumer group string.
     */
    String consumerGroup() default "$Default";

    /**
     * Defines the app setting name that contains the Azure Eventhub connection string.
     * @return The app setting name of the connection string.
     */
    String connection();
}
