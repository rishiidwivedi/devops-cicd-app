
package com.example.devops;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final ParameterizedTypeReference<Map<String, String>>
            RESPONSE_TYPE = new ParameterizedTypeReference<>() {};

    @Test
    void healthEndpointShouldReturnUp() {
        ResponseEntity<Map<String, String>> response =
                restTemplate.exchange(
                        "/api/health",
                        HttpMethod.GET,
                        null,
                        RESPONSE_TYPE);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() != null);
        assertEquals("UP", response.getBody().get("status"));
    }

    @Test
    void greetingEndpointShouldReturnNamedGreeting() {
        ResponseEntity<Map<String, String>> response =
                restTemplate.exchange(
                        "/api/greeting?name=Jenkins",
                        HttpMethod.GET,
                        null,
                        RESPONSE_TYPE);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() != null);
        assertEquals(
                "Hello, Jenkins!",
                response.getBody().get("message"));
    }

    @Test
    void greetingEndpointShouldUseDefaultName() {
        ResponseEntity<Map<String, String>> response =
                restTemplate.exchange(
                        "/api/greeting",
                        HttpMethod.GET,
                        null,
                        RESPONSE_TYPE);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() != null);
        assertEquals(
                "Hello, World!",
                response.getBody().get("message"));
    }
}
