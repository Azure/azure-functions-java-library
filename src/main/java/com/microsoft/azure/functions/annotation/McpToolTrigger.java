package com.microsoft.azure.functions.annotation;

import java.lang.annotation.*;

/**
 * Annotation to bind a parameter to an input from the MCP Tool Trigger.
 * <p>
 * This trigger is used to fetch code snippets or perform actions based on the MCP tool's behavior.
 * When applied to a parameter, it enables the Azure Function to be triggered by the
 * custom "mcpToolTrigger" extension with the specified configuration.
 * </p>
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface McpToolTrigger {

    /**
     * The variable name used in function code for the MCP tool context.
     * This name will be used as the binding name in the function.json file.
     * If the toolName property is not specified, this name will be used as the default.
     *
     * @return The variable name used in function code for the MCP tool context.
     */
    String name();
    /**
     * The name of the tool being invoked.
     * This should match the tool identifier that the extension will use
     * to route and execute the correct functionality.
     * If toolName is not specified, the value of the 'name' property
     * will be used as the default.
     *
     * @return the name of the tool
     */
    // String toolName() default "";

    /**
     * A description of the tool or its intended function.
     * This may be used in UI or documentation to help users understand
     * what the trigger does.
     *
     * @return the tool's description
     */
    String description();

    /**
     * A JSON array string defining the properties required by the tool.
     * Each item should be an object with the following keys:
     * - propertyName: the name of the input
     * - propertyType: the expected type (e.g., string, int)
     * - description: an explanation of what the property represents
     *
     * Example:
     * <pre>
     * [{"propertyName":"snippetname","propertyType":"string","description":"The name of the snippet."}]
     * </pre>
     *
     * @return a JSON string describing tool properties
     */
    String toolProperties() default "";
}