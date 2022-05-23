package com.example.feign.client;

import org.springframework.stereotype.Component;

@Component
public class SimpleFeignClientFallback implements SimpleFeignClient {

    @Override
    public String getPing() {
        return "fallback";
    }
    
    
}
