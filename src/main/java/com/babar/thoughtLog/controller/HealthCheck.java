package com.babar.thoughtLog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthCheck {

    @GetMapping("/hel-check")
    public String healthCheck(){
        return "OK";
    }
    
}
