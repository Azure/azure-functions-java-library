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
 * <p>Place this on a parameter whose value would come from CosmosDB, and causing the method to run when CosmosDB
 * data is changed. The parameter type can be one of the following:</p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Nullable values using Optional&lt;T&gt;</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 * <p>The following example shows a cosmos db trigger which logs the count of the returned items:</p>
 *
 * <pre>{@literal @}FunctionName("cdbprocessor")
 * public void cosmosDbProcessor(
 *    {@literal @}CosmosDBTrigger(name = "items",
 *                      databaseName = "mydbname",
 *                      collectionName = "mycollname",
 *                      leaseCollectionName = "",
 *                      connectionStringSetting = "myconnvarname") MyDataItem[] items,
 *     final ExecutionContext context
 * ) {
 *     context.getLogger().info(items.length);
 * }</pre>
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface CosmosDBTrigger {
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
     * Defines the database name of the CosmosDB to which to bind.
     * @return The database name string.
     */
    String databaseName();

    /**
     * Defines the collection name of the CosmosDB to which to bind.
     * @return The collection name string.
     */
    String collectionName();

    /**
     * Defines the lease collection name of the CosmosDB to which to bind.
     * @return The lease collection name string.
     */
    String leaseCollectionName();

    /**
     * Defines whether to create a new lease collection if not exists.
     * @return The configuration whether to create a new lease collection if not exists.
     */
    boolean createLeaseCollectionIfNotExists() default false;

    /**
     * Defines the app setting name that contains the CosmosDB connection string.
     * @return The app setting name of the connection string.
     */
    String connectionStringSetting();
}
