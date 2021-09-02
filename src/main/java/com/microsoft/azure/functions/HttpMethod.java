/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.functions;

import java.util.Locale;

/**
 * Copyright (c) Microsoft Corporation. All rights reserved. Licensed under the MIT License. See
 * License.txt in the project root for license information.
 */

public enum HttpMethod {

  GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE, PATCH;

      /**
       * Converts passed value to upper case to extract valueOf() of this Enum.
       * 
       * @param value of http method in any case
       * @return this enum
       */
    public static HttpMethod value(String value) {
        return HttpMethod.valueOf(value.toUpperCase(Locale.ROOT));
    }

}
