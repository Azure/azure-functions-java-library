/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions.annotation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServiceBusTriggerTest {
    @Test
    public void queueTriggerDefaultsToHostAutoCompletion() throws NoSuchMethodException {
        assertEquals(Boolean.TRUE, ServiceBusQueueTrigger.class.getDeclaredMethod("autoCompleteMessages").getDefaultValue());
        assertTrue(queueTrigger("queueDefault").autoCompleteMessages());
    }

    @Test
    public void topicTriggerDefaultsToHostAutoCompletion() throws NoSuchMethodException {
        assertEquals(Boolean.TRUE, ServiceBusTopicTrigger.class.getDeclaredMethod("autoCompleteMessages").getDefaultValue());
        assertTrue(topicTrigger("topicDefault").autoCompleteMessages());
    }

    @Test
    public void queueTriggerCanDisableAutoCompletion() throws NoSuchMethodException {
        assertFalse(queueTrigger("queueManual").autoCompleteMessages());
    }

    @Test
    public void topicTriggerCanDisableAutoCompletion() throws NoSuchMethodException {
        assertFalse(topicTrigger("topicManual").autoCompleteMessages());
    }

    private ServiceBusQueueTrigger queueTrigger(String methodName) throws NoSuchMethodException {
        return Functions.class.getDeclaredMethod(methodName, String.class)
                .getParameters()[0].getAnnotation(ServiceBusQueueTrigger.class);
    }

    private ServiceBusTopicTrigger topicTrigger(String methodName) throws NoSuchMethodException {
        return Functions.class.getDeclaredMethod(methodName, String.class)
                .getParameters()[0].getAnnotation(ServiceBusTopicTrigger.class);
    }

    private static class Functions {
        public void queueDefault(
                @ServiceBusQueueTrigger(name = "message", queueName = "queue", connection = "ServiceBus")
                String message) {
        }

        public void queueManual(
                @ServiceBusQueueTrigger(name = "message", queueName = "queue", connection = "ServiceBus",
                        autoCompleteMessages = false)
                String message) {
        }

        public void topicDefault(
                @ServiceBusTopicTrigger(name = "message", topicName = "topic", subscriptionName = "subscription",
                        connection = "ServiceBus")
                String message) {
        }

        public void topicManual(
                @ServiceBusTopicTrigger(name = "message", topicName = "topic", subscriptionName = "subscription",
                        connection = "ServiceBus", autoCompleteMessages = false)
                String message) {
        }
    }
}
