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
 * Place this on a parameter whose value would come from a Service Bus queue, and causing the method
 * to run when a new item is pushed. The parameter type can be one of the following:
 * </p>
 *
 * <ul>
 * <li>Any native Java types such as int, String, byte[]</li>
 * <li>Nullable values using Optional&lt;T&gt;</li>
 * <li>Any POJO type</li>
 * </ul>
 *
 * <p>
 * The following example shows a Java function that logs a Service Bus queue message:
 * </p>
 *
 * <pre>
 * {@literal @}FunctionName("serviceBusMonitor")
 * public void logServiceBusMessage(
 *    {@literal @}ServiceBusQueueTrigger(name = "msg", queueName = "myqueue", connection = "AzureServiceBusConnection") 
 *     final String message,
 *     final ExecutionContext context
 * ) {
 *     context.getLogger().info("Message is received: " + message);
 * }
 * </pre>
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ServiceBusQueueTrigger {
  /**
   * The variable name used in function.json.
   * 
   * @return The variable name used in function.json.
   */
  String name();

  /**
   * <p>
   * Defines how Functions runtime should treat the parameter value. Possible values are:
   * </p>
   * <ul>
   * <li>"": get the value as a string, and try to deserialize to actual parameter type like
   * POJO</li>
   * <li>string: always get the value as a string</li>
   * <li>binary: get the value as a binary data, and try to deserialize to actual parameter type
   * byte[]</li>
   * </ul>
   * 
   * @return The dataType which will be used by the Functions runtime.
   */
  String dataType() default "";

  /**
   * Defines the name of the Service Bus queue to which to bind.
   * 
   * @return The Service Bus queue string.
   */
  String queueName();

  /**
   * Defines the app setting name that contains the Service Bus connection string.
   * 
   * @return The app setting name of the connection string.
   */
  String connection();

  /**
   * Defines the permission of the Service Bus queue to which to bind.
   * 
   * @return The Service Bus queue permission.
   */
  AccessRights access() default AccessRights.MANAGE;

  /**
   * Defines the value indicating whether the sessions are enabled.
   * @return The value indicating whether the sessions are enabled.
   */
  boolean isSessionsEnabled() default false;

  /**
   * Cardinality of the trigger input.
   * Choose 'One' if the input is a single message or 'Many' if the input is an array of messages.
   * 'ONE' is the default if unspecified
   * @return An {@link Cardinality} value representing the Cardinality
   */
  Cardinality cardinality() default Cardinality.ONE;
}
