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
 * Place this on a parameter whose value would come from CosmosDB, and causing the method to run
 * when CosmosDB data is changed. The parameter type can be one of the following:
 * </p>
 *
 * <ul>
 * <li>Some native Java types such as String</li>
 * <li>Nullable values using Optional&lt;T&gt;</li>
 * <li>Any POJO type</li>
 * </ul>
 *
 * <p>
 * The following example shows a Java function that is invoked when there are inserts or updates in
 * the specified database and collection.
 * </p>
 *
 * <pre>
 * {@literal @}FunctionName("cosmosDBMonitor")
 * public void cosmosDbLog(
 *    {@literal @}CosmosDBTrigger(name = "database",
 *                      databaseName = "ToDoList",
 *                      collectionName = "Items",
 *                      leaseCollectionName = "leases",
 *                      createLeaseCollectionIfNotExists = true,
 *                      connectionStringSetting = "AzureCosmosDBConnection") 
 *                      List&lt;Map&lt;String, String&gt;&gt; items,
 *     final ExecutionContext context
 * ) {
 *     context.getLogger().info(items.size() + " item(s) is/are inserted.");
 *     if (!items.isEmpty()) {
 *         context.getLogger().info("The ID of the first item is: " + items.get(0).get("id"));
 *     }
 * }
 * </pre>
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface CosmosDBTrigger {
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
   * <li>"": get the value as a string, and try to deserialize to actual parameter type like
   * POJO</li>
   * <li>string: always get the value as a string</li>
   * <li>binary: get the value as a binary data, and try to deserialize to actual parameter type
   * byte[]</li>
   * </ul>
   * 
   * @return The dataType which will be used by the Functions runtime.
   */
  String dataType() default "";

  /**
   * Defines the database name of the CosmosDB to which to bind.
   * 
   * @return The database name string.
   */
  String databaseName();

  /**
   * Defines the collection name of the CosmosDB to which to bind.
   * 
   * @return The collection name string.
   */
  String collectionName();

  /**
   * Defines Connection string for the service containing the lease collection.
   * 
   * @return Connection string for the lease collection.
   */
  String leaseConnectionStringSetting() default "";

  /**
   * Defines the lease collection name of the CosmosDB to which to bind.
   * 
   * @return The lease collection name string.
   */
  String leaseCollectionName() default "";

  /**
   * Defines Name of the database containing the lease collection.
   * 
   * @return Name of the database for lease collection.
   */
  String leaseDatabaseName() default "";

  /**
   * Defines whether to create a new lease collection if not exists.
   * 
   * @return configuration whether to create a new lease collection if not exists.
   */
  boolean createLeaseCollectionIfNotExists() default false;

  /**
   * defines the throughput of the created collection..
   * 
   * @return throughput
   */
  int leasesCollectionThroughput() default -1;

  /**
   * Defines a prefix to be used within a Leases collection for this Trigger. Useful when sharing
   * the same Lease collection among multiple Triggers.
   * 
   * @return LeaseCollectionPrefix
   */
  String leaseCollectionPrefix() default "";

  /**
   * Customizes the amount of milliseconds between lease checkpoints. Default is always after a
   * Function call.
   * 
   * @return checkpointInterval
   */
  int checkpointInterval() default -1;

  /**
   * Customizes the amount of documents between lease checkpoints. Default is always after a
   * Function call.
   * 
   * @return CheckpointDocumentCount
   */
  int checkpointDocumentCount() default -1;

  /**
   * Customizes the delay in milliseconds in between polling a partition for new changes on the
   * feed, after all current changes are drained. Default is 5000 (5 seconds).
   * 
   * @return feedPollDelay
   */
  int feedPollDelay() default 5000;

  /**
   * Defines the app setting name that contains the CosmosDB connection string.
   * 
   * @return The app setting name of the connection string.
   */
  String connectionStringSetting();

  /**
   * Customizes the renew interval in milliseconds for all leases for partitions currently held by
   * the Trigger. Default is 17000 (17 seconds).
   * 
   * @return renew interval in milliseconds for all leases
   */
  int leaseRenewInterval() default 17000;

  /**
   * Customizes the interval in milliseconds to kick off a task to compute if partitions are
   * distributed evenly among known host instances. Default is 13000 (13 seconds).
   * 
   * @return interval in milliseconds
   */
  int leaseAcquireInterval() default 13000;

  /**
   * Customizes the interval in milliseconds for which the lease is taken on a lease representing a
   * partition. If the lease is not renewed within this interval, it will cause it to expire and
   * ownership of the partition will move to another Trigger instance. Default is 60000 (60
   * seconds).
   * 
   * @return interval in milliseconds for which the lease is taken
   */
  int leaseExpirationInterval() default 60000;

  /**
   * Customizes the maximum amount of items received in an invocation
   * 
   * @return maximum amount of items received
   */
  int maxItemsPerInvocation() default -1;

  /**
   * Gets or sets whether change feed in the Azure Cosmos DB service should start from beginning
   * (true) or from current (false). By default it's start from current (false).
   * 
   * @return Configuration whether change feed should start from beginning
   */
  boolean startFromBeginning() default false;

  /**
   * Defines preferred locations (regions) for geo-replicated database accounts in the Azure Cosmos
   * DB service. Values should be comma-separated. example, PreferredLocations = "East US,South
   * Central US,North Europe"
   * 
   * @return preferred locations (regions) for geo-replicated database accounts
   */
  String preferredLocations() default "";
}
