package com.microsoft.azure.functions;

/**
 *
 * @since 1.0.0
 */
public interface OutputBinding<T> {
    T getValue();
    void setValue(T value);
}
