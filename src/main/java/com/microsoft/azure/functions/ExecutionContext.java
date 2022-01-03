/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions;

import java.util.logging.Logger;

/**
 * The execution context enables interaction with the Azure Functions execution environment.
 *
 * @since 1.0.0
 */
public interface ExecutionContext {
    /**
     * Returns the built-in logger, which is integrated with the logging functionality provided in the Azure Functions
     * portal, as well as in Azure Application Insights.
     *
     * @return A Java logger that will see output directed to Azure Portal, as well as any other configured output
     *      locations.
     */
    Logger getLogger();

    /**
     * Returns the invocation ID for the function call.
     * @return the invocation ID for the function call.
     */
    String getInvocationId();

    /**
     * Returns the function name.
     * @return the function name.
     */
    String getFunctionName();

    /**
     * Returns the trace context.
     * @return the trace context
     */
    default TraceContext getTraceContext() {
        return null;
    }

    /**
     * Returns the retry context.
     * @return the retry context
     */
    default RetryContext getRetryContext() {
        return null;
    }
}

