/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions;

/**
 * The Retry context that is obtained from the host.
 *
 * @since 2.0.0
 */
public interface RetryContext {

    /**
     * Returns the current retry count
     *
     * @return the current retry count.
     */
    int getRetrycount();

    /**
     * Returns the max retry count
     *
     * @return the max retry count.
     */
    int getMaxretrycount();

    /**
     * Returns the exception that caused the retry
     *
     * @return the exception that caused the retry.
     */
    RpcException getException();

}
