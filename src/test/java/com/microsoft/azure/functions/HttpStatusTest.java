package com.microsoft.azure.functions;

import org.junit.*;

import static junit.framework.TestCase.*;

/**
 * Unit tests that enforce annotation contracts and conventions for Functions
 */
public class HttpStatusTest {
    @Test
    public void set_custom_httpstatuscode() {
        HttpStatusType customHttpStatus = HttpStatusType.custom(209);
        assertTrue(customHttpStatus.value() == 209);
    }

    @Test
    public void set_standard_httpstatuscode() {
        HttpStatusType customHttpStatus = HttpStatus.OK;
        assertTrue(customHttpStatus.value() == 200);
    }

    @Test(expected = IllegalArgumentException.class)
    public void set_invalid_httpstatuscode() {
        HttpStatusType.custom(-100);
    }
}
