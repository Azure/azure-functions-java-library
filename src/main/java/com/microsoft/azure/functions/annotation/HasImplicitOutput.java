/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.functions.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>
 * Place this on a parameter to define a binding that need return value</p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 * <p>The following example shows a example binding that uses HasImplicitOutput</p>
 *
 * <pre>{@literal @}Retention(RetentionPolicy.RUNTIME)
 * {@literal @}Target(ElementType.PARAMETER)
 * {@literal @}HasImplicitOutput
 * public @interface HasImplicitOutputBinding {
 *     // ...
 * }</pre>
 *
 * @since 2.0.1
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface HasImplicitOutput {
    /**
     * Defines the binding metadata value, if ture support for return value, if false don't support return value.
     * Default is true
     *
     * @return The binding metadata value.
     */
    boolean value() default true;
}
