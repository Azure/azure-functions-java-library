/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.functions;


public interface RpcException {

    // Source of the exception
    String getSource();

    // Stack trace for the exception
    String getStacktrace();

    // Textual message describing the exception
    String getMessage();

}
