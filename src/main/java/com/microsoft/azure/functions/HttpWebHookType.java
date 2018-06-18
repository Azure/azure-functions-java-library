/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions;

/**
 * List of supported WebHooks for HttpTrigger.
 * 
 * <p>
 * Configures the HTTP trigger to act as a webhook receiver for the specified
 * provider. Don't set the methods property if you set this property. The
 * webhook type can be one of the following values:
 * </p>
 *
 * <ul>
 * <li><strong>genericJson</strong>: A general-purpose webhook endpoint without
 * logic for a specific provider. This setting restricts requests to only those
 * using HTTP POST and with the {@code application/json} content type.</li>
 * <li><strong>github</strong>: The function responds to
 * <a href="https://developer.github.com/webhooks/">GitHub webhooks</a>. Do not
 * use the {@code com.microsoft.azure.functions.annotation.HttpTrigger.authLevel()} property with GitHub webhooks.</li>
 * <li><strong>slack</strong>: The function responds to
 * <a href="https://api.slack.com/outgoing-webhooks">Slack webhooks</a>. Do not
 * use the {@code com.microsoft.azure.functions.annotation.HttpTrigger.authLevel()} property with Slack webhooks.</li>
 * </ul>
 */
public enum HttpWebHookType {
    NONE, GENERICJSON, GITHUB, SLACK;
}