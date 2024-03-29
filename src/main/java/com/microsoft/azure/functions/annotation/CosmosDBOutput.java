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
 * Place this on a parameter whose value would be written to CosmosDB. The parameter type should be
 * OutputBinding&lt;T&gt;, where T could be one of:
 * </p>
 *
 * <ul>
 * <li>Some native Java types such as String</li>
 * <li>Any POJO type</li>
 * </ul>
 *
 * <p>
 * The following example shows a Java function that adds a document to a database, using data
 * provided in the body of an HTTP Post request.
 * </p>
 *
 * <pre>
 * {@literal @}FunctionName("addItem")
 *
 * public String cosmosDbAddItem(
 *    {@literal @}HttpTrigger(name = "request", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS)
 *     final String message,
 *    {@literal @}CosmosDBOutput(name = "database", databaseName = "ToDoList", containerName = "Items",
 *    connection = "AzureCosmosDBConnection")
 * ) {
 *     return "{ \"id\": \"" + System.currentTimeMillis() + "\", \"description\": \"" + message + "\" }";
 * }
 * </pre>
 * 
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.METHOD })
public @interface CosmosDBOutput {
  /**
   * The variable name used in function.json.
   * 
   * @return The variable name used in function.json.
   */
  String name();

  /**
   * <p>
   * Defines how Functions runtime should treat the parameter value. Possible values are:
   * </p>
   * <ul>
   * <li>"" or string: treat it as a string whose value is serialized from the parameter</li>
   * <li>binary: treat it as a binary data whose value comes from for example
   * OutputBinding&lt;byte[]&gt;</li>
   * </ul>
   * 
   * @return The dataType which will be used by the Functions runtime.
   */
  String dataType() default "";

  /**
   * Defines the database name of the CosmosDB to which to write.
   * 
   * @return The database name string.
   */
  String databaseName();

  /**
   * Defines the container name of the CosmosDB to which to write.
   * 
   * @return The container name string.
   */
  String containerName();

  /**
   * Defines the ID of the CosmosDB to which to write.
   * 
   * @return The ID string.
   */
  boolean createIfNotExists() default false;

  /**
   * Defines the app setting name that contains the CosmosDB connection string.
   * 
   * @return The app setting name of the connection string.
   */
  String connection();

  /**
   * Defines the partition key path for the created container when createIfNotExists is set to
   * true. May include binding parameters.
   * 
   * @return partitionKey of the created container.
   */
  String partitionKey() default "";

  /**
   * If CreateIfNotExists is true, defines the throughput of the created container.
   * 
   * @return Throughput of the created container.
   */
  int containerThroughput() default -1;

  /**
   * Enable to use with Multi Master accounts.
   * 
   * @return whether to Multi Master accounts
   */
  boolean useMultipleWriteLocations() default false;

  /**
   * Defines preferred locations (regions) for geo-replicated database accounts in the Azure Cosmos
   * DB service. Values should be comma-separated. example, PreferredLocations = "East US,South
   * Central US,North Europe"
   * 
   * @return PreferredLocations for geo-replicated database accounts
   */
  String preferredLocations() default "";
}
