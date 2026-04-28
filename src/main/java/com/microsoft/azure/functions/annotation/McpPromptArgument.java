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
 * Defines a strongly-typed input argument for an MCP prompt function parameter.
 * <p>
 * Alternative to using JSON format in {@link McpPromptTrigger#promptArguments()}.
 * Each annotated parameter receives a specific argument value from the prompt invocation.
 * Unlike tool properties, prompt arguments are always strings — no type schema is needed.
 * </p>
 * <p>
 * The {@code name()} value serves as both the binding parameter name and the argument name
 * exposed in the MCP protocol (the Maven plugin patches it into {@code argumentName} in
 * function.json). This follows the same convention as {@link McpToolProperty} where
 * {@code name()} is patched into {@code propertyName}.
 * </p>
 *
 * <p>Example:</p>
 * <pre>
 * {@literal @}FunctionName("codeReview")
 * public String codeReview(
 *     {@literal @}McpPromptTrigger(name = "code_review", description = "Code review prompt") String context,
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
 * @see McpPromptTrigger
 * @since 3.3.0
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface McpPromptArgument {

    /**
     * The argument name used as both the binding parameter name and the MCP protocol
     * argument identifier. The Maven plugin patches this into {@code argumentName}
     * in function.json.
     *
     * @return The argument name
     */
    String name();

    /**
     * Description of the argument's purpose and usage.
     *
     * @return Description of the argument
     */
    String description() default "";

    /**
     * Whether this argument is required for prompt invocation.
     *
     * @return true if required, false if optional
     */
    boolean isRequired() default false;
}
