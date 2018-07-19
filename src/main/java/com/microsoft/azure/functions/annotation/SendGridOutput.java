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
 * <p>Place this on a parameter whose value would be written to SendGrid.
 * The parameter type should be OutputBinding&lt;T&gt;, where T could be one of:</p>
 *
 * <ul>
 *     <li>Any native Java types such as int, String, byte[]</li>
 *     <li>Any POJO type</li>
 * </ul>
 *
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
public @interface SendGridOutput {
    /**
     * The variable name used in function.json.
     * @return The variable name used in function.json.
     */
    String name();

    /**
     * <p>Defines how Functions runtime should treat the parameter value. Possible values are:</p>
     * <ul>
     *     <li>"" or string: treat it as a string whose value is serialized from the parameter</li>
     *     <li>binary: treat it as a binary data whose value comes from for example OutputBinding&lt;byte[]&gt;</li>
     * </ul>
     * @return The dataType which will be used by the Functions runtime.
     */
    String dataType() default "";

    /**
     * Defines the API key of the SendGrid to which to write.
     * @return The SendGrid API key string.
     */
    String apiKey();

    /**
     * Defines the 'TO' email of the SendGrid to which to write.
     * @return The SendGrid 'TO' email string.
     */
    String to();

    /**
     * Defines the 'FROM' name of the SendGrid to which to write.
     * @return The SendGrid 'FROM' name string.
     */
    String from();

    /**
     * Defines the subject of the SendGrid email to which to write.
     * @return The SendGrid email subject string.
     */
    String subject();

    /**
     * Defines the content text of the SendGrid email to which to write.
     * @return The SendGrid email content string.
     */
    String text();
}
