package com.traffic.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FastCampusApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastCampusApplication.class, args);
    }

}
