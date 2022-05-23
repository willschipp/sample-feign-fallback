package com.example.feign.client;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties={"simple.client.url=http://localhost:9000/","feign.circuitbreaker.enabled=true"})
public class SimpleFeignClientIT {
    
    
    WireMockServer wireMockServer = new WireMockServer(9000);

    @Autowired
    SimpleFeignClient simpleFeignClient;

    @Test
    public void test_invoke() throws Exception {
        wireMockServer.stubFor(get("/ping")
            .willReturn(aResponse()
                .withStatus(200).withBody("pong")));
        
        wireMockServer.start();

        String response = simpleFeignClient.getPing();
        assertEquals("pong", response);

        wireMockServer.stop();
    }


    @Test
    public void test_fallback() throws Exception {
        wireMockServer.stubFor(get("/ping")
            .willReturn(aResponse()
                .withStatus(500)));

        wireMockServer.start();

        String response = simpleFeignClient.getPing();
        assertEquals("fallback", response);

        wireMockServer.stop();                
    }
    
}