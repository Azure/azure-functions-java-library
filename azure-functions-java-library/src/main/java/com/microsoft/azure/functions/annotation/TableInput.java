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
 * <p>Place this on a parameter whose value would come from storage table.
 * The parameter type can be one of the following:</p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Nullable values using Optional&lt;T&gt;</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 * <p>The following example shows an HTTP trigger which returned the total count of the items in a table storage:</p>
 *
 * <pre>{@literal @}FunctionName("getallcount")
 * public int run(
 *    {@literal @}HttpTrigger(name = "req",
 *                  methods = {"get"},
 *                  authLevel = AuthorizationLevel.ANONYMOUS) Object dummyShouldNotBeUsed,
 *    {@literal @}TableInput(name = "items",
 *                 tableName = "mytablename",
 *                 partitionKey = "myparkey",
 *                 connection = "myconnvarname") MyItem[] items
 * ) {
 *     return items.length;
 * }</pre>
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface TableInput {
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
     * Defines the name of the storage table to which to bind.
     * @return The storage table name string.
     */
    String tableName();

    /**
     * Defines the partition key of the storage table to which to bind.
     * @return The storage table partition key string.
     */
    String partitionKey() default "";

    /**
     * Defines the row key of the storage table to which to bind.
     * @return The storage table row key string.
     */
    String rowKey() default "";

    /**
     * Defines the filter of the storage table to which to bind.
     * @return The storage table filter string.
     */
    String filter() default "";

    /**
     * Defines the number of rows to be retrieved from the storage table to which to bind.
     * @return The storage table retrieving rows number string.
     */
    String take() default "";

    /**
     * Defines the app setting name that contains the Azure Storage connection string.
     * @return The app setting name of the connection string.
     */
    String connection() default "";
}
