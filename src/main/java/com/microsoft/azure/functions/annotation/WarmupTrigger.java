package com.microsoft.azure.functions.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p> The warmup trigger lets you define a function that's run when a new instance of your function app is started.
 * You can use a warmup trigger to pre-load custom dependencies during the pre-warming process so your functions are
 * ready to start processing requests immediately. Some actions for a warmup trigger might include opening connections,
 * loading dependencies, or running any other custom logic before your app begins receiving traffic.
 * The parameter type should be set as {@link java.lang.Object}</p>
 *
 *
 * <p>The following example shows a Java function that logs the message body of the event hub trigger:</p>
 *
 * <pre>{@literal @}FunctionName("Warmup")
 * public void warmup(
 *    {@literal @}WarmupTrigger Object warmupContext,
 *     final ExecutionContext context
 * ) {
 *     context.getLogger().info("Function App instance is warm up");
 * }</pre>
 *
 * @since 2.0.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@CustomBinding(direction = "in", name = "warmupContext", type = "warmupTrigger")
public @interface WarmupTrigger {
    /**
     * <p>Defines how Functions runtime should treat the parameter value. Possible values are:</p>
     * <ul>
     *     <li>"": get the value as a string, and try to deserialize to actual parameter type like POJO</li>
     *     <li>string: always get the value as a string</li>
     *     <li>binary: get the value as a binary data, and try to deserialize to actual parameter type byte[]</li>
     * </ul>
     * @return The dataType which will be used by the Functions runtime.
     */
    String dataType() default "";
}
