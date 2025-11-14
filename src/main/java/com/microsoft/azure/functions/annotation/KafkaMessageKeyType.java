/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions.annotation;

/**
 * <p>
 * Specifies the data type of the message key that will be serialized/deserialized for Kafka topics.
 * This defines the supported key types for Kafka messages.
 * </p>
 *
 * @since 1.4.0
 */
public enum KafkaMessageKeyType {
  /**
   * Integer key type (32-bit signed integer)
   */
  INT,

  /**
   * Long key type (64-bit signed integer)
   */
  LONG,

  /**
   * String key type (default)
   */
  STRING,

  /**
   * Binary key type (byte array)
   */
  BINARY
}
