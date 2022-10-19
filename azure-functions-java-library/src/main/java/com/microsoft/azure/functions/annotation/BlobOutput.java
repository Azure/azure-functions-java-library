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
 * <p>Place this on a parameter whose value would be written to a blob.
 * The parameter type should be OutputBinding&lt;T&gt;, where T could be one of:</p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 * <p>The following example shows blob input and output bindings in a Java function. The function makes a copy of
 * a text blob. The function is triggered by a queue message that contains the name of the blob to copy. The new
 * blob is named {originalblobname}-Copy.</p>
 * 
 * <pre>{@literal @}FunctionName("copyTextBlob")
 *{@literal @}StorageAccount("AzureWebJobsStorage")
 *{@literal @}BlobOutput(name = "target", path = "samples-workitems/{queueTrigger}-Copy")
 * public String blobCopy(
 *    {@literal @}QueueTrigger(name = "filename",
 *                   queueName = "myqueue-items") String filename,
 *    {@literal @}BlobInput(name = "source",
 *                path = "samples-workitems/{queueTrigger}") String content
 * ) {
 *     return content;
 * }</pre>
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface BlobOutput {
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
     * Defines the path of the blob to which to write.
     * @return The blob path string.
     */
    String path();

    /**
     * Defines the app setting name that contains the Azure Storage connection string.
     * @return The app setting name of the connection string.
     */
    String connection() default "";
}
