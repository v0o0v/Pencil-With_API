package com.pencilwith.apiserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthcheckController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
