/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions.annotation;

/**
 * Azure Service Bus permission.
 *
 * @since 1.0.0
 */
public enum AccessRights {
    /**
     * Confers the right to manage the topology of the namespace, including creating and deleting entities.
     */
    MANAGE,

    /**
     * Confers the right to listen (relay) or receive (queue, subscriptions) and all related message handling.
     */
    LISTEN
}
