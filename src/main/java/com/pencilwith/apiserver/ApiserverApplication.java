package com.pencilwith.apiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ApiserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiserverApplication.class, args);
    }

}
