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
 * Place this on a parameter whose value would come from Azure Functions runtime. Use this
 * annotation when you want to get the value of trigger metadata, or when you defined your own
 * bindings in function.json manually.
 * </p>
 *
 * @since 1.0.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindingName {
  /**
   * Defines the trigger metadata name or binding name defined in function.json.
   * 
   * @return The trigger metadata name or binding name.
   */
  String value();
}
