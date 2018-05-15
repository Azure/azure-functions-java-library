package com.microsoft.azure.functions;

public interface OutputBinding<T> {
    T getValue();
    void setValue(T value);
}
