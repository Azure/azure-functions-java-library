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
 * <p>Output messages to Azure SignalR Service</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface SignalROutput {
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
     * Defines the app setting name that contains the Azure SignalR Service connection string.
     * @return The app setting name of the connection string.
     */
    String connectionStringSetting() default "";

    /**
     * Defines the name of the hub in Azure SignalR Service to which to connect.
     * @return The hub name.
     */
    String hubName();
}