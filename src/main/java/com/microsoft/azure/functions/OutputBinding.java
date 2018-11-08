/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions;

/**
 * <p>This type should be used with the parameter of output bindings.</p>
 *
 * @since 1.0.0
 */
public interface OutputBinding<T> {
    /**
     * Get the value to be passed to the output binding.
     * @return The actual value to be passed to the output binding.
     */
    T getValue();

    /**
     * Set the value to be passed to the output binding.
     * @param value The actual value to be passed to the output binding.
     */
    void setValue(T value);
}
