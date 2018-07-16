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
 * <p>Place this on a parameter whose value would be written to twilio SMS.
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
public @interface TwilioSmsOutput {
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
     * Defines the account SID of Twilio SMS to which to write.
     * @return The Twilio SMS account SID string.
     */
    String accountSid();

    /**
     * Defines the authorization token of Twilio SMS to which to write.
     * @return The Twilio SMS authorization token string.
     */
    String authToken();

    /**
     * Defines the target of Twilio SMS to which to write.
     * @return The Twilio SMS target string.
     */
    String to();

    /**
     * Defines the source of Twilio SMS to which to write.
     * @return The Twilio SMS source string.
     */
    String from();

    /**
     * Defines the content body of Twilio SMS to which to write.
     * @return The Twilio SMS content body string.
     */
    String body();
}
