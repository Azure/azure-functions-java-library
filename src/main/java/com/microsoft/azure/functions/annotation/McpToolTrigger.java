package com.microsoft.azure.functions.annotation;

import java.lang.annotation.*;

/**
 * Triggers an Azure Function when invoked by the Model Context Protocol (MCP) tool system.
 * <p>
 * This annotation enables Azure Functions to be called as tools from MCP-compatible clients
 * like AI assistants. The annotated parameter receives tool invocation arguments and context.
 * </p>
 * 
 * <p>Example:</p>
 * <pre>
 * {@literal @}FunctionName("getFileContent")
 * public HttpResponseMessage getFile(
 *     {@literal @}McpToolTrigger(
 *         name = "request",
 *         description = "Reads file content",
 *         toolProperties = "[{\"propertyName\":\"filePath\",\"propertyType\":\"string\"," +
 *                         "\"description\":\"File path to read\"}]"
 *     ) String toolRequest
 * ) {
 *     // Parse and handle tool request
 * }
 * </pre>
 * 
 * @see McpToolProperty
 * @since 3.2.0
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface McpToolTrigger {

    /**
     * The binding name for the tool invocation context parameter.
     * 
     * @return The parameter binding name
     */
    String name();

    /**
     * Human-readable description of what this tool does.
     * 
     * @return Description of the tool's functionality
     */
    String description();

    /**
     * JSON array defining expected tool properties.
     * <p>
     * Each property should be a JSON object with: propertyName, propertyType, description.
     * Alternative: use {@link McpToolProperty} annotations on parameters.
     * </p>
     * 
     * <p>Example:</p>
     * <pre>
     * [{"propertyName":"fileName","propertyType":"string","description":"File to read"}]
     * </pre>
     * 
     * @return JSON array of property definitions, or empty string
     */
    String toolProperties() default "";
}