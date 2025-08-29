package com.microsoft.azure.functions.annotation;

import java.lang.annotation.*;

/**
 * Annotation to define a property for an MCP Tool.
 * <p>
 * This annotation is used to specify individual properties that an MCP tool expects,
 * including their type, description, and whether they are required.
 * It can be used as an alternative to the JSON string format in {@link McpToolTrigger#toolProperties()}.
 * </p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@literal @}FunctionName("myTool")
 * public void myToolFunction(
 *     {@literal @}McpToolTrigger(name = "toolArgs", toolName = "myTool") String toolArguments,
 *     {@literal @}McpToolProperty(
 *         propertyName = "fileName",
 *         propertyType = "string",
 *         description = "The name of the file to process",
 *         required = true
 *     ) String fileName
 * ) {
 *     // function implementation
 * }
 * </pre>
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface McpToolProperty {

    /**
     * The name of the tool property.
     * This should match the property name that will be passed in the tool arguments.
     *
     * @return the name of the property
     */
    String propertyName();

    /**
     * The expected type of the property (e.g., "string", "int", "boolean", "array").
     * This is used for validation and documentation purposes.
     *
     * @return the type of the property
     */
    String propertyType();

    /**
     * A description of what this property represents and how it should be used.
     * This may be used in UI or documentation to help users understand the property.
     *
     * @return the property's description
     */
    String description();

    /**
     * Indicates whether this property is required for the tool to function properly.
     * If true, the tool execution may fail if this property is not provided.
     *
     * @return true if the property is required, false otherwise (defaults to false)
     */
    boolean required() default false;
}
