package com.microsoft.azure.functions;

import java.util.logging.Logger;

public interface ExecutionContext {
    Logger getLogger();
    String getInvocationId();
    String getFunctionName();
}
