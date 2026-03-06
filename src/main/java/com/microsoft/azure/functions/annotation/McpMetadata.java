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
 * Attaches arbitrary JSON metadata to an MCP trigger (tool or resource).
 * <p>
 * This is a declarative annotation placed on the <b>same parameter</b> as the trigger
 * annotation ({@link McpToolTrigger} or {@link McpResourceTrigger}). The metadata is surfaced
 * in the MCP protocol's {@code _meta} field when clients call {@code tools/list} or
 * {@code resources/list}.
 * </p>
 * <p>
 * The {@code json} property must contain valid JSON. It can include any arbitrary key-value
 * pairs such as author information, version numbers, UI hints, tags, or nested objects.
 * </p>
 *
 * <p>Example with a resource trigger:</p>
 * <pre>
 * {@literal @}FunctionName("getReadme")
 * public String getReadme(
 *     {@literal @}McpResourceTrigger(
 *         name = "context",
 *         uri = "file://readme.md",
 *         resourceName = "readme",
 *         description = "Application readme file",
 *         mimeType = "text/plain"
 *     )
 *     {@literal @}McpMetadata(
 *         name = "context",
 *         json = "{\"author\": \"John Doe\", \"version\": 1.0}"
 *     ) String context,
 *     final ExecutionContext executionContext
 * ) {
 *     return "# My Application";
 * }
 * </pre>
 *
 * <p>Example with a tool trigger:</p>
 * <pre>
 * {@literal @}FunctionName("getWeather")
 * public String getWeather(
 *     {@literal @}McpToolTrigger(
 *         name = "context",
 *         description = "Returns weather information"
 *     )
 *     {@literal @}McpMetadata(
 *         name = "context",
 *         json = "{\"version\": 1.0, \"author\": \"Jane Doe\"}"
 *     ) String context
 * ) {
 *     return "Sunny, 72°F";
 * }
 * </pre>
 *
 * @see McpToolTrigger
 * @see McpResourceTrigger
 * @since 3.2.4
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface McpMetadata {

    /**
     * The binding parameter name. Should match the {@code name()} of the trigger
     * annotation on the same parameter.
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
     * The metadata as a JSON string.
     * <p>
     * Must contain valid JSON. The contents are surfaced in the MCP protocol's
     * {@code _meta} field when clients discover available tools or resources.
     * </p>
     *
     * <p>Example:</p>
     * <pre>
     * json = "{\"author\": \"John Doe\", \"version\": 1.0, \"tags\": [\"utility\", \"time\"]}"
     * </pre>
     *
     * @return The metadata JSON string
     */
    String json();
}
