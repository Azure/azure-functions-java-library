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
 * Place this on a parameter to define a custom binding</p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Nullable values using Optional&lt;T&gt;</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 * <p>The following example shows a Java function that uses a customBinding:</p>
 *
 * <pre>{@literal @}FunctionName("CustomBindingTriggerSample")
 * public void logCustomTriggerInput(
 *    {@literal @}CustomBinding(direction = "in", name = "inputParameterName", type = "customBindingTrigger") String customTriggerInput
 *    final ExecutionContext context
 * ) {
 *     context.getLogger().info(customTriggerInput);
 * }</pre>
 *
 * @since 1.0.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CustomBinding {
  /**
   * The variable name used in function.json.
   * @return The variable name used in function.json.
   */
  String name();
  
  /**
   * The variable name used in function.json to specify the type of the binding.
   * @return The type of the binding.
   */
  String type();
  
  /**
   * The variable name used in function.json to specify the direction of the binding: in or out
   * @return The direction of the biding.
   */
  String direction();
}
