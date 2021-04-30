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
 * <p>Place this on a parameter whose value would come from a blob, and causing the method to run when a blob is
 * uploaded. The parameter type can be one of the following:</p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Nullable values using Optional&lt;T&gt;</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 *
 * <p>The following example shows a Java function that logs the filename and size when a blob is added or updated
 * in the "samples-workitems" container:</p>
 *
 * <pre>{@literal @}FunctionName("blobMonitor")
 * public void blobMonitor(
 *    {@literal @}BlobTrigger(name = "file",
 *                  dataType = "binary",
 *                  path = "samples-workitems/{name}",
 *                  connection = "AzureWebJobsStorage") byte[] content,
 *    {@literal @}BindingName("name") String filename,
 *     final ExecutionContext context
 * ) {
 *     context.getLogger().info("Name: " + filename + ", Size: " + content.length + " bytes");
 * }</pre>
 *
 * @see com.microsoft.azure.functions.annotation.BindingName
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface BlobTrigger {
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

    /**
     * Defines the parameter to specify the type of the blob trigger to use, example EventGrid for event grid blob trigger.
     * @return the parameter to specify the type of the blob trigger to use.
     */
    String source() default "";
}
