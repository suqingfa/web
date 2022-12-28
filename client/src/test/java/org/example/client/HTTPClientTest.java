package org.example.client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPClientTest {
    private static final Logger log = LoggerFactory.getLogger(HTTPClientTest.class);

    private static HttpClient client;

    @BeforeAll
    static void beforeAll() {
        client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    private void test(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        log.info("response version: {}", response.version());
        log.info("response headers: {}", response.headers());
        log.info("response body: {}", response.body());
    }

    @Test
    void getTest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/get"))
                .header("Cookie", "userId=java11")
                .GET()
                .build();

        log.info("get test");
        test(request);
    }

    @Test
    void postFormTest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/post"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("key1=value1&key2=value2"))
                .build();

        log.info("post from test");
        test(request);
    }

    @Test
    void postJsonTest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://httpbin.org/post"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {"key":"value"}
                        """))
                .build();

        log.info("post json test");
        test(request);
    }
}
