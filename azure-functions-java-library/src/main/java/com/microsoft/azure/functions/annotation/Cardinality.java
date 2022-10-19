/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions.annotation;

/**
 * <p>
 * Cardinality of the EventHubTrigger input. Choose 'ONE' if the input is a single message or 'Many'
 * if the input is an array of messages. 'Many' is the default if unspecified
 * </p>
 *
 * @since 1.0.0
 */
public enum Cardinality {
  /**
   * To receive a single message, set cardinality to ONE
   */
  ONE,

  /**
   * To receive events in a batch, set cardinality to MANY. This is the default if omitted.
   */
  MANY
}
