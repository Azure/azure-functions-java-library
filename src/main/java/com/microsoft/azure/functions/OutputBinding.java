/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions;

/**
 *
 * @since 1.0.0
 */
public interface OutputBinding<T> {
    T getValue();
    void setValue(T value);
}
