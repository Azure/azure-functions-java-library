/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions;

import java.util.Map;

/**
 * The Tracing context that is obtained from the host.
 *
 * @since 1.0.0
 */
public interface TraceContext {
    /**
     * Returns the TraceparentString from the Activity.
     *
     * @return the TraceparentString from the Activity.
     */
    String getTraceparent();

    /**
     * Returns the Tracestate which is Activity.Current?.Id from host.
     * @return the Tracestate which is Activity.Current?.Id from host.
     */
    String getTracestate();

    /**
     * Returns the attributes which correspond to the tags.
     * @return the attributes which correspond to the tags.
     */
    Map<String, String> getAttributes();
}
