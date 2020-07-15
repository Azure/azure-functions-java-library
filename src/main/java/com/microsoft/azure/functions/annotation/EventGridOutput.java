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
 * Place this on a parameter whose value would come from EventGrid, and causing the method to run when an event is
 * arrived. The parameter type can be one of the following:</p>
 *
 * <ul>
 *     <li>Any native Java types such as String, byte[]</li>
 *     <li>Nullable values using Optional&lt;T&gt;</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 * <p>The following example shows a Java function that prints out an event and then sends an event to a
 * custom eventGrid topic:</p>
 *
 * <pre>{@literal @}FunctionName("eventGridMonitor")
 * public void logEvent(
 *    {@literal @}EventGridTrigger(name = "event") String content,
 *    {@literal @}EventGridOutput(name = "outputEvent", topicEndpointUri = "MyEventGridTopicUriSetting",
 *                                topicKeySetting = "MyEventGridTopicKeySetting")
 *                                      OutputBinding&lt;String&gt; outputEvent
 *     final ExecutionContext context
 * ) {
 *     context.getLogger().info(content);
 *     final String eventGridOutputDocument = "{\"id\": \"100\", \"eventType\":\"recordInserted\",
 *         \"subject\": \"myapp/test/java\", \"eventTime\":\"2017-08-10T21:03:07+00:00\",
 *         \"data\": {\"tag1\": \"value1\",\"tag2\":\"value2\"}, \"dataVersion\": \"1.0\"}";
 *     outputEvent.setValue(eventGridOutputDocument);
 * }</pre>
 *
 * @since 1.4.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface EventGridOutput {
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
     * Gets or sets the Topic Key setting. You can find information on getting the Key for a topic
     * here: https://docs.microsoft.com/en-us/azure/event-grid/custom-event-quickstart#send-an-event-to-your-topic
     * @return The topic key setting of the eventGrid topic.
     */
    String topicKeySetting();

    /**
     * Gets or sets the topic events endpoint URI. Eg: https://topic1.westus2-1.eventgrid.azure.net/api/events
     * This is found in the Event Grid Topic's definition. You can find information on getting the Url for a topic
     * here: https://docs.microsoft.com/en-us/azure/event-grid/custom-event-quickstart#send-an-event-to-your-topic
     * @return The topic events endpoint URI of the eventGrid topic.
     */
    String topicEndpointUri();
}
