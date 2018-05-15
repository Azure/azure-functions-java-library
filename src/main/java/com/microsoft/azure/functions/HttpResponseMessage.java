package com.microsoft.azure.functions;

public interface HttpResponseMessage<T> {
    int getStatus();
    void setStatus(int status);
    void addHeader(String key, String value);
    String getHeader(String key);
    T getBody();
    void setBody(T body);
}
