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
 *
 * <p>Example:</p>
 * <pre>
 * {@literal @}FunctionName("codeReview")
 * public String codeReview(
 *     {@literal @}McpPromptTrigger(name = "context", description = "Code review prompt") String context,
 *     {@literal @}McpPromptArgument(
 *         name = "code",
 *         argumentName = "code",
 *         description = "The code to review",
 *         isRequired = true
 *     ) String code,
 *     {@literal @}McpPromptArgument(
 *         name = "language",
 *         argumentName = "language",
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
     * The parameter binding name for the Azure Functions runtime.
     *
     * @return The parameter binding name
     */
    String name();

    /**
     * The name of the prompt argument as exposed in the MCP protocol.
     *
     * @return The argument name
     */
    String argumentName() default "";

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
