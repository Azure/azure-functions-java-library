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
 * Triggers an Azure Function when invoked by the Model Context Protocol (MCP) prompt system.
 * <p>
 * This annotation enables Azure Functions to expose prompt templates that MCP-compatible clients
 * can discover via {@code prompts/list} and invoke via {@code prompts/get} with arguments.
 * The function returns a plain string (auto-wrapped into a single user message) or a
 * JSON-serialized {@code GetPromptResult} for multi-message or rich content responses.
 * </p>
 *
 * <p>Example:</p>
 * <pre>
 * {@literal @}FunctionName("codeReview")
 * public String codeReview(
 *     {@literal @}McpPromptTrigger(
 *         name = "code_review",
 *         description = "Generates a code review prompt"
 *     ) String context,
 *     {@literal @}McpPromptArgument(
 *         name = "code",
 *         description = "The code to review",
 *         isRequired = true
 *     ) String code,
 *     {@literal @}McpPromptArgument(
 *         name = "language",
 *         description = "The programming language"
 *     ) String language
 * ) {
 *     return "Please review the following " + language + " code:\n\n" + code;
 * }
 * </pre>
 *
 * @see McpPromptArgument
 * @see McpMetadata
 * @since 3.3.0
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface McpPromptTrigger {

    /**
     * The variable name used in function.json and also the unique prompt name
     * that MCP clients use to identify and invoke this prompt.
     * <p>
     * This serves as both the binding parameter name and the prompt identifier.
     * It must be unique across all prompts in the function app.
     * </p>
     *
     * @return The prompt name / parameter binding name
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
     * Human-readable description of what this prompt does.
     *
     * @return Description of the prompt's purpose
     */
    String description() default "";

    /**
     * Optional human-readable title for display purposes.
     *
     * @return The display title, or empty string if not specified
     */
    String title() default "";

    /**
     * JSON array defining expected prompt arguments.
     * <p>
     * Each argument should be a JSON object with: name, description, required.
     * Alternative: use {@link McpPromptArgument} annotations on parameters.
     * </p>
     *
     * <p>Example:</p>
     * <pre>
     * [{"name":"code","description":"The code to review","required":true}]
     * </pre>
     *
     * @return JSON array of argument definitions, or empty string
     */
    String promptArguments() default "";

    /**
     * JSON-serialized metadata for the MCP prompt.
     *
     * @return JSON metadata string, or empty string if not specified
     */
    String metadata() default "";

    /**
     * JSON array of icons for the MCP prompt.
     *
     * @return JSON array of icon definitions, or empty string if not specified
     */
    String icons() default "";
}
