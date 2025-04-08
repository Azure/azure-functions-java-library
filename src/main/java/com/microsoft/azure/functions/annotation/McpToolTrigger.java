package com.microsoft.azure.functions.annotation;
import java.lang.annotation.*;

@CustomBinding(
        direction = "in",
        name = "context",
        type = "mcpToolTrigger"
)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface McpToolTrigger {
    String toolName();
    String description() default "";
    String toolProperties() default "";
}