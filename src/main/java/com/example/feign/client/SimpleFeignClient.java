package com.example.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="simpleClient",url="${simple.client.url}",fallback = SimpleFeignClientFallback.class)
public interface SimpleFeignClient {

    @GetMapping("/ping")
    String getPing();

}