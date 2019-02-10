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
 * <p>Place this on a parameter to obtain a SignalRConnectionInfo object.
 * The parameter type can be one of the following:</p>
 *
 * <ul>
 *     <li>SignalRConnectionInfo type</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface SignalRConnectionInfoInput {
    /**
     * The variable name used in function.json.
     * @return The variable name used in function.json.
     */
    String name();

    /**
     * <p>Defines how Functions runtime should treat the parameter value. Possible values are:</p>
     * <ul>
     *     <li>"": get the value as a string, and try to deserialize to actual parameter type like POJO</li>
     *     <li>string: always get the value as a string</li>
     *     <li>binary: get the value as a binary data, and try to deserialize to actual parameter type byte[]</li>
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

    /**
     * Defines the user ID to associate with the connection. Typically uses a 
     * binding expression such as {x-ms-client-principal-name} (the principal name 
     * from App Service Authentication).
     * @return The user ID.
     */
    String userId() default "";
}