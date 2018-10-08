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
 * <p>Place this on a parameter whose value would come from a blob. The parameter type can be one of the following:</p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Nullable values using Optional&lt;T&gt;</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 * <p>The following example is a Java function that uses a queue trigger and an input blob binding. The queue message
 * contains the name of the blob, and the function logs the size of the blob.</p>
 * 
 * <pre>{@literal @}FunctionName("getBlobSize")
 *{@literal @}StorageAccount("AzureWebJobsStorage")
 * public void blobSize(
 *    {@literal @}QueueTrigger(name = "filename",
 *                   queueName = "myqueue-items") String filename,
 *    {@literal @}BlobInput(name = "file",
 *                dataType = "binary",
 *                path = "samples-workitems/{queueTrigger}") byte[] content,
 *     final ExecutionContext context
 * ) {
 *     context.getLogger().info("The size of \"" + filename + "\" is: " + content.length + " bytes");
 * }</pre>
 * 
 * 
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface BlobInput {
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
     * Defines the path of the blob to which to bind.
     * @return The blob path string.
     */
    String path();

    /**
     * Defines the app setting name that contains the Azure Storage connection string.
     * @return The app setting name of the connection string.
     */
    String connection() default "";
}
