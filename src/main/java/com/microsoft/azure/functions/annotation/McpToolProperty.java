package com.microsoft.azure.functions.annotation;

import java.lang.annotation.*;

/**
 * Defines a strongly-typed input property for an MCP tool function parameter.
 * <p>
 * Alternative to using JSON format in {@link McpToolTrigger#toolProperties()}.
 * Each annotated parameter receives a specific value from the tool invocation arguments.
 * </p>
 * 
 * <p>Example:</p>
 * <pre>
 * {@literal @}FunctionName("searchFiles")
 * public HttpResponseMessage search(
 *     {@literal @}McpToolTrigger(name = "context", description = "Search files") String context,
 *     {@literal @}McpToolProperty(
 *         name = "query",
 *         propertyName = "searchTerm",
 *         propertyType = "string",
 *         description = "Search term",
 *         required = true
 *     ) String searchTerm
 * ) {
 *     // Use searchTerm directly
 * }
 * </pre>
 * 
 * @see McpToolTrigger
 * @since 3.2.0
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface McpToolProperty {

    /**
     * The parameter binding name for the Azure Functions runtime.
     * 
     * @return The parameter binding name
     */
    String name();

    /**
     * The expected data type (e.g., "string", "number", "boolean", "array", "object").
     * 
     * @return The property type identifier
     */
    String propertyType();

    /**
     * Description of the property's purpose and usage.
     * 
     * @return Description of the property
     */
    String description();

    /**
     * Whether this property is required for tool execution.
     * 
     * @return true if required, false if optional
     */
    boolean required() default false;
}
