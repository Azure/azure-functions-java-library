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
 * Triggers an Azure Function when an MCP client requests to read a resource.
 * <p>
 * This annotation enables Azure Functions to expose content (files, data, images)
 * as MCP resources that can be discovered and consumed by MCP-compatible clients
 * like AI assistants. The function returns the resource content as a string (text)
 * or byte[] (binary).
 * </p>
 *
 * <p>Example (text resource):</p>
 * <pre>
 * {@literal @}FunctionName("getReadme")
 * public String getReadme(
 *     {@literal @}McpResourceTrigger(
 *         name = "context",
 *         uri = "file://readme.md",
 *         resourceName = "readme",
 *         description = "Application readme file",
 *         mimeType = "text/plain"
 *     ) String context,
 *     final ExecutionContext executionContext
 * ) {
 *     return "# My Application\nThis is the readme content.";
 * }
 * </pre>
 *
 * @see McpToolTrigger
 * @since 3.2.4
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface McpResourceTrigger {

    /**
     * The binding name for the resource invocation context parameter.
     *
     * @return The parameter binding name
     */
    String name();

    /**
     * Defines how Functions runtime should treat the parameter value. Possible values are:
     * <ul>
     * <li>"": get the value as a string, and try to deserialize to actual parameter type like POJO</li>
     * <li>string: always get the value as a string</li>
     * <li>binary: get the value as a binary data, and try to deserialize to actual parameter type byte[]</li>
     * </ul>
     *
     * @return The dataType which will be used by the Functions runtime.
     */
    String dataType() default "";

    /**
     * The URI of the MCP resource (e.g., "file://readme.md").
     *
     * @return The resource URI
     */
    String uri();

    /**
     * The display name of the MCP resource.
     *
     * @return The resource name
     */
    String resourceName();

    /**
     * The MIME type of the MCP resource (e.g., "text/plain", "image/png").
     *
     * @return The MIME type, or empty string if not specified
     */
    String mimeType() default "";

    /**
     * Human-readable description of this resource.
     *
     * @return Description of the resource
     */
    String description() default "";

    /**
     * The optional size of the resource in bytes. A value of -1 indicates
     * that the size is not specified.
     *
     * @return The resource size in bytes, or -1 if not specified
     */
    long size() default -1;

    /**
     * JSON-serialized metadata for the MCP resource.
     *
     * @return JSON metadata string, or empty string if not specified
     */
    String metadata() default "";
}
