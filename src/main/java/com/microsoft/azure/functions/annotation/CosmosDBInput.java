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
 * <p>Place this on a parameter whose value would come from CosmosDB.
 * The parameter type can be one of the following:</p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Nullable values using Optional&lt;T&gt;</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 *
 * <p>The following example shows a Java function that retrieves a single document. The function is triggered by an
 * HTTP request that uses a query string to specify the ID to look up. That ID is used to retrieve a ToDoItem document
 * from the specified database and collection.</p>
 * 
 * <pre>{@literal @}FunctionName("getItem")
 * public String cosmosDbQueryById(
 *    {@literal @}HttpTrigger(name = "req",
 *                  methods = {HttpMethod.GET},
 *                  authLevel = AuthorizationLevel.ANONYMOUS) Optional&lt;String&gt; dummy,
 *    {@literal @}CosmosDBInput(name = "database",
 *                      databaseName = "ToDoList",
 *                      collectionName = "Items",
 *                      leaseCollectionName = "",
 *                      id = "{Query.id}"
 *                      connectionStringSetting = "AzureCosmosDBConnection") Optional&lt;String&gt; item
 * ) {
 *     return item.orElse("Not found");
 * }</pre>
 * 
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CosmosDBInput {
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
     * Defines the ID of the CosmosDB to which to bind.
     * @return The ID string.
     */
    String id() default "";

    /**
     * Defines the SQL query string to which to bind.
     * @return The SQL query string.
     */
    String sqlQuery() default "";

    /**
     * Defines the app setting name that contains the CosmosDB connection string.
     * @return The app setting name of the connection string.
     */
    String connectionStringSetting();
}
