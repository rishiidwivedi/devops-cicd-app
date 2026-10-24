
package com.example.devops;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetingServiceTest {

    private final GreetingService greetingService = new GreetingService();

    @Test
    void shouldGreetNamedUser() {
        assertEquals("Hello, Jenkins!", greetingService.greet("Jenkins"));
    }

    @Test
    void shouldTrimWhitespaceFromName() {
        assertEquals("Hello, Jenkins!", greetingService.greet("  Jenkins  "));
    }

    @Test
    void shouldUseWorldWhenNameIsNull() {
        assertEquals("Hello, World!", greetingService.greet(null));
    }

    @Test
    void shouldUseWorldWhenNameIsBlank() {
        assertEquals("Hello, World!", greetingService.greet("   "));
    }
}
